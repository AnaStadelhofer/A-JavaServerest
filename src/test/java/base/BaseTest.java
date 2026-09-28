package base;

import client.ApiClient;
import model.Produto;
import model.Usuario;
import utils.FakerUtils;

public class BaseTest {

    protected final ApiClient apiClient = new ApiClient();
    protected final FakerUtils fakerUtils = new FakerUtils();

    protected Usuario gerarPessoaAdmin() {
        return fakerUtils.gerarPessoaAdmin();
    }

    protected Produto gerarProduto() {
        return fakerUtils.gerarProduto();
    }

    protected String gerarNome() {
        return fakerUtils.nomePessoa();
    }

    protected String gerarNomeProduto() {
        return fakerUtils.nomeProduto();
    }
}