package tests.Carrinho;

import base.BaseTest;
import io.restassured.response.Response;
import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;
import model.Usuario;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrinhoTest extends BaseTest {

    private String token;

    protected Response criarCarrinho() {

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

        Response responseCarrinho = apiClient.post(
                "carrinhos",
                carrinho,
                token
        );

        assertEquals(201, responseCarrinho.statusCode());

        return responseCarrinho;
    }

    @BeforeEach
    public void login() {
        Usuario usuario = gerarPessoaAdmin();

        apiClient.post("usuarios", usuario);
        Response responseLogin = apiClient.login(usuario.getEmail(), usuario.getPassword());

        token = responseLogin.jsonPath().getString("authorization");

        assertEquals(200, responseLogin.statusCode());
        assertNotNull(token);
    }

    @Order(1)
    @DisplayName("Criar um carrinho com sucesso")
    @Test
    public void criarCarrinhoComSucesso() {
        Response responseCarrinho  = criarCarrinho();

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );
    }

    @Order(2)
    @DisplayName("Concluir compra do carrinho")
    @Test
    public void concluirCompraCarrinho() {
        Response responseCarrinho  = criarCarrinho();

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );

        Response responseDelete = apiClient.deleteSemID("carrinhos/concluir-compra", token);

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Registro excluído com sucesso", responseDelete.jsonPath().getString("message"));
    }

    @Order(3)
    @DisplayName("Cancelar compra do carrinho")
    @Test
    public void cancelarCompraCarrinho() {
        Response responseCarrinho  = criarCarrinho();

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );

        Response responseDelete = apiClient.deleteSemID("carrinhos/cancelar-compra", token);

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Registro excluído com sucesso. Estoque dos produtos reabastecido", responseDelete.jsonPath().getString("message"));
    }

    @Order(4)
    @DisplayName("Buscar por carrinho criado")
    @Test
    public void buscarCarrinhoPorIDComSucesso() {
        Response responseCarrinho  = criarCarrinho();

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );

        String idCarrinho = responseCarrinho.jsonPath().getString("_id");

        Response responseGet = apiClient.getById("carrinhos", idCarrinho, token);

        assertEquals(200, responseGet.statusCode());
        assertFalse(responseGet.jsonPath().getString("produtos").isEmpty());
        assertTrue(responseGet.jsonPath().getInt("precoTotal") > 0);
        assertTrue(responseGet.jsonPath().getInt("quantidadeTotal") > 0);
    }

    @Order(5)
    @DisplayName("Buscar por todos carrinhos")
    @Test
    public void buscarTodosCarrinhos() {
        Response responseCarrinho  = criarCarrinho();

        assertEquals(201, responseCarrinho.statusCode());
        assertEquals(
                "Cadastro realizado com sucesso",
                responseCarrinho.jsonPath().getString("message")
        );

        Response responseGet = apiClient.getAll("carrinhos", token);

        assertEquals(200, responseGet.statusCode());
        assertFalse(responseGet.jsonPath().getString("carrinhos[0].produtos").isEmpty());
        assertTrue(responseGet.jsonPath().getInt("carrinhos[0].precoTotal") > 0);
        assertTrue(responseGet.jsonPath().getInt("carrinhos[0].quantidadeTotal") > 0);
    }
}
