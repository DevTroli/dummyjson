package com.dummyjson.API;

import com.dummyjson.API.model.Carrinho;
import com.dummyjson.API.model.ProdutoCarrinho;
import com.dummyjson.API.model.RespostaCarrinhos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    RestClient dummyJsonClient(RestClient.Builder builder) {
        return builder.baseUrl("https://dummyjson.com").build();
    }
}

@RestController
class CarrinhoController {
    private final CarrinhoService service;

    CarrinhoController(CarrinhoService service) {
        this.service = service;
    }

    @GetMapping("/testar")
    String testarStreams() {
        service.executarOperacoesStream();
        return "Processado com sucesso! Confira o console/terminal.";
    }
}

@Service
class CarrinhoService {
    private final RestClient dummyJsonClient;

    CarrinhoService(RestClient dummyJsonClient) {
        this.dummyJsonClient = dummyJsonClient;
    }

    void executarOperacoesStream() {
        RespostaCarrinhos dados = dummyJsonClient.get()
                .uri("/carts?limit=0")
                .retrieve()
                .body(RespostaCarrinhos.class);

        if (dados == null || dados.getCarts() == null) {
            return;
        }

        List<Carrinho> lista = dados.getCarts();

        System.out.println("\n--- Filter: Economia > R$50 ---");
        lista.stream()
                .filter(c -> c.getEconomia() > 50.0)
                .forEach(c -> System.out.println("Carrinho: " + c.getId()
                        + " economizou: " + c.getEconomia()));

        System.out.println("\n--- FlatMap + Map: Nomes em Maiúsculo ---");
        lista.stream()
                .filter(c -> c.getProducts() != null)
                .flatMap(c -> c.getProducts().stream())
                .map(ProdutoCarrinho::getTitle)
                .filter(title -> title != null)
                .map(String::toUpperCase)
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\n--- Sorted: Preço Decrescente ---");
        lista.stream()
                .filter(c -> c.getProducts() != null)
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getPrice() != null)
                .sorted(Comparator.comparing(ProdutoCarrinho::getPrice).reversed())
                .limit(5)
                .forEach(p -> System.out.println(p.getTitle() + " - R$" + p.getPrice()));

        int totalItens = lista.stream()
                .map(Carrinho::getTotalQuantity)
                .filter(quantity -> quantity != null)
                .reduce(0, Integer::sum);
        System.out.println("\n--- Reduce (Total Itens): " + totalItens + " ---");

        Map<Double, List<ProdutoCarrinho>> agrupaPreco = lista.stream()
                .filter(c -> c.getProducts() != null)
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getPrice() != null)
                .collect(Collectors.groupingBy(ProdutoCarrinho::getPrice));
        System.out.println("\n--- GroupingBy (Grupos Criados): " + agrupaPreco.size() + " ---");

        encontrarProdutoMaisCaro(lista).ifPresent(produto ->
                System.out.println("\n--- Produto mais caro: " + produto.getTitle()
                        + " - R$" + produto.getPrice() + " ---"));
    }

    /** Retorna o produto de maior preço, ou Optional.empty() quando não há produtos válidos. */
    Optional<ProdutoCarrinho> encontrarProdutoMaisCaro(List<Carrinho> carrinhos) {
        if (carrinhos == null) {
            return Optional.empty();
        }

        return carrinhos.stream()
                .filter(c -> c != null && c.getProducts() != null)
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p != null && p.getPrice() != null)
                .max(Comparator.comparing(ProdutoCarrinho::getPrice));
    }
}
