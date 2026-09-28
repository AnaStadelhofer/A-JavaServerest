package model;

import java.util.List;

public class Carrinho {

    private List<ItemCarrinho> produtos;

    public Carrinho(List<ItemCarrinho> produtos) {
        this.produtos = produtos;
    }

    public List<ItemCarrinho> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemCarrinho> produtos) {
        this.produtos = produtos;
    }
}