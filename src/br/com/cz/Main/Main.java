package br.com.cz.Main;
import br.com.cz.Controller.AutenticacaoController;
import br.com.cz.Controller.ContaBancariaController;
import br.com.cz.Model.ContaBancaria;
import br.com.cz.Model.Pessoal;
import br.com.cz.Model.Profissional;
import br.com.cz.Model.Utilizador;
import br.com.cz.Util.SistemaAplicacao;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AutenticacaoController autenticacaoController = new AutenticacaoController();
        ContaBancariaController contaBancariaController = new ContaBancariaController();
        SistemaAplicacao sistema = new SistemaAplicacao();

        while (true) {
            String op = sistema.menuInicial();

            if (op.equals("1")) {
                Scanner ler = new Scanner(System.in);

                System.out.println("""
                        1 - Pessoal
                        2 - Profissional""");

                System.out.print("Selecione uma opcao de Cadastro: ");
                int op2 = ler.nextInt();
                ler.nextLine();

                if (op2 == 1) {
                    System.out.print("Digite seu nome: ");
                    String nome = ler.nextLine();
                    System.out.print("Digite sua idade: ");
                    int idade = ler.nextInt();
                    ler.nextLine();

                    Pessoal contaPessoal = new Pessoal(nome, idade);

                    System.out.print("Digite seu nome de usuario: ");
                    String nomeUsuario = ler.nextLine();
                    System.out.print("Digite seu email: ");
                    String email = ler.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = ler.nextLine();

                    Utilizador<Pessoal> utilizador = new Utilizador<>(nomeUsuario, email, senha, contaPessoal);

                    boolean cadastro = autenticacaoController.adicionarUtilizador(utilizador);

                    if (cadastro) {
                        System.out.println("=-=- CADASTRO REALIZADO COM SUCESSO -=-=");
                    } else {
                        System.out.println("=-=- CADASTRO NÃO REALIZADO -=-=");
                    }

                } else if (op2 == 2) {
                    System.out.print("Digite seu nome: ");
                    String nome = ler.nextLine();
                    System.out.print("Digite sua idade: ");
                    int idade = ler.nextInt();
                    ler.nextLine();
                    System.out.print("Digite sua Profissao: ");
                    String profissao = ler.nextLine();

                    Profissional contaProfissional = new Profissional(nome, idade, profissao);

                    System.out.print("Digite seu nome de usuario: ");
                    String nomeUsuario = ler.nextLine();
                    System.out.print("Digite seu email: ");
                    String email = ler.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = ler.nextLine();

                    Utilizador<Profissional> utilizador = new Utilizador<>(nomeUsuario, email, senha, contaProfissional);


                    boolean cadastro = autenticacaoController.adicionarUtilizador(utilizador);

                    if (cadastro) {
                        System.out.println("=-=- CADASTRO REALIZADO COM SUCESSO -=-=");
                    } else {
                        System.out.println("=-=- CADASTRO NÃO REALIZADO -=-=");
                    }
                }

            }
            else if (op.equals("2")) {
                while (true) {
                    Scanner ler = new Scanner(System.in);
                    System.out.print("Digite seu nome de Usuario: ");
                    String nomeUsuario = ler.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = ler.nextLine();

                    boolean login = autenticacaoController.buscarUtilizador(nomeUsuario, senha);

                    if (login) {
                        op = sistema.menuPrincipal();
                        if (op.equals("1")) {
                            while (true) {
                                op = sistema.menuConta();
                                if (op.equals("1")) {
                                    System.out.println("-=-= CADASTRANDO CONTA BANCÁRIA =-=-");
                                    System.out.print("Digite o nome da instituição: ");
                                    String instituicao = ler.nextLine();
                                    System.out.print("Digite o saldo da conta: ");
                                    double saldoConta = ler.nextDouble();
                                    ler.nextLine();
                                    ContaBancaria contaBancaria = new ContaBancaria(instituicao, saldoConta, autenticacaoController.buscarUtilizador(nomeUsuario).getIdUtilizador());
                                    boolean cadastroConta = contaBancariaController.adicionarConta(contaBancaria);

                                    if (cadastroConta) {
                                        System.out.println("=-=- CONTA CADASTRADA COM SUCESSO -=-=");
                                    } else {
                                        System.out.println("=-=- NÃO FOI POSSÍVEL REALIZAR O CADASTRO -=-=");
                                    }
                                } else if (op.equals("2")) {
                                    System.out.println("=-=- CONTAS DO UTILIZADOR -=-=");
                                    ArrayList<ContaBancaria> contasBancarias = contaBancariaController.listarContas(autenticacaoController.buscarUtilizador(nomeUsuario).getIdUtilizador());

                                    for (ContaBancaria contasBancaria : contasBancarias) {
                                        System.out.print("Instituição: " + contasBancaria.getInstituicao());
                                        System.out.printf("Saldo da Conta: %.2f", contasBancaria.getSaldoConta());
                                    }

                                } else if (op.equals("3")) {
                                    System.out.println("=-=- REMOVER CONTA -=-=");
                                    System.out.print("Digite o nome da Instituíção que deseja remover: ");
                                    String instuicao = ler.nextLine();

                                    boolean removerConta = contaBancariaController.removerConta(instuicao);

                                    if (removerConta) {
                                        System.out.println("=-=- CONTA REMOVIDA COM SUCESSO -=-=");
                                    } else {
                                        System.out.println("=-=- NÃO FOI POSSÍVEL REMOVER A CONTA -=-=");
                                    }
                                } else if (op.equals("4")) {
                                    System.out.println("=-=- ATUALIZAR CONTA -=-=");
                                    System.out.println("Digite o nome da Insittuição que deseja atualizar: ");
                                    String instituicaoAtual = ler.nextLine();

                                    System.out.print("Deseja atualizar apenas o saldo? (Y/N): ");
                                    String resp = ler.next();

                                    switch (resp) {
                                        case "Y":
                                            System.out.println("Digite o novo Saldo: ");
                                            double saldoNovo = ler.nextDouble();
                                            ContaBancaria contaBancariaAtualizar = new ContaBancaria(instituicaoAtual, saldoNovo, autenticacaoController.buscarUtilizador(nomeUsuario).getIdUtilizador());
                                            contaBancariaController.atualizarConta(instituicaoAtual, contaBancariaAtualizar);
                                            break;
                                        case "N":
                                            System.out.print("Digite o novo nome da Instituição: ");
                                            String instituicaoNovo = ler.nextLine();
                                            System.out.print("Digite o novo Saldo: ");
                                            saldoNovo = ler.nextDouble();
                                            contaBancariaAtualizar = new ContaBancaria(instituicaoNovo, saldoNovo, autenticacaoController.buscarUtilizador(nomeUsuario).getIdUtilizador());
                                            contaBancariaController.atualizarConta(instituicaoAtual, contaBancariaAtualizar);
                                            break;
                                        default:
                                            System.out.println("Opção Inválida");
                                    }

                                } else if (op.equals("0")) {
                                    System.out.println("-=-= VOLTAR =-=-");
                                    break;
                                }

                            }
                        } else if (op.equals("2")) {
                            while (true) {
                                op = sistema.menuTransacao();
                                if (op.equals("1")) {

                                } else if (op.equals("2")) {

                                } else if (op.equals("3")) {

                                } else if (op.equals("0")) {
                                    System.out.println("-=-= VOLTAR =-=-");
                                    break;
                                }
                            }

                        } else if (op.equals("3")) {
                            while (true) {
                                op = sistema.menuInvestimento();
                                if (op.equals("1")) {

                                } else if (op.equals("2")) {

                                } else if (op.equals("3")) {

                                } else if (op.equals("0")) {
                                    System.out.println("-=-= VOLTAR =-=-");
                                    break;
                                }
                            }
                        } else if (op.equals("0")) {
                            System.out.println("-=-= VOLTAR =-=-");
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            else if (op.equals("3")) {
                Scanner ler = new Scanner(System.in);
                System.out.print("Digite seu nome de Usuario: ");
                String nomeUsuario = ler.nextLine();
                System.out.print("Digite sua senha: ");
                String senha = ler.nextLine();

                boolean excluir = autenticacaoController.excluirUtilizador(nomeUsuario, senha);

                if (excluir) {
                    System.out.println("=-=- CONTA EXCLUIDA -=-=");
                } else {
                    System.out.println("=-=- FALHA NA EXCLUSAO -=-=");
                }

            }
            else if (op.equals("4")) {
                Scanner ler = new Scanner(System.in);

                System.out.print("Digite seu nome de Usuario atual: ");
                String nomeUsuarioAtual = ler.nextLine();
                System.out.print("Digite sua senha atual: ");
                String senhaAtual = ler.nextLine();

                System.out.println("""
                        1 - Pessoal
                        2 - Profissional""");

                System.out.print("Selecione uma opcao de Cadastro: ");
                int op2 = ler.nextInt();
                ler.nextLine();

                if (op2 == 1) {
                    System.out.print("Digite seu novo nome: ");
                    String nome = ler.nextLine();
                    System.out.print("Digite sua nova idade: ");
                    int idade = ler.nextInt();
                    ler.nextLine();

                    Pessoal contaPessoal = new Pessoal(nome, idade);

                    System.out.print("Digite seu novo nome de usuario: ");
                    String nomeUsuario = ler.nextLine();
                    System.out.print("Digite seu novo email: ");
                    String email = ler.nextLine();
                    System.out.print("Digite sua nova senha: ");
                    String senha = ler.nextLine();

                    Utilizador<Pessoal> utilizadorNovo = new Utilizador<>(nomeUsuario, email, senha, contaPessoal);

                    boolean atualizacao = autenticacaoController.atualizarUtilizador(nomeUsuarioAtual, senhaAtual, utilizadorNovo);

                    if (atualizacao) {
                        System.out.println("=-=- ATUALIZACAO REALIZADA COM SUCESSO -=-=");
                    } else {
                        System.out.println("=-=- ATUALIZACAO NÃO REALIZADA -=-=");
                        ;
                    }

                } else if (op2 == 2) {
                    System.out.print("Digite seu novo nome: ");
                    String nome = ler.nextLine();
                    System.out.print("Digite sua nova idade: ");
                    int idade = ler.nextInt();
                    ler.nextLine();
                    System.out.print("Digite sua nova Profissao: ");
                    String profissao = ler.nextLine();

                    Profissional contaProfissional = new Profissional(nome, idade, profissao);

                    System.out.print("Digite seu novo nome de usuario: ");
                    String nomeUsuario = ler.nextLine();
                    System.out.print("Digite seu novo email: ");
                    String email = ler.nextLine();
                    System.out.print("Digite sua nova senha: ");
                    String senha = ler.nextLine();

                    Utilizador<Profissional> utilizadorNovo = new Utilizador<>(nomeUsuario, email, senha, contaProfissional);

                    boolean atualizacao = autenticacaoController.atualizarUtilizador(nomeUsuarioAtual, senhaAtual, utilizadorNovo);

                    if (atualizacao) {
                        System.out.println("=-=- ATUALIZACAO REALIZADA COM SUCESSO -=-=");
                    } else {
                        System.out.println("=-=- ATUALIZACAO NÃO REALIZADA -=-=");
                    }

                }
            }
            else if (op.equals("0")) {
                System.out.println("-=-= SAIR =-=-");
                break;
            } else {
                System.out.println("=-=- OPCAO INVALIDA-=-=");
            }
        }
    }
}
