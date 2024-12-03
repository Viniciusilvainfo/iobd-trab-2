package negocio;

import java.sql.SQLException;
import java.util.List;

import persistencia.AnotacaoDAO;

public class AnotacaoService {
    private final AnotacaoDAO anotacaoDAO;

    public AnotacaoService(AnotacaoDAO anotacaoDAO) {
        this.anotacaoDAO = anotacaoDAO;
    }

    public boolean criarAnotacao(String titulo, String descricao, String cor, byte[] foto) {
        Anotacao anotacao = new Anotacao(titulo, descricao, cor, foto);

        try {
            if (!anotacaoDAO.inserir(anotacao)) {
                System.out.println("Falha ao inserir a anotação no banco de dados.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir anotação no banco de dados: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean atualizarAnotacao(Anotacao anotacao) {
        try {
            if (!anotacaoDAO.atualizar(anotacao)) {
                System.out.println("Nenhuma anotação encontrada para atualizar com o ID: " + anotacao.getId());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar anotação no banco de dados: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean excluirAnotacao(int id) {
        try {
            if (!anotacaoDAO.excluir(id)) {
                System.out.println("Nenhuma anotação encontrada para excluir com o ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir anotação no banco de dados: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean copiarAnotacao(int id) {
        Anotacao anotacao = anotacaoDAO.buscarPorId(id);

        if (anotacao == null) {
            System.out.println("Nenhuma anotação encontrada com o ID: " + id);
            return false;
        }

        return this.criarAnotacao(anotacao.getTitulo(), anotacao.getDescricao(), anotacao.getCor(), anotacao.getFoto());
    }

    public Anotacao buscarPorId(int id) {
        Anotacao anotacao = anotacaoDAO.buscarPorId(id);

        if (anotacao == null)
            System.out.println("Nenhuma anotação encontrada com o ID: " + id);

        return anotacao;
    }

    public List<Anotacao> listarAnotacoesOrdenadas() {
        return anotacaoDAO.listarOrdenado();
    }

    public List<Anotacao> listarAnotacoes() {
        return anotacaoDAO.listar();
    }
}