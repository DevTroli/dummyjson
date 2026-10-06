package com.dummyjson.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrinho {
    private Long id;
    private Double total;
    private Double descontoTotal;
    private Integer totalQuantia;
    private List<ProdutoCarrinho> produtos;

    public Carrinho() {}

    public Double getEconomia() { 
        return (total != null && discountedTotal != null) ? total - discountedTotal : 0.0; 
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Double getDiscountedTotal() { return descontoTotal; }
    public void setDiscountedTotal(Double descontoTotal) { this.descontoTotal = discountedTotal; }
    public Integer getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Integer totalQuantia) { this.totalQuantia = totalQuantity; }
    public List<ProdutoCarrinho> getProducts() { return produtos; }
    public void setProducts(List<ProdutoCarrinho> produtos) { this.produtos = products; }
}
