package com.example.domain.repository;

import com.example.domain.models.Livro;
import com.example.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    public void criarTabelaLivros() {
        String sql = """
        CREATE TABLE IF NOT EXISTS livros (
            id BIGSERIAL PRIMARY KEY,
            titulo VARCHAR(100) NOT NULL,
            autor VARCHAR(100) NOT NULL,
            preco DECIMAL(10,2) NOT NULL,
            estoque INT NOT NULL
        )
    """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Livro salvarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, preco, estoque) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setBigDecimal(3, livro.getPreco());
            pstmt.setInt(4, livro.getEstoque());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getLong(1));
                }
            }

            return livro;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY titulo";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getLong("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("estoque")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }

    public Livro atualizarLivro(Long id, Livro livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, preco = ?, estoque = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setBigDecimal(3, livro.getPreco());
            pstmt.setInt(4, livro.getEstoque());
            pstmt.setLong(5, id);

            pstmt.executeUpdate();
            livro.setId(id);
            return livro;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletarLivro(Long id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

