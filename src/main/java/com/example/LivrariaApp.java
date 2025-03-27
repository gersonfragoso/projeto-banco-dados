package com.example;

import com.example.domain.models.ItemPedido;
import com.example.domain.models.Livro;
import com.example.domain.models.Pedido;
import com.example.domain.repository.LivroRepository;
import com.example.domain.repository.PedidoRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
    Para o popular o banco com exemplos bastar descomentar:
    1: O metodo inicializarBaseDeDados();
    2: E onde ele é chamado no main;
*/

public class LivrariaApp {
    private static Scanner scanner = new Scanner(System.in);
    private static LivroRepository livroRepositorio = new LivroRepository();
    private static PedidoRepository pedidoRepositorio = new PedidoRepository();

    public static void main(String[] args) {
        livroRepositorio.criarTabelaLivros();
        pedidoRepositorio.criarTabelasPedidos();

        // inicializarBaseDeDados();  Apenas pra popular o banco
        exibirBoasVindas();

        while (true) {
            try {
                exibirMenu();
                int opcao = lerOpcao();

                switch (opcao) {
                    case 1 -> inserirLivro();
                    case 2 -> atualizarLivro();
                    case 3 -> deletarLivro();
                    case 4 -> listarLivros();
                    case 5 -> criarPedido();
                    case 6 -> adicionarItemAoPedido();
                    case 7 -> listarPedidos();
                    case 8 -> {
                        System.out.println("Obrigado por utilizar nossa livraria digital!");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
                aguardarEnter();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                aguardarEnter();
            }
        }
    }

    private static void exibirBoasVindas() {
        System.out.println("""
                    ==========================================
                    Bem-vindo ao Sistema de Gerenciamento de Livraria
                    ==========================================
                """);
    }

    /*
    private static void inicializarBaseDeDados() {
        if (livroRepositorio.listarLivros().isEmpty()) {
            List<Livro> livros = new ArrayList<>();
            livros.add(new Livro(null, "Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", new BigDecimal("39.90"), 50));
            livros.add(new Livro(null, "The Hobbit", "J.R.R. Tolkien", new BigDecimal("29.90"), 30));
            livros.add(new Livro(null, "A Game of Thrones", "George R.R. Martin", new BigDecimal("49.90"), 25));
            livros.add(new Livro(null, "Naruto, Vol. 1", "Masashi Kishimoto", new BigDecimal("19.90"), 100));
            livros.add(new Livro(null, "Attack on Titan, Vol. 1", "Hajime Isayama", new BigDecimal("22.90"), 80));
            livros.add(new Livro(null, "The Da Vinci Code", "Dan Brown", new BigDecimal("34.90"), 40));
            livros.add(new Livro(null, "The Name of the Wind", "Patrick Rothfuss", new BigDecimal("44.90"), 35));
            livros.add(new Livro(null, "One Piece, Vol. 1", "Eiichiro Oda", new BigDecimal("19.90"), 90));
            livros.add(new Livro(null, "Dune", "Frank Herbert", new BigDecimal("39.90"), 45));
            livros.add(new Livro(null, "Fullmetal Alchemist, Vol. 1", "Hiromu Arakawa", new BigDecimal("21.90"), 70));
            livros.add(new Livro(null, "The Hunger Games", "Suzanne Collins", new BigDecimal("29.90"), 60));
            livros.add(new Livro(null, "Death Note, Vol. 1", "Tsugumi Ohba", new BigDecimal("19.90"), 85));
            livros.add(new Livro(null, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", new BigDecimal("39.90"), 50));
            livros.add(new Livro(null, "The Lord of the Rings: The Fellowship of the Ring", "J.R.R. Tolkien", new BigDecimal("34.90"), 30));
            livros.add(new Livro(null, "Demon Slayer, Vol. 1", "Koyoharu Gotouge", new BigDecimal("22.90"), 75));
            livros.add(new Livro(null, "To Kill a Mockingbird", "Harper Lee", new BigDecimal("24.90"), 40));
            livros.add(new Livro(null, "My Hero Academia, Vol. 1", "Kohei Horikoshi", new BigDecimal("19.90"), 65));
            livros.add(new Livro(null, "1984", "George Orwell", new BigDecimal("27.90"), 50));
            livros.add(new Livro(null, "Bleach, Vol. 1", "Tite Kubo", new BigDecimal("19.90"), 60));
            livros.add(new Livro(null, "The Catcher in the Rye", "J.D. Salinger", new BigDecimal("22.90"), 35));
            livros.add(new Livro(null, "Vinland Saga - Volume 1", "Makoto Yukimura", new BigDecimal("22.90"), 50));
            livros.add(new Livro(null, "Blade of the Immortal - Volume 1", "Hiroaki Samura", new BigDecimal("24.90"), 40));
            livros.add(new Livro(null, "Gantz - Volume 1", "Hiroya Oku", new BigDecimal("19.90"), 60));
            livros.add(new Livro(null, "Lone Wolf and Cub - Volume 1", "Kazuo Koike", new BigDecimal("29.90"), 35));
            livros.add(new Livro(null, "Hunter x Hunter - Volume 1", "Yoshihiro Togashi", new BigDecimal("19.90"), 70));
            livros.add(new Livro(null, "Berserk - Volume 1", "Kentaro Miura", new BigDecimal("24.90"), 60));
            livros.add(new Livro(null, "Vagabond - Volume 1", "Takehiko Inoue", new BigDecimal("22.90"), 50));
            livros.add(new Livro(null, "Kingdom - Volume 1", "Yasuhisa Hara", new BigDecimal("19.90"), 70));

            for (Livro livro : livros) {
                livroRepositorio.salvarLivro(livro);
            }
        }
    }

     */

    private static void exibirMenu() {
        System.out.println("""
                === MENU ===
                1. Inserir Novo Livro
                2. Atualizar Livro
                3. Deletar Livro
                4. Listar Livros
                5. Criar Pedido
                6. Adicionar Item ao Pedido
                7. Listar Pedidos
                8. Sair
                --------------------
                Escolha uma opção: """);
    }

    private static int lerOpcao() {
        while (true) {
            System.out.print("> ");
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido.");
            }
        }
    }

    private static void aguardarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    private static void inserirLivro() {
        System.out.println("\n=== Inserir Novo Livro ===");
        if (voltarAoMenu()) {
            return;
        }
        String titulo = obterEntradaObrigatoria("Título", 1, 100);
        if (livroRepositorio.listarLivros().stream().anyMatch(l -> l.getTitulo().equalsIgnoreCase(titulo))) {
            System.out.println("Erro: Já existe um livro com este título.");
            return;
        }

        String autor = obterEntradaObrigatoria("Autor", 2, 100);
        BigDecimal preco = obterPreco();
        Integer estoque = obterEstoque();

        Livro livro = new Livro(null, titulo, autor, preco, estoque);
        try {
            Livro salvo = livroRepositorio.salvarLivro(livro);
            System.out.println("Livro cadastrado com sucesso! ID: " + salvo.getId());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    private static void atualizarLivro() {
        System.out.println("\n=== Atualizar Livro ===");
        if (voltarAoMenu()) {
            return;
        }
        Long id = obterIdValido("ID do Livro a atualizar");

        Livro livroAtual = livroRepositorio.listarLivros().stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
        if (livroAtual == null) {
            System.out.println("Erro: Livro não encontrado.");
            return;
        }

        Livro livroAtualizado = new Livro();

        System.out.print("Novo Título (deixe em branco para manter): ");
        String novoTitulo = scanner.nextLine().trim();
        if (!novoTitulo.isEmpty()) {
            if (novoTitulo.length() < 1 || novoTitulo.length() > 100) {
                System.out.println("Erro: Título deve ter entre 1 e 100 caracteres.");
                return;
            }
            livroAtualizado.setTitulo(novoTitulo);
        } else {
            livroAtualizado.setTitulo(livroAtual.getTitulo());
        }

        System.out.print("Novo Autor (deixe em branco para manter): ");
        String novoAutor = scanner.nextLine().trim();
        if (!novoAutor.isEmpty()) {
            if (novoAutor.length() < 2 || novoAutor.length() > 100) {
                System.out.println("Erro: Autor deve ter entre 2 e 100 caracteres.");
                return;
            }
            livroAtualizado.setAutor(novoAutor);
        } else {
            livroAtualizado.setAutor(livroAtual.getAutor());
        }

        System.out.print("Novo Preço (deixe em branco para manter): ");
        String novoPrecoStr = scanner.nextLine().trim();
        if (!novoPrecoStr.isEmpty()) {
            try {
                BigDecimal novoPreco = new BigDecimal(novoPrecoStr.replace(",", "."));
                if (novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Erro: Preço deve ser maior que zero.");
                    return;
                }
                livroAtualizado.setPreco(novoPreco);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Formato de preço inválido.");
                return;
            }
        } else {
            livroAtualizado.setPreco(livroAtual.getPreco());
        }

        System.out.print("Novo Estoque (deixe em branco para manter): ");
        String novoEstoqueStr = scanner.nextLine().trim();
        if (!novoEstoqueStr.isEmpty()) {
            try {
                int novoEstoque = Integer.parseInt(novoEstoqueStr);
                if (novoEstoque < 0) {
                    System.out.println("Erro: Estoque não pode ser negativo.");
                    return;
                }
                livroAtualizado.setEstoque(novoEstoque);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Estoque deve ser um número inteiro.");
                return;
            }
        } else {
            livroAtualizado.setEstoque(livroAtual.getEstoque());
        }

        try {
            Livro atualizado = livroRepositorio.atualizarLivro(id, livroAtualizado);
            System.out.println("Livro atualizado com sucesso: " + atualizado);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    private static void deletarLivro() {
        System.out.println("\n=== Deletar Livro ===");
        if (voltarAoMenu()) {
            return;
        }
        Long id = obterIdValido("ID do Livro a deletar");

        if (livroRepositorio.listarLivros().stream().noneMatch(l -> l.getId().equals(id))) {
            System.out.println("Erro: Livro não encontrado.");
            return;
        }

        try {
            boolean deletado = livroRepositorio.deletarLivro(id);
            if (deletado) {
                System.out.println("Livro deletado com sucesso!");
            } else {
                System.out.println("Não foi possível deletar o livro.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar livro: " + e.getMessage());
        }
    }

    private static void listarLivros() {
        System.out.println("\n=== Lista de Livros ===");
        List<Livro> livros = livroRepositorio.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            gerarRelatorioLivros(livros);
        }
    }

    private static void gerarRelatorioLivros(List<Livro> livros) {
        BigDecimal valorTotalEstoque = BigDecimal.ZERO;
        int quantidadeTotalLivros = 0;

        System.out.println("Relatório de Livros:");
        System.out.println("-------------------");
        for (Livro livro : livros) {
            System.out.printf("ID: %d | Título: %s | Autor: %s | Preço: %.2f | Estoque: %d%n", livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getPreco(), livro.getEstoque());

            valorTotalEstoque = valorTotalEstoque.add(livro.getPreco().multiply(BigDecimal.valueOf(livro.getEstoque())));
            quantidadeTotalLivros += livro.getEstoque();
        }

        System.out.println("\nResumo:");
        System.out.println("Quantidade total de títulos: " + livros.size());
        System.out.println("Quantidade total de livros em estoque: " + quantidadeTotalLivros);
        System.out.printf("Valor total do estoque: R$ %.2f%n", valorTotalEstoque);
    }

    private static void criarPedido() {
        System.out.println("\n=== Criar Pedido ===");
        if (voltarAoMenu()) {
            return;
        }
        try {
            Pedido pedido = pedidoRepositorio.criarPedido();
            System.out.println("Pedido criado com sucesso! ID: " + pedido.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar pedido: " + e.getMessage());
        }
    }

    private static void adicionarItemAoPedido() {
        System.out.println("\n=== Adicionar Item ao Pedido ===");
        if (voltarAoMenu()) {
            return;
        }
        Long pedidoId = obterIdValido("ID do Pedido");
        Long livroId = obterIdValido("ID do Livro");

        Pedido pedido = pedidoRepositorio.listarPedidos().stream().filter(p -> p.getId().equals(pedidoId)).findFirst().orElse(null);
        if (pedido == null) {
            System.out.println("Erro: Pedido não encontrado.");
            return;
        }

        Livro livro = livroRepositorio.listarLivros().stream().filter(l -> l.getId().equals(livroId)).findFirst().orElse(null);
        if (livro == null) {
            System.out.println("Erro: Livro não encontrado.");
            return;
        }

        while (true) {
            Integer quantidade = obterQuantidade();
            if (quantidade > livro.getEstoque()) {
                System.out.println("Erro: Quantidade indisponível em estoque! Estoque disponível: " + livro.getEstoque());
                System.out.println("Por favor, insira uma quantidade menor.");
            } else {
                try {
                    Pedido atualizado = pedidoRepositorio.adicionarItemAoPedido(pedido, livro, quantidade);
                    System.out.println("Item adicionado! Valor total do pedido: " + atualizado.getValorTotal());
                    break;
                } catch (Exception e) {
                    System.out.println("Erro ao adicionar item ao pedido: " + e.getMessage());
                    break;
                }
            }
        }
    }

    private static void listarPedidos() {
        System.out.println("\n=== Lista de Pedidos ===");
        List<Pedido> pedidos = pedidoRepositorio.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            gerarRelatorioPedidos(pedidos);
        }
    }

    private static void gerarRelatorioPedidos(List<Pedido> pedidos) {
        BigDecimal valorTotalVendas = BigDecimal.ZERO;
        int quantidadeTotalPedidos = pedidos.size();
        int quantidadeTotalLivrosDistintos = 0;
        int quantidadeTotalLivrosVendidos = 0;

        Set<String> livrosDistintos = new HashSet<>();

        System.out.println("Relatório de Pedidos:");
        System.out.println("--------------------");

        for (Pedido pedido : pedidos) {
            System.out.printf("ID: %d | Data: %s | Valor Total: %.2f%n", pedido.getId(), pedido.getData(), pedido.getValorTotal());

            valorTotalVendas = valorTotalVendas.add(pedido.getValorTotal());

            if (!pedido.getItensPedidos().isEmpty()) {
                for (ItemPedido item : pedido.getItensPedidos()) {
                    livrosDistintos.add(item.getLivro().getTitulo());
                    quantidadeTotalLivrosVendidos += item.getQuantidade();

                    System.out.printf("  - Livro: %s | Quantidade: %d | Preço Unitário: %.2f%n", item.getLivro().getTitulo(), item.getQuantidade(), item.getPrecoUnitario());
                }
            }
        }

        quantidadeTotalLivrosDistintos = livrosDistintos.size();

        System.out.println("\nResumo:");
        System.out.println("Quantidade total de pedidos: " + quantidadeTotalPedidos);
        System.out.println("Quantidade total de livros distintos vendidos: " + quantidadeTotalLivrosDistintos);
        System.out.println("Quantidade total de livros vendidos: " + quantidadeTotalLivrosVendidos);
        System.out.printf("Valor total de vendas: R$ %.2f%n", valorTotalVendas);
    }

    // Métodos auxiliares para validações

    private static String obterEntradaObrigatoria(String campo, int minLength, int maxLength) {
        while (true) {
            System.out.print(campo + ": ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Erro: " + campo + " não pode ser vazio.");
                continue;
            }
            if (entrada.length() < minLength || entrada.length() > maxLength) {
                System.out.printf("Erro: %s deve ter entre %d e %d caracteres.%n", campo, minLength, maxLength);
                continue;
            }
            return entrada;
        }
    }

    private static BigDecimal obterPreco() {
        while (true) {
            System.out.print("Preço: ");
            try {
                String precoStr = scanner.nextLine().replace(",", ".").trim();
                BigDecimal preco = new BigDecimal(precoStr);

                if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Erro: Preço deve ser maior que zero.");
                    continue;
                }
                return preco;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Formato de preço inválido. Use um número válido, ex: 45.00");
            }
        }
    }

    private static Integer obterEstoque() {
        while (true) {
            System.out.print("Estoque: ");
            try {
                String estoqueStr = scanner.nextLine().trim();
                int estoque = Integer.parseInt(estoqueStr);

                if (estoque < 0) {
                    System.out.println("Erro: Estoque não pode ser negativo.");
                    continue;
                }
                return estoque;
            } catch (NumberFormatException e) {
                System.out.println("Erro: O estoque deve ser um número inteiro válido!");
            }
        }
    }

    private static Long obterIdValido(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            try {
                String idStr = scanner.nextLine().trim();
                long id = Long.parseLong(idStr);

                if (id <= 0) {
                    System.out.println("Erro: " + campo + " deve ser um número positivo.");
                    continue;
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.println("Erro: " + campo + " inválido. Por favor, insira um número válido.");
            }
        }
    }

    private static Integer obterQuantidade() {
        while (true) {
            System.out.print("Quantidade: ");
            try {
                String quantidadeStr = scanner.nextLine().trim();
                int quantidade = Integer.parseInt(quantidadeStr);

                if (quantidade <= 0) {
                    System.out.println("Erro: Quantidade deve ser maior que zero.");
                    continue;
                }
                return quantidade;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Quantidade inválida. Por favor, insira um número inteiro válido.");
            }
        }
    }

    private static boolean voltarAoMenu() {
        System.out.print("Deseja voltar ao menu inicial? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }
}