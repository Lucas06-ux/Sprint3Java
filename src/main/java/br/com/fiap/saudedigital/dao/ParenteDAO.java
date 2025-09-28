package br.com.fiap.saudedigital.dao;

import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.factory.ConnectionFactory;
import br.com.fiap.saudedigital.model.Parente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParenteDAO {

    private Connection conexao;

    public ParenteDAO() throws SQLException, ClassNotFoundException{
         conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Parente parente) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_SD_PARENTE (CD_PARENTE, NM_PARENTE, DS_PARENTESCO, NR_TELEFONE_1, " +
                        "NR_TELEFONE_2, NR_TELEFONE_3, CD_PACIENTE, CD_LEMBRETE) " +
                        "VALUES (SQ_PARENTE_ID.NEXTVAL,?,?,?,?,?,?,?)");
            stmt.setString(1, parente.getNome());
            stmt.setString(2, parente.getDsParentesco());
            stmt.setString(3, parente.getNmrTelefone1());
            stmt.setString(4, parente.getNmrTelefone2());
            stmt.setString(5, parente.getNmrTelefone3());
            stmt.setInt(6, parente.getCodigoPaciente());
            stmt.setInt(7, parente.getCodigoLembrete());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    parente.setCodigoParente(idGerado);
                }
            }
    }//cadastrar

    public void atualizar (Parente parente) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_SD_PARENTE SET NM_PARENTE = ?, DS_PARENTESCO = ?, NR_TELEFONE_1 = ?, NR_TELEFONE_2 = ?, " +
                        "NR_TELEFONE_3 = ?, CD_PACIENTE = ?, CD_LEMBRETE = ? WHERE CD_PARENTE = ?");

            stmt.setString(1, parente.getNome());
            stmt.setString(2, parente.getDsParentesco());
            stmt.setString(3, parente.getNmrTelefone1());
            stmt.setString(4, parente.getNmrTelefone2());
            stmt.setString(5, parente.getNmrTelefone3());
            stmt.setInt(6, parente.getCodigoPaciente());
            stmt.setInt(7, parente.getCodigoLembrete());
            stmt.setInt(8, parente.getCodigoParente());

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Nenhum parente encontrado para atualizar! ❌");
    }//atualizar

    public Parente buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("SELECT CD_PARENTE, NM_PARENTE, DS_PARENTESCO, NR_TELEFONE_1, NR_TELEFONE_2, NR_TELEFONE_3, CD_PACIENTE, CD_LEMBRETE " +
                        "FROM T_SD_PARENTE WHERE CD_PARENTE = ?");

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())
                    throw new EntidadeNaoEncontradaException("Parente não encontrado!");
                return parseParente(rs);
            }
    }//buscar por ID

    public List<Parente> listar() throws SQLException {
        Statement stmt = conexao.createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM T_SD_PARENTE");
            List<Parente> parentes = new ArrayList<>();
            while (result.next()){
                Parente parente = parseParente(result);
                parentes.add(parente);
            }
            return parentes;

    }//listar

    public void remover(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_SD_PARENTE WHERE CD_PARENTE = ?");
            stmt.setInt(1, id);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontradaException("Não foi possivel remover. Parente não existe!");
    }//remover

    private static Parente parseParente(ResultSet result) throws SQLException {
        int codigoParente = result.getInt("cd_parente");
        String nome = result.getString("nm_parente");
        String dsParentesco = result.getString("ds_parentesco");
        String nmrTelefone1 = result.getString("nr_telefone_1");
        String nmrTelefone2 = result.getString("nr_telefone_2");
        String nmrTelefone3 = result.getString("nr_telefone_3");
        int codigoPaciente = result.getInt("cd_paciente");
        int codigoLembrete = result.getInt("cd_lembrete");

        Parente parente = new Parente(nome, codigoParente, dsParentesco, nmrTelefone1, nmrTelefone2, nmrTelefone3, codigoLembrete, codigoPaciente);
        return parente;
    }

}//classe
