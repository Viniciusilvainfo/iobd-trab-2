package negocio;

import java.sql.SQLException;
import java.util.List;

import persistencia.AnotacaoDAO;

public class AnotacaoService {
    private final AnotacaoDAO anotacaoDAO;

    public AnotacaoService(AnotacaoDAO anotacaoDAO) {
        this.anotacaoDAO = anotacaoDAO;
    }

    public void criarAnotacao(String titulo, String descricao, String cor, byte[] foto) {
        Anotacao anotacao = new Anotacao(titulo, descricao, cor, foto);

        try {
            if (!anotacaoDAO.inserir(anotacao))
                throw new RuntimeException("Falha ao inserir a anotação no banco de dados.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir anotação no banco de dados: " + e.getMessage());
        }
    }

    public void atualizarAnotacao(Anotacao anotacao) {
        try {
            if (!anotacaoDAO.atualizar(anotacao))
                throw new RuntimeException("Nenhuma anotação encontrada para atualizar com o ID: " + anotacao.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar anotação no banco de dados: " + e.getMessage());
        }
    }

    public void excluirAnotacao(int id) {
        try {
            if (!anotacaoDAO.excluir(id))
                throw new RuntimeException("Nenhuma anotação encontrada para excluir com o ID: " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir anotação no banco de dados: " + e.getMessage());
        }
    }

    public void copiarAnotacao(int id) {
        Anotacao anotacao = anotacaoDAO.buscarPorId(id);

        if (anotacao == null)
            throw new RuntimeException("Nenhuma anotação encontrada com o ID: " + id);

        this.criarAnotacao(anotacao.getTitulo(), anotacao.getDescricao(), anotacao.getCor(), anotacao.getFoto());
    }

    public Anotacao buscarPorId(int id) {
        Anotacao anotacao = anotacaoDAO.buscarPorId(id);

        if (anotacao == null)
            throw new RuntimeException("Nenhuma anotação encontrada com o ID: " + id);

        return anotacao;
    }

    public List<Anotacao> listarAnotacoesOrdenadas() {
        return anotacaoDAO.listarOrdenado();
    }

    public List<Anotacao> listarAnotacoes() {
        return anotacaoDAO.listar();
    }
}