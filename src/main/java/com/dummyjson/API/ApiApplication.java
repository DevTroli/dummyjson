import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("https://dummyjson.com");
    }
}

@RestController
class CarrinhoController {
    private final CarrinhoService service;

    public CarrinhoController(CarrinhoService service) { this.service = service; }

    @GetMapping("/testar")
    public String testarStreams() {
        service.executarOperacoesStream();
        return "Processado com sucesso! Confira o console/terminal.";
    }
}

@Service
class CarrinhoService {
    private final WebClient webClient;

    public CarrinhoService(WebClient webClient) { this.webClient = webClient; }

    public void ejecutarOperacoesStream() {
        RespostaCarrinhos dados = webClient.get()
                .uri("/carts?limit=0")
                .retrieve()
                .bodyToMono(RespostaCarrinhos.class)
                .block();

        if (dados == null || dados.getCarts() == null) return;
        List<Carrinho> lista = dados.getCarts();

        System.out.println("\n--- Filter: Economia > R$50 ---");
        lista.stream()
                .filter(c -> c.getEconomia() > 50.0)
                .forEach(c -> System.out.println("Carrinho: " + c.getId() + " economizou: " + c.getEconomia()));

        System.out.println("\n--- FlatMap + Map: Nomes em Maiúsculo ---");
        lista.stream()
                .flatMap(c -> c.getProducts().stream())
                .map(p -> p.getTitle().toUpperCase())
                .limit(5)
                .forEach(System.out.println);

        System.out.println("\n--- Sorted: Preço Decrescente ---");
        lista.stream()
                .flatMap(c -> c.getProducts().stream())
                .sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()))
                .limit(5)
                .forEach(p -> System.out.println(p.getTitle() + " - R$" + p.getPrice()));

        Integer totalItens = lista.stream()
                .map(Carrinho::getTotalQuantity)
                .reduce(0, (sub, qtd) -> sub + qtd);
        System.out.println("\n--- Reduce (Total Itens): " + totalItens + " ---");

        Map<Double, List<ProdutoCarrinho>> agrupaPreco = lista.stream()
                .flatMap(c -> c.getProducts().stream())
                .collect(Collectors.groupingBy(ProdutoCarrinho::getPrice));
        System.out.println("\n--- GroupingBy (Grupos Criados): " + agrupaPreco.size() + " ---");
    }
}
