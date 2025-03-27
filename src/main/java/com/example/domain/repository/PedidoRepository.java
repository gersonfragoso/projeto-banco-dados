package com.example.domain.repository;

import com.example.domain.models.ItemPedido;
import com.example.domain.models.Livro;
import com.example.domain.models.Pedido;
import com.example.infra.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    public void criarTabelasPedidos() {
        String sqlPedidos = """
        CREATE TABLE IF NOT EXISTS pedidos (
            id BIGSERIAL PRIMARY KEY,
            data DATE NOT NULL,
            valor_total DECIMAL(10,2) NOT NULL
        )
    """;

        String sqlItensPedido = """
        CREATE TABLE IF NOT EXISTS itens_pedido (
            id BIGSERIAL PRIMARY KEY,
            pedido_id BIGINT NOT NULL,
            livro_id BIGINT NOT NULL,
            quantidade INT NOT NULL,
            preco_unitario DECIMAL(10,2) NOT NULL,
            FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
            FOREIGN KEY (livro_id) REFERENCES livros(id)
        )
    """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlPedidos);
            stmt.execute(sqlItensPedido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pedido criarPedido() {
        String sql = "INSERT INTO pedidos (data, valor_total) VALUES (?, 0)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();

            Pedido pedido = new Pedido();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getLong(1));
                    pedido.setData(LocalDate.now());
                    pedido.setValorTotal(BigDecimal.ZERO);
                }
            }

            return pedido;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pedido adicionarItemAoPedido(Pedido pedido, Livro livro, Integer quantidade) {
        String sqlItem = "INSERT INTO itens_pedido (pedido_id, livro_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        String sqlAtualizaValorTotal = "UPDATE pedidos SET valor_total = valor_total + ? WHERE id = ?";
        String sqlAtualizaEstoque = "UPDATE livros SET estoque = estoque - ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement inserirItem = conn.prepareStatement(sqlItem);
                 PreparedStatement updateValorTotal = conn.prepareStatement(sqlAtualizaValorTotal);
                 PreparedStatement updateEstoque = conn.prepareStatement(sqlAtualizaEstoque)) {

                BigDecimal valorItem = livro.getPreco().multiply(BigDecimal.valueOf(quantidade));

                inserirItem.setLong(1, pedido.getId());
                inserirItem.setLong(2, livro.getId());
                inserirItem.setInt(3, quantidade);
                inserirItem.setBigDecimal(4, livro.getPreco());
                inserirItem.executeUpdate();

                updateValorTotal.setBigDecimal(1, valorItem);
                updateValorTotal.setLong(2, pedido.getId());
                updateValorTotal.executeUpdate();

                updateEstoque.setInt(1, quantidade);
                updateEstoque.setLong(2, livro.getId());
                updateEstoque.executeUpdate();

                conn.commit();

                pedido.setValorTotal(pedido.getValorTotal().add(valorItem));
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return pedido;
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sqlPedidos = "SELECT * FROM pedidos ORDER BY data DESC";
        String sqlItensPedido = "SELECT ip.id, ip.pedido_id, ip.livro_id, ip.quantidade, ip.preco_unitario, " +
                "l.id AS livro_id, l.titulo, l.autor, l.preco, l.estoque " +
                "FROM itens_pedido ip " +
                "JOIN livros l ON ip.livro_id = l.id " +
                "WHERE ip.pedido_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsPedidos = stmt.executeQuery(sqlPedidos)) {

            while (rsPedidos.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rsPedidos.getLong("id"));
                pedido.setData(rsPedidos.getDate("data").toLocalDate());
                pedido.setValorTotal(rsPedidos.getBigDecimal("valor_total"));

                try (PreparedStatement pstmtItens = conn.prepareStatement(sqlItensPedido)) {
                    pstmtItens.setLong(1, pedido.getId());

                    try (ResultSet rsItens = pstmtItens.executeQuery()) {
                        while (rsItens.next()) {
                            ItemPedido item = new ItemPedido();
                            item.setId(rsItens.getLong("id"));
                            item.setQuantidade(rsItens.getInt("quantidade"));
                            item.setPrecoUnitario(rsItens.getBigDecimal("preco_unitario"));

                            Livro livro = new Livro(
                                    rsItens.getLong("livro_id"),
                                    rsItens.getString("titulo"),
                                    rsItens.getString("autor"),
                                    rsItens.getBigDecimal("preco"),
                                    rsItens.getInt("estoque")
                            );

                            item.setLivro(livro);
                            item.setPedido(pedido);
                            pedido.getItensPedidos().add(item);
                        }
                    }
                }

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}
