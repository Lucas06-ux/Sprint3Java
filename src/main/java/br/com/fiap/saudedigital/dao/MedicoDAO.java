package br.com.fiap.saudedigital.dao;

import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.factory.ConnectionFactory;
import br.com.fiap.saudedigital.model.Medico;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    private Connection conexao;

    public MedicoDAO() throws SQLException, ClassNotFoundException{
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Medico medico) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_SD_MEDICO (CD_MEDICO, NM_MEDICO, NR_CRM, DT_NASCIMENTO, DS_ESPECIALIDADE, VL_SALARIO) " +
                        "VALUES(SQ_MEDICO_ID.NEXTVAL,?,?,?,?,?)");

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setDate(3, Date.valueOf(medico.getDataNascimento()));
            stmt.setString(4, medico.getEspecialidade());
            stmt.setDouble(5, medico.getSalario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    medico.setCodigoMedico(idGerado);
                }
            }

    }//cadastrar

    // 2. ATUALIZAR (UPDATE)
    public void atualizar (Medico medico) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_SD_MEDICO SET NM_MEDICO = ?, NR_CRM = ?, DT_NASCIMENTO = ?, DS_ESPECIALIDADE = ?, VL_SALARIO = ? " +
                        "WHERE CD_MEDICO = ?");

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setDate(3, Date.valueOf(medico.getDataNascimento()));
            stmt.setString(4, medico.getEspecialidade());
            stmt.setDouble(5, medico.getSalario());
            stmt.setInt(6, medico.getCodigoMedico());

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Nenhum médico encontrado para atualizar! ❌");
    }//atualizar

    public Medico buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement(
                "SELECT CD_MEDICO, NM_MEDICO, NR_CRM, DT_NASCIMENTO, DS_ESPECIALIDADE, VL_SALARIO " +
                        "FROM T_SD_MEDICO WHERE CD_MEDICO = ?");
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())
                    throw new EntidadeNaoEncontradaException("Médico não encontrado!");
                return parseMedico(rs);
            }

    }//buscar por ID

    public List<Medico> listar() throws SQLException {
        try (Statement stmt = conexao.createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM T_SD_MEDICO")) {

            List<Medico> medicos = new ArrayList<>();
            while (result.next()){
                Medico medico = parseMedico(result);
                medicos.add(medico);
            }
            return medicos;
        }
    }// listar

    public void remover(int id) throws SQLException, EntidadeNaoEncontradaException {
         PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_SD_MEDICO WHERE CD_MEDICO = ?");

            stmt.setInt(1, id);

            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontradaException("Não foi possivel remover. Médico não existe!");
    }// remover

    private static Medico parseMedico(ResultSet result) throws SQLException {
        int codigoMedico = result.getInt("CD_MEDICO");
        String nome = result.getString("NM_MEDICO");
        String crm = result.getString("NR_CRM");
        LocalDate dataNascimento = result.getDate("DT_NASCIMENTO").toLocalDate();
        String especialidade = result.getString("DS_ESPECIALIDADE");
        double salario = result.getDouble("VL_SALARIO");

        Medico medico = new Medico(nome, dataNascimento, codigoMedico, crm, especialidade, salario);
        return medico;
    }

}//classe
