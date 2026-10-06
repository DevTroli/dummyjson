package com.dummyjson.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaCarrinhos {
    private List<Carrinho> carts;
    public RespostaCarrinhos() {}
    public List<Carrinho> getCarts() { return carts; }
    public void setCarts(List<Carrinho> carts) { this.carts = carts; }
}
