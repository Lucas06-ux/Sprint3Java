package br.com.fiap.saudedigital.dao;

import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.factory.ConnectionFactory;
import br.com.fiap.saudedigital.model.Consulta;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    private Connection conexao;

    public ConsultaDAO() throws SQLException, ClassNotFoundException{
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Consulta consulta) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_SD_CONSULTA (CD_CONSULTA, DT_INICIO, DT_FIM, DS_LINK, DS_OBSERVACOES, DS_STATUS, CD_PACIENTE, CD_MEDICO) " +
                        "VALUES (SQ_CONSULTA_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)") ;
            stmt.setDate(1, Date.valueOf(consulta.getDataInicio()));
            stmt.setDate(2, Date.valueOf(consulta.getDataFim()));
            stmt.setString(3, consulta.getLink());
            stmt.setString(4, consulta.getObservacoes());
            stmt.setString(5, consulta.getStatusConsulta());
            stmt.setInt(6, consulta.getCodigoPaciente());
            stmt.setInt(7, consulta.getCodigoMedico());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    consulta.setCodigoConsulta(idGerado);
                }
            }
    }//cadastrar

    public void atualizar(Consulta consulta) throws SQLException, EntidadeNaoEncontradaException {
         PreparedStatement stmt = conexao.prepareStatement("UPDATE T_SD_CONSULTA SET DT_INICIO = ?, DT_FIM = ?, DS_LINK = ?" +
                 " DS_OBSERVACOES = ?, DS_STATUS = ?, CD_PACIENTE = ?, CD_MEDICO = ? " +
                        "WHERE CD_CONSULTA = ?");

            stmt.setDate(1, Date.valueOf(consulta.getDataInicio()));
            stmt.setDate(2, Date.valueOf(consulta.getDataFim()));
            stmt.setString(3, consulta.getLink());
            stmt.setString(4, consulta.getObservacoes());
            stmt.setString(5, consulta.getStatusConsulta());
            stmt.setInt(6, consulta.getCodigoPaciente());
            stmt.setInt(7, consulta.getCodigoMedico());
            stmt.setInt(8, consulta.getCodigoConsulta());

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Nenhuma consulta encontrada para atualizar! ❌");
    }//atualizar

    public Consulta buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement(
                "SELECT CD_CONSULTA, DT_INICIO, DT_FIM, DS_LINK, DS_OBSERVACOES, DS_STATUS, CD_PACIENTE, CD_MEDICO " +
                        "FROM T_SD_CONSULTA WHERE CD_CONSULTA = ?");
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())
                    throw new EntidadeNaoEncontradaException("Consulta não encontrada!");
                return parseConsulta(rs);
            }
    }//buscar por ID

    public List<Consulta> listar() throws SQLException {
        Statement stmt = conexao.createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM T_SD_CONSULTA");
            List<Consulta> consultas = new ArrayList<>();
            while (result.next()) {
                Consulta consulta = parseConsulta(result);
                consultas.add(consulta);
            }
            return consultas;
    }//listar

    public void remover(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_SD_CONSULTA WHERE CD_CONSULTA = ?");

            stmt.setInt(1, id);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontradaException("Não foi possivel remover. Consulta não existe!");
    }//remover

    private static Consulta parseConsulta(ResultSet result) throws SQLException {
        int codigoConsulta = result.getInt("CD_CONSULTA");

        LocalDate dataInicio = result.getDate("DT_INICIO").toLocalDate();
        LocalDate dataFim = result.getDate("DT_FIM").toLocalDate();

        String link = result.getString("DS_LINK");
        String observacoes = result.getString("DS_OBSERVACOES");
        String status = result.getString("DS_STATUS");
        int codigoPaciente = result.getInt("CD_PACIENTE");
        int codigoMedico = result.getInt("CD_MEDICO");

        Consulta consulta = new Consulta(codigoConsulta, dataInicio, dataFim, link, observacoes, status, codigoPaciente, codigoMedico);
        return consulta;
    }

}
