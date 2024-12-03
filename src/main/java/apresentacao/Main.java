package apresentacao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import negocio.Anotacao;
import negocio.AnotacaoService;
import persistencia.AnotacaoDAO;

public class Main {
    
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
        AnotacaoService anotacaoService = new AnotacaoService(anotacaoDAO);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1 - Criar Anotação");
            System.out.println("2 - Listar Anotações");
            System.out.println("3 - Excluir Anotação");
            System.out.println("4 - Atualizar Anotação");
            System.out.println("5 - Copiar Anotação");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            boolean ret;

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Cor: ");
                    String cor = scanner.nextLine();

                    System.out.print("Foto (URL ou caminho): ");
                    String foto = scanner.next();

                    try {
                        Path caminho = Paths.get(foto);
                        ret = anotacaoService.criarAnotacao(titulo, descricao, cor, Files.readAllBytes(caminho));

                        if(ret == true)
                            System.out.println("Anotação criada com sucesso.");

                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo de foto: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Erro ao criar anotação: " + e.getMessage());
                    }

                    break;
                case 2:
                    List<Anotacao> anotacoes = anotacaoService.listarAnotacoesOrdenadas();
                    for (Anotacao anotacao : anotacoes) {
                        System.out.println("Anotação: " + anotacao.getTitulo() + " com a cor: " + anotacao.getCor() + " e com a descrição " + anotacao.getDescricao());
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID da anotação para excluir: ");
                    int idExcluir = scanner.nextInt();
                    scanner.nextLine();

                    Anotacao anotacaoExcluir = anotacaoService.buscarPorId(idExcluir);

                    if (anotacaoExcluir == null)
                        break;

                    ret = anotacaoService.excluirAnotacao(idExcluir);
                    
                    if(ret == true)
                        System.out.println("Anotação excluída com sucesso.");
                    break;
                case 4:
                    System.out.print("Digite o ID da anotação para atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    Anotacao anotacao = anotacaoService.buscarPorId(idAtualizar);
    
                    if (anotacao == null)
                        break;

                    System.out.print("Título: ");
                    String novoTitulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String novaDescricao = scanner.nextLine();

                    System.out.print("Cor: ");
                    String novaCor = scanner.nextLine();

                    System.out.print("Foto (URL ou caminho): ");
                    String novaFoto = scanner.nextLine();
                    
                    try {
                        Path novoCaminho = Paths.get(novaFoto);
                        anotacao.setId(idAtualizar);
                        anotacao.setTitulo(novoTitulo);
                        anotacao.setDescricao(novaDescricao);
                        anotacao.setCor(novaCor);
                        anotacao.setFoto(Files.readAllBytes(novoCaminho));
                        ret = anotacaoService.atualizarAnotacao(anotacao);

                        if(ret == true)
                            System.out.println("Anotação atualizada com sucesso.");
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo de foto: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Erro ao atualizar anotação: " + e.getMessage());
                    }

                    System.out.println("Anotação atualizada.");

                    break;
                case 5:
                    System.out.print("Digite o ID da anotação a copiar: ");
                    int idCopiar = scanner.nextInt();

                    try {
                        ret = anotacaoService.copiarAnotacao(idCopiar);

                        if(ret == true)
                            System.out.println("Anotação copiada com sucesso.");
                    } catch (Exception e) {
                        System.out.println("Erro ao copiar anotação: " + e.getMessage());
                    }

                    break;
                case 6:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}