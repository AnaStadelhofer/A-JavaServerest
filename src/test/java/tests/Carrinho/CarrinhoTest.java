package tests.Carrinho;

import base.BaseTest;
import io.restassured.response.Response;
import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarrinhoTest extends BaseTest {

    private String token;

    @BeforeEach
    public void login() {
        Response responseLogin = apiClient.login("anateste@email.com", "1234");

        token = responseLogin.jsonPath().getString("authorization");

        assertEquals(200, responseLogin.statusCode());
        assertNotNull(token);
    }

    @Test
    public void criarCarrinho() {
        Produto produto1 = gerarProduto();
        Produto produto2 = gerarProduto();

        Response responseProduto1 = apiClient.post("produtos", produto1, token);
        Response responseProduto2 = apiClient.post("produtos", produto2, token);

        assertEquals(201, responseProduto1.statusCode());
        assertEquals(201, responseProduto2.statusCode());

        String id1 = responseProduto1.jsonPath().getString("_id");
        String id2 = responseProduto2.jsonPath().getString("_id");

        List<ItemCarrinho> produtos = List.of(
                new ItemCarrinho(id1, 2),
                new ItemCarrinho(id2, 1)
        );

        Carrinho carrinho = new Carrinho(produtos);

        // Cria o carrinho
        Response responseCarrinho = apiClient.post(
                "carrinhos",
                carrinho,
                token
        );

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );
    }
}
