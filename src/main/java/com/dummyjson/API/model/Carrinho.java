package com.dummyjson.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrinho {
    private Long id;
    private Double total;

    @JsonProperty("discountedTotal")
    private Double discountedTotal;

    @JsonProperty("totalQuantity")
    private Integer totalQuantity;

    private List<ProdutoCarrinho> products;

    public Carrinho() {
    }

    public Double getEconomia() {
        return total != null && discountedTotal != null ? total - discountedTotal : 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(Double discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<ProdutoCarrinho> getProducts() {
        return products;
    }

    public void setProducts(List<ProdutoCarrinho> products) {
        this.products = products;
    }
}
