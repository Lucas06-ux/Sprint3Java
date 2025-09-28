package br.com.fiap.saudedigital.dao;

import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.factory.ConnectionFactory;
import br.com.fiap.saudedigital.model.Lembrete;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LembreteDAO {
    private Connection conexao;

    public LembreteDAO() throws SQLException, ClassNotFoundException{
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Lembrete lembrete) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("INSERT INTO T_SD_LEMBRETE (CD_LEMBRETE, DS_MENSAGEM, DT_ENVIO) " +
                        "VALUES (SQ_LEMBRETE_ID.NEXTVAL,?,?)");
            stmt.setString(1, lembrete.getMensagem());
            stmt.setDate(2, Date.valueOf(lembrete.getDataEnvio()));
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    lembrete.setCodigoLembrete(idGerado);
                }
            }
    }//cadastrar


    public void atualizar (Lembrete lembrete) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("UPDATE T_SD_LEMBRETE SET DS_MENSAGEM = ?, DT_ENVIO = ? " +
                        "WHERE CD_LEMBRETE = ?");
            stmt.setString(1, lembrete.getMensagem());
            stmt.setDate(2, Date.valueOf(lembrete.getDataEnvio()));
            stmt.setInt(3, lembrete.getCodigoLembrete());

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Nenhum lembrete encontrado para atualizar! ❌");
    }//atualizar

    public Lembrete buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("SELECT CD_LEMBRETE, DS_MENSAGEM, DT_ENVIO FROM T_SD_LEMBRETE WHERE CD_LEMBRETE = ?");
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())
                    throw new EntidadeNaoEncontradaException("Lembrete não encontrado!");
                return parseLembrete(rs);
            }
    }//buscar por ID

    public List<Lembrete> listar () throws SQLException {
        Statement stmt = conexao.createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM T_SD_LEMBRETE");
            List<Lembrete> lembretes = new ArrayList<>();
            while (result.next()){
                Lembrete lembrete = parseLembrete(result);
                lembretes.add(lembrete);
            }
            return lembretes;
    }//listar

    public void remover (int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("DELETE FROM T_SD_LEMBRETE WHERE CD_LEMBRETE = ?");
            stmt.setInt(1, id);

            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontradaException("Não foi possivel remover. Lembrete não existe!");
    }//remover

    private static Lembrete parseLembrete(ResultSet result) throws SQLException {
        int codigoLembrete = result.getInt("cd_lembrete");
        String mensagem = result.getString("ds_mensagem");
        LocalDate dataEnvio = result.getDate("dt_envio").toLocalDate();

        Lembrete lembrete = new Lembrete(codigoLembrete, mensagem, dataEnvio);
        return lembrete;
    }

}//classe
