import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoCarrinho {
    private String titulo;
    private Double preco;

    public ProdutoCarrinho() {}

    public String getTitle() { return title; }
    public void setTitle(String titulo) { this.titulo = titulo; }
    public Double getPreco() { return preco; }
    public void setPreco(Double Preco) { this.preco = preco; }
}
