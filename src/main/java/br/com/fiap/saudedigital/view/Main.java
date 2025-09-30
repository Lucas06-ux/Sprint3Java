package br.com.fiap.saudedigital.view;

import br.com.fiap.saudedigital.dao.*;
import br.com.fiap.saudedigital.exception.EntidadeNaoEncontradaException;
import br.com.fiap.saudedigital.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner leia = new Scanner(System.in);

        try {
            PacienteDAO pacienteDao = new PacienteDAO();
            MedicoDAO medicoDao = new MedicoDAO();
            LembreteDAO lembreteDAO = new LembreteDAO();
            ParenteDAO parenteDAO = new ParenteDAO();
            ConsultaDAO consultaDAO = new ConsultaDAO();

            int opcaoPrincipal = -1;
            do {
                System.out.println("\n--- MENU PRINCIPAL ---");

                System.out.println("Escolha a entidade: \n1-Gerenciar Pacientes \n2-Gerenciar Médicos \n3-Gerenciar Lembretes" +
                        " \n4-Gerenciar Parentes \n5-Gerenciar Consultas \n0-Sair");
                try {
                    opcaoPrincipal = leia.nextInt();
                    leia.nextLine();

                    switch (opcaoPrincipal) {
                        case 1:
                            gerenciarPacientes(leia, pacienteDao);
                            break;
                        case 2:
                            gerenciarMedicos(leia, medicoDao);
                            break;
                        case 3:
                            gerenciarLembretes(leia, lembreteDAO);
                            break;
                        case 4:
                            gerenciarParentes(leia, parenteDAO, pacienteDao, lembreteDAO);
                            break;
                        case 5:
                            gerenciarConsultas(leia, consultaDAO, pacienteDao, medicoDao);
                            break;
                        case 0:
                            System.out.println("Programa encerrado. Obrigado! 👋");
                            break;
                        default:
                            System.out.println("Opção inválida!❌");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                    leia.nextLine();
                    opcaoPrincipal = -1;
                }
            } while (opcaoPrincipal != 0);

        } catch (Exception e) {
            System.err.println("\nERRO FATAL NA APLICAÇÃO: 💀💀💀");
            e.printStackTrace();
        } finally {
            leia.close();
        }
    } // main

