package model;

public class ItemCarrinho {

    private String idProduto;
    private Integer quantidade;

    public ItemCarrinho(String idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}