package com.dummyjson.API;

import com.dummyjson.API.model.Carrinho;
import com.dummyjson.API.model.ProdutoCarrinho;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void deveEncontrarProdutoDeMaiorValorComMaxOptional() {
        Carrinho carrinho = new Carrinho();
        carrinho.setProducts(List.of(
                new ProdutoCarrinho("Produto barato", 10.0),
                new ProdutoCarrinho("Produto mais caro", 99.90),
                new ProdutoCarrinho("Produto intermediário", 45.0)
        ));

        CarrinhoService service = new CarrinhoService(null);
        Optional<ProdutoCarrinho> resultado = service.encontrarProdutoMaisCaro(List.of(carrinho));

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitle()).isEqualTo("Produto mais caro");
        assertThat(resultado.get().getPrice()).isEqualTo(99.90);
    }

    @Test
    void deveRetornarOptionalVazioSemProdutos() {
        Carrinho carrinho = new Carrinho();
        carrinho.setProducts(List.of());

        CarrinhoService service = new CarrinhoService(null);

        assertThat(service.encontrarProdutoMaisCaro(List.of(carrinho))).isEmpty();
        assertThat(service.encontrarProdutoMaisCaro(null)).isEmpty();
    }
}