//-----------------------------------------------------------
//                                 PACIENTE
//-----------------------------------------------------------

    private static void gerenciarPacientes(Scanner leia, PacienteDAO dao) throws SQLException {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PACIENTES ---");
            System.out.println("Escolha: \n1-Cadastrar \n2-Atualizar \n3-Pesquisar por id \n4-Listar \n5-Remover \n0-Voltar ao Menu Principal");

            try {
                opcao = leia.nextInt();
                leia.nextLine();

                switch (opcao) {
                    case 1:
                        Paciente paciente = lerPaciente(leia);
                        dao.cadastrar(paciente);
                        System.out.println("Paciente cadastrado com sucesso! ✅");
                        break;

                    case 2:
                        try {
                            paciente = lerPaciente(leia);
                            System.out.print("Digite o ID do paciente que deseja atualizar: ");
                            int idAtualizar = leia.nextInt();
                            leia.nextLine();
                            paciente.setCodigoPaciente(idAtualizar);

                            dao.atualizar(paciente);
                            System.out.println("Paciente atualizado com sucesso!");
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 3:
                        try {
                            System.out.println("Digite o ID do paciente que deseja pesquisar:");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            Paciente pac = dao.buscarPorId(codigo);
                            System.out.println(pac);
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Lista de Pacientes ---");
                        List<Paciente> lista = dao.listar();
                        for (Paciente p : lista) {
                            System.out.println(p);
                        }
                        break;

                    case 5:
                        try{
                            System.out.println("Digite o código do paciente que deseja excluir");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            dao.remover(codigo);
                            System.out.println("Paciente removido com sucesso ☠️");

                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal... ☁️");
                        break;

                    default:
                        System.out.println("Opção inválida!❌");
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                leia.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }//gerenciar pacientes

//---------------------------------------------------------------------
//                                    MEDICO
//---------------------------------------------------------------------

    private static void gerenciarMedicos(Scanner leia, MedicoDAO dao) throws SQLException {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR MÉDICOS ---");

            System.out.println("Escolha: \n1-Cadastrar \n2-Atualizar \n3-Pesquisar por id \n4-Listar \n5-Remover \n0-Voltar ao Menu Principal");
            try {
                opcao = leia.nextInt();
                leia.nextLine();
                switch (opcao) {
                    case 1:
                        Medico medico = lerMedico(leia);
                        dao.cadastrar(medico);
                        System.out.println("Médico cadastrado com sucesso! ✅");
                        break;
                    case 2:
                        try {
                            System.out.print("Digite o ID do médico que deseja atualizar: ");
                            int idAtualizar = leia.nextInt();
                            leia.nextLine();

                            medico = lerMedico(leia);
                            medico.setCodigoMedico(idAtualizar);

                            dao.atualizar(medico);
                            System.out.println("Médico atualizado com sucesso!✅");
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.println("Digite o ID do médico que deseja pesquisar:");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            Medico med = dao.buscarPorId(codigo);
                            System.out.println(med);
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("\n--- Lista de Médicos ---");
                        List<Medico> lista = dao.listar();
                        for (Medico m : lista) {
                            System.out.println(m);
                        }
                        break;
                    case 5:
                        try{
                            System.out.println("Digite o código do médico que deseja excluir");
                            int codigo = leia.nextInt();
                            leia.nextLine();
                            dao.remover(codigo);
                            System.out.println("Médico removido com sucesso ☠️");
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;
                    case 0:
                        System.out.println("Voltando ao Menu Principal...☁️");
                        break;
                    default:
                        System.out.println("Opção inválida!❌");
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                leia.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }//gerenciar medicos
//-------------------------------------------------------------------------------------
//                                         LEMBRETE
// --------------------------------------------------------------------------------------

    private static void gerenciarLembretes(Scanner leia, LembreteDAO dao) throws SQLException {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR LEMBRETES ---");
            System.out.println("Escolha: \n1-Cadastrar \n2-Atualizar \n3-Pesquisar por id \n4-Listar \n5-Remover \n0-Voltar ao Menu Principal");

            try {
                opcao = leia.nextInt();
                leia.nextLine();

                switch (opcao) {
                    case 1:
                        Lembrete lembrete = lerLembrete(leia);
                        dao.cadastrar(lembrete);
                        System.out.println("Lembrete cadastrado com sucesso! ✅");
                        break;

                    case 2:
                        try {
                            System.out.print("Digite o ID do lembrete que deseja atualizar: ");
                            int idAtualizar = leia.nextInt();
                            leia.nextLine();

                            lembrete = lerLembrete(leia);
                            lembrete.setCodigoLembrete(idAtualizar);

                            dao.atualizar(lembrete);
                            System.out.println("Lembrete atualizado com sucesso!✅");
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 3:
                        try {
                            System.out.println("Digite o ID do lembrete que deseja pesquisar:");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            Lembrete lem = dao.buscarPorId(codigo);
                            System.out.println(lem);
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Lista de Lembretes ---");
                        List<Lembrete> lista = dao.listar();
                        for (Lembrete l : lista) {
                            System.out.println(l);
                        }
                        break;

                    case 5:
                        try{
                            System.out.println("Digite o código do lembrete que deseja excluir");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            dao.remover(codigo);
                            System.out.println("Lembrete removido com sucesso ☠️");

                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal...☁️");
                        break;

                    default:
                        System.out.println("Opção inválida!❌");
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                leia.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }//gerenciar lembretes
//----------------------------------------------------------------------------------
//                             PARENTE
// ----------------------------------------------------------------------------------

    private static void gerenciarParentes(Scanner leia, ParenteDAO parenteDAO, PacienteDAO pacienteDAO, LembreteDAO lembreteDAO) throws SQLException {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PARENTES ---");
            System.out.println("Escolha: \n1-Cadastrar \n2-Atualizar \n3-Pesquisar por id \n4-Listar \n5-Remover \n0-Voltar ao Menu Principal");

            try {
                opcao = leia.nextInt();
                leia.nextLine();

                switch (opcao) {
                    case 1:
                        try {
                            Parente parente = lerParente(leia, pacienteDAO, lembreteDAO);
                            parenteDAO.cadastrar(parente);
                            System.out.println("Parente cadastrado com sucesso! ✅");
                        }catch(EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Digite o ID do parente que deseja atualizar: ");
                            int idAtualizar = leia.nextInt();
                            leia.nextLine();

                            Parente parente = lerParente(leia, pacienteDAO, lembreteDAO);
                            parente.setCodigoParente(idAtualizar);
                            parenteDAO.atualizar(parente);
                            System.out.println("Parente atualizado com sucesso!✅");
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 3:
                        try {
                            System.out.println("Digite o ID do parente que deseja pesquisar:");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            Parente par = parenteDAO.buscarPorId(codigo);
                            System.out.println(par);
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Lista de Parentes ---");
                        List<Parente> lista = parenteDAO.listar();
                        for (Parente p : lista) {
                            System.out.println(p);
                        }
                        break;

                    case 5:
                        try{
                            System.out.println("Digite o código do parente que deseja excluir");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            parenteDAO.remover(codigo);
                            System.out.println("Parente removido com sucesso ☠️");

                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal...☁️");
                        break;

                    default:
                        System.out.println("Opção inválida!❌");
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                leia.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }//gerenciar parentes
    // ----------------------------------------------------------------------------------
//                             CONSULTA
// ----------------------------------------------------------------------------------

    private static void gerenciarConsultas(Scanner leia, ConsultaDAO consultaDao, PacienteDAO pacienteDao, MedicoDAO medicoDao) throws SQLException {
        int opcao;
        do {
            System.out.println("\n--- GERENCIAR CONSULTAS ---");
            System.out.println("Escolha: \n1-Cadastrar \n2-Atualizar \n3-Pesquisar por id \n4-Listar \n5-Remover \n0-Voltar ao Menu Principal");

            try {
                opcao = leia.nextInt();
                leia.nextLine();

                switch (opcao) {
                    case 1:
                        try {
                            Consulta consulta = lerConsulta(leia, pacienteDao, medicoDao);
                            consultaDao.cadastrar(consulta);
                            System.out.println("Consulta cadastrada com sucesso! ✅");
                        } catch (EntidadeNaoEncontradaException e) {
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Digite o ID da consulta que deseja atualizar: ");
                            int idAtualizar = leia.nextInt();
                            leia.nextLine();

                            Consulta consulta = lerConsulta(leia, pacienteDao, medicoDao);
                            consulta.setCodigoConsulta(idAtualizar);

                            consultaDao.atualizar(consulta);
                            System.out.println("Consulta atualizada com sucesso!✅");
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 3:
                        try {
                            System.out.println("Digite o ID da consulta que deseja pesquisar:");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            Consulta cons = consultaDao.buscarPorId(codigo);
                            System.out.println(cons);
                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Lista de Consultas ---");
                        List<Consulta> lista = consultaDao.listar();
                        for (Consulta c : lista) {
                            System.out.println(c);
                        }
                        break;

                    case 5:
                        try{
                            System.out.println("Digite o código da consulta que deseja excluir");
                            int codigo = leia.nextInt();
                            leia.nextLine();

                            consultaDao.remover(codigo);
                            System.out.println("Consulta removida com sucesso ☠️");

                        } catch (InputMismatchException e) {
                            System.err.println("ID inválido. Por favor, digite um número inteiro.❌");
                            leia.nextLine();
                        } catch (EntidadeNaoEncontradaException e){
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal...☁️");
                        break;

                    default:
                        System.out.println("Opção inválida!❌");
                }
            } catch (InputMismatchException e) {
                System.err.println("Valor inválido. Por favor, digite um número para a opção.❌");
                leia.nextLine();
                opcao = -1;
            }

        } while (opcao != 0);
    }//gerenciar consultas

// ----------------------------------------------------------------------------------
//                             MÉTODOS LER
// ----------------------------------------------------------------------------------

    private static Paciente lerPaciente(Scanner leia) {
        System.out.print("Digite o nome do paciente: ");
        String nome = leia.nextLine();
        System.out.print("Digite a data de nascimento (DD/MM/AAAA): ");
        String dataNascimentoStr = leia.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, FORMATTER);
        System.out.print("Digite o CPF: ");
        String cpf = leia.nextLine();
        System.out.println("Digite o email: ");
        String email = leia.nextLine();
        System.out.print("Digite o telefone principal: ");
        String nmrTelefone1 = leia.nextLine();
        System.out.print("Digite o telefone 2 (opcional): ");
        String nmrTelefone2 = leia.nextLine();
        System.out.print("Digite o telefone 3 (opcional): ");
        String nmrTelefone3 = leia.nextLine();
        return new Paciente(nome, dataNascimento, cpf, email, nmrTelefone1, nmrTelefone2, nmrTelefone3);
    }//ler paciente

    private static Medico lerMedico(Scanner leia) {
        System.out.print("Digite o nome do médico: ");
        String nome = leia.nextLine();
        System.out.print("Digite o CRM: ");
        String crm = leia.nextLine();
        System.out.print("Data de nascimento (DD/MM/AAAA): ");
        String dataNascimentoStr = leia.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, FORMATTER);
        System.out.print("Especialidade: ");
        String especialidade = leia.nextLine();
        double salario = 0;
        try {
            System.out.print("Salário: ");
            String salarioStr = leia.nextLine();
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            System.err.println("Valor de salário inválido. Usando 0.0.");
        }

        return new Medico(nome, crm, dataNascimento, especialidade, salario);
    }//ler medico

    private static Lembrete lerLembrete(Scanner leia) {
        System.out.print("Digite a mensagem do lembrete: ");
        String mensagem = leia.nextLine();
        System.out.println("Digite a data de envio (DD/mm/AAAA): ");
        String dataEnvioStr = leia.nextLine();
        LocalDate dataEnvio = LocalDate.parse(dataEnvioStr, FORMATTER);
        return new Lembrete(mensagem, dataEnvio);
    }// ler lembrete

    private static Parente lerParente(Scanner leia, PacienteDAO pacienteDAO, LembreteDAO lembreteDAO) throws EntidadeNaoEncontradaException, SQLException {
        System.out.print("Nome do parente: ");
        String nome = leia.nextLine();
        System.out.print("Descrição do Parentesco: ");
        String dsParentesco = leia.nextLine();
        System.out.print("Telefone 1: ");
        String nmrTelefone1 = leia.nextLine();
        System.out.print("Telefone 2 (opcional): ");
        String nmrTelefone2 = leia.nextLine();
        System.out.print("Telefone 3 (opcional): ");
        String nmrTelefone3 = leia.nextLine();

        int codPaciente = 0, codLembrete = 0;
        try {
            System.out.print("Código do Paciente (FK): ");
            String codPacienteStr = leia.nextLine();
            codPaciente = Integer.parseInt(codPacienteStr);

            pacienteDAO.buscarPorId(codPaciente);

        } catch (NumberFormatException e) {
            System.err.println("Código do Paciente inválido. Por favor, digite um número.❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Parente cancelado.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro de Validação: Esse ID de Paciente ainda não existe!❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Parente cancelado.");
        }

        try {
            System.out.print("Código do Lembrete (FK): ");
            String codLembreteStr = leia.nextLine();
            codLembrete = Integer.parseInt(codLembreteStr);

            lembreteDAO.buscarPorId(codLembrete);

        } catch (NumberFormatException e) {
            System.err.println("Código do Lembrete inválido. Por favor, digite um número.❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Parente cancelado.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro de Validação: Esse ID de Lembrete ainda não existe!❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Parente cancelado.");
        }
        return new Parente(nome, dsParentesco, nmrTelefone1, nmrTelefone2, nmrTelefone3, codLembrete, codPaciente);
    }//ler parente

    private static Consulta lerConsulta(Scanner leia, PacienteDAO pacienteDao, MedicoDAO medicoDao) throws EntidadeNaoEncontradaException, SQLException {
        System.out.print("Link da consulta: ");
        String link = leia.nextLine();
        System.out.print("Observações: ");
        String observacoes = leia.nextLine();
        System.out.print("Status da Consulta: ");
        String statusConsulta = leia.nextLine();

        LocalDate dataInicio = LocalDate.now(), dataFim = LocalDate.now();
        try {
            System.out.print("Data de Início (DD/MM/AAAA): ");
            dataInicio = LocalDate.parse(leia.nextLine(), FORMATTER);
            System.out.print("Data de Fim (DD/MM/AAAA): ");
            dataFim = LocalDate.parse(leia.nextLine(), FORMATTER);
        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Formato de data inválido. Usando data atual.");
        }

        int codigoPaciente = 0, codigoMedico = 0;

        try {
            System.out.print("Código do Paciente (FK): ");
            String codPacienteStr = leia.nextLine();
            codigoPaciente = Integer.parseInt(codPacienteStr);
            pacienteDao.buscarPorId(codigoPaciente);
        } catch (NumberFormatException e) {
            System.err.println("Código do Paciente inválido. Por favor, digite um número.❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Consulta cancelado.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro de Validação: Esse ID de Paciente ainda não existe!❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Consulta cancelado.");
        }

        try {
            System.out.print("Código do Médico (FK): ");
            String codMedicoStr = leia.nextLine();
            codigoMedico = Integer.parseInt(codMedicoStr);
            medicoDao.buscarPorId(codigoMedico);
        } catch (NumberFormatException e) {
            System.err.println("Código do Médico inválido. Por favor, digite um número.❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Consulta cancelado.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro de Validação: Esse ID de Médico ainda não existe!❌");
            throw new EntidadeNaoEncontradaException("Cadastro de Consulta cancelado.");
        }

        return new Consulta(dataInicio, dataFim, link, observacoes, statusConsulta, codigoPaciente, codigoMedico);
    }//ler consulta

}// public class main
