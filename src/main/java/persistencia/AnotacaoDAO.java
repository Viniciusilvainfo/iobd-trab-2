package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import negocio.Anotacao;

public class AnotacaoDAO {

    public boolean inserir(Anotacao anotacao) throws SQLException {
        String sql = "INSERT INTO anotacao (titulo, descricao, cor, foto, data_hora_criacao) VALUES(?, ?, ?, ?, ?) RETURNING id;";

        try (Connection connect = new ConexaoPostgreSQL().getConexao(); PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, anotacao.getTitulo());
            ps.setString(2, anotacao.getDescricao());
            ps.setString(3, anotacao.getCor());
            ps.setBytes(4, anotacao.getFoto());
            ps.setTimestamp(5, new Timestamp(anotacao.getDataHoraCriacao().getTime()));

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    anotacao.setId(id);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return anotacao.getId() != 0;
    }

    public boolean atualizar(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacao SET titulo = ?, descricao = ?, cor = ?, foto = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try (Connection connect = new ConexaoPostgreSQL().getConexao(); PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, anotacao.getTitulo());
            ps.setString(2, anotacao.getDescricao());
            ps.setString(3, anotacao.getCor());
            ps.setBytes(4, anotacao.getFoto());
            ps.setInt(5, anotacao.getId());
            linhasAfetadas = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return linhasAfetadas == 1;
    }
    
    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM anotacao WHERE id = ?";
        int linhasAfetadas = 0;

        try (Connection connect = new ConexaoPostgreSQL().getConexao(); PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, id);
            linhasAfetadas = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return linhasAfetadas == 1;
    }

    public List<Anotacao> listarOrdenado() {
        List<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT * FROM anotacao ORDER BY data_hora_criacao DESC";

        try (Connection connect = new ConexaoPostgreSQL().getConexao(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Anotacao anotacao = new Anotacao(rs.getString("titulo"), rs.getString("descricao"), rs.getString("cor"), rs.getBytes("foto"));
                anotacao.setId(rs.getInt("id"));
                anotacoes.add(anotacao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return anotacoes;
    }

    public List<Anotacao> listar() {
        List<Anotacao> anotacoes = new ArrayList<>();
        String sql = "SELECT * FROM anotacao";

        try (Connection connect = new ConexaoPostgreSQL().getConexao(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Anotacao anotacao = new Anotacao(rs.getString("titulo"), rs.getString("descricao"), rs.getString("cor"), rs.getBytes("foto"));
                anotacao.setId(rs.getInt("id"));
                anotacoes.add(anotacao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return anotacoes;
    }

    public Anotacao buscarPorId(int id) {
        String sql = "SELECT * FROM anotacao WHERE id = ?";
        Anotacao anotacao = null;

        try {
            Connection connect = new ConexaoPostgreSQL().getConexao();
            PreparedStatement ps = connect.prepareStatement(sql);
            
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    anotacao = new Anotacao(rs.getString("titulo"), rs.getString("descricao"), rs.getString("cor"), rs.getBytes("foto"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar anotação por ID: " + e.getMessage());
        }

        return anotacao;
    }
}