package tests.Produto;

import base.BaseTest;
import io.restassured.response.Response;
import model.Produto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoTest extends BaseTest {

    private String token;

    @BeforeEach
    public void login() {
        Response responseLogin = apiClient.login("anateste@email.com", "1234");

        token = responseLogin.jsonPath().getString("authorization");

        assertEquals(200, responseLogin.statusCode());
        assertNotNull(token);
    }

    @Order(1)
    @Test
    @DisplayName("Criar um produto com sucesso")
    public void criarProdutoComSucesso() {
        Response response = apiClient.post("produtos", gerarProduto(), token);

        assertEquals(201, response.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                response.jsonPath().getString("message")
        );
    }

    @Order(2)
    @Test
    @DisplayName("Buscar todos produtos")
    public void buscarTodosProdutos() {

        Response response = apiClient.getAll("produtos", token);

        assertEquals(200, response.statusCode());
        assertTrue(Integer.parseInt(response.jsonPath().getString("quantidade")) > 0);
    }

    @Order(3)
    @Test
    @DisplayName("Buscar produto por um ID")
    public void buscarProdutoPorID() {
        Produto produto = gerarProduto();

        Response responsePost = apiClient.post("produtos", produto, token);

        assertEquals(201, responsePost.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responsePost.jsonPath().getString("message")
        );

        String id = responsePost.jsonPath().getString("_id");

        Response responseGet = apiClient.getById("produtos", id, token);

        assertEquals(200, responseGet.statusCode());
        assertEquals(produto.getNome(), responseGet.jsonPath().getString("nome"));
        assertEquals(produto.getDescricao(), responseGet.jsonPath().getString("descricao"));
        assertEquals(produto.getPreco(), responseGet.jsonPath().getInt("preco"));
        assertEquals(produto.getQuantidade(), responseGet.jsonPath().getInt("quantidade"));
        assertEquals(id, responseGet.jsonPath().getString("_id"));
    }

    @Order(4)
    @Test
    @DisplayName("Deletar um produto")
    public void deletarProduto() {
        Produto produto = gerarProduto();

        Response responsePost = apiClient.post("produtos", produto, token);

        assertEquals(201, responsePost.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responsePost.jsonPath().getString("message")
        );

        String id = responsePost.jsonPath().getString("_id");

        Response responseDelete = apiClient.delete("produtos", id, token);

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Registro excluído com sucesso", responseDelete.jsonPath().getString("message"));
    }

    @Order(5)
    @Test
    @DisplayName("Editar um produto")
    public void editarProduto() {
        Produto produto = gerarProduto();

        Response responsePost = apiClient.post("produtos", produto, token);

        assertEquals(201, responsePost.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responsePost.jsonPath().getString("message")
        );

        String id = responsePost.jsonPath().getString("_id");

        produto.setNome(gerarNomeProduto());

        Response responsePut = apiClient.put("produtos", id, produto, token);

        assertEquals(200, responsePut.statusCode());
        assertEquals("Registro alterado com sucesso", responsePut.jsonPath().getString("message"));

        Response responseGet = apiClient.getById("produtos", id, token);

        assertEquals(200, responseGet.statusCode());
        assertEquals(produto.getNome(), responseGet.jsonPath().getString("nome"));
        assertEquals(produto.getDescricao(), responseGet.jsonPath().getString("descricao"));
        assertEquals(produto.getQuantidade(), responseGet.jsonPath().getInt("quantidade"));
        assertEquals(produto.getPreco(), responseGet.jsonPath().getInt("preco"));
    }
}
