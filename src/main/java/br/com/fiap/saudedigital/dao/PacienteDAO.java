package br.com.fiap.saudedigital.dao;

import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.factory.ConnectionFactory;
import br.com.fiap.saudedigital.model.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private Connection conexao;

    public PacienteDAO() throws SQLException, ClassNotFoundException{
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Paciente paciente) throws SQLException{

        PreparedStatement stmt = conexao.prepareStatement("insert into t_sd_paciente (cd_paciente, nm_paciente, dt_nascimento, nr_cpf, ds_email," +
                "nr_telefone_1, nr_telefone_2, nr_telefone_3) values(sq_paciente_id.nextval,?,?,?,?,?,?,?)");

        stmt.setString(1, paciente.getNome());
        stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
        stmt.setString(3, paciente.getCpf());
        stmt.setString(4,paciente.getEmail());
        stmt.setString(5,paciente.getNmrTelefone1());
        stmt.setString(6, paciente.getNmrTelefone2());
        stmt.setString(7, paciente.getNmrTelefone3());

        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                paciente.setCodigoPaciente(idGerado);
            }
        }

        }//cadastrar

        public void atualizar (Paciente paciente) throws SQLException, EntidadeNaoEncontradaException{
        PreparedStatement stmt = conexao.prepareStatement("update t_sd_paciente set nm_paciente = ?," +
                "dt_nascimento = ?, nr_cpf = ?, ds_email = ?, nr_telefone_1 = ?, nr_telefone_2 = ?, nr_telefone_3 = ? where cd_paciente = ?");
            stmt.setString(1, paciente.getNome());
            stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getEmail());
            stmt.setString(5, paciente.getNmrTelefone1());
            stmt.setString(6, paciente.getNmrTelefone2());
            stmt.setString(7, paciente.getNmrTelefone3());
            stmt.setInt(8, paciente.getCodigoPaciente());

           if (stmt.executeUpdate() == 0)
               throw new EntidadeNaoEncontradaException("Nenhum paciente encontrado para atualizar! ❌");

    }//atualizar

    public Paciente buscarPorId(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stmt = conexao.prepareStatement("SELECT cd_paciente, nm_paciente, dt_nascimento, " +
                "nr_cpf, ds_email, nr_telefone_1, nr_telefone_2, nr_telefone_3 FROM T_SD_PACIENTE WHERE CD_PACIENTE = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next())
            throw new EntidadeNaoEncontradaException("Paciente não encontrado!");

        return parsePaciente(rs);


    }//buscar por ID

    public List<Paciente> listar() throws SQLException{
        Statement stmt = conexao.createStatement();

        ResultSet result = stmt.executeQuery("select * from t_sd_paciente");

        List<Paciente> pacientes = new ArrayList<>();
        while (result.next()){

            Paciente paciente = parsePaciente(result);

            pacientes.add(paciente);
        }
        return pacientes;
    }// listar


    public void remover(int id) throws SQLException, EntidadeNaoEncontradaException{
        PreparedStatement stmt = conexao.prepareStatement("delete from t_sd_paciente where cd_paciente = ?");

        stmt.setInt(1, id);

        int linha = stmt.executeUpdate();
        if (linha == 0)
            throw new EntidadeNaoEncontradaException("Não foi possivel remover. Paciente não existe!");
    }// remover

    private static Paciente parsePaciente(ResultSet result) throws SQLException {
        int codigoPaciente = result.getInt("cd_paciente");
        String nome = result.getString("nm_paciente");
        LocalDate dataNascimento = result.getDate("dt_nascimento").toLocalDate();
        String cpf = result.getString("nr_cpf");
        String email = result.getString("ds_email");
        String nmrTelefone1 = result.getString("nr_telefone_1");
        String nmrTelefone2 = result.getString("nr_telefone_2");
        String nmrTelefone3 = result.getString("nr_telefone_3");

        Paciente paciente = new Paciente(codigoPaciente, nome, dataNascimento, cpf, email, nmrTelefone1, nmrTelefone2, nmrTelefone3);
        return paciente;
    }


}//classe
