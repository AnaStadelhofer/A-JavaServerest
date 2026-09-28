package tests.Usuario;

import base.BaseTest;
import io.restassured.response.Response;
import model.Usuario;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest extends BaseTest {

    @Order(1)
    @Test
    @DisplayName("Criar usuário com sucesso")
    void criarUsuarioComSucesso() {

        Response response = apiClient.post("usuarios", gerarPessoaAdmin());

        assertEquals(201, response.statusCode());
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().getString("message"));
    }

    @Order(2)
    @Test
    @DisplayName("Trazer todos usuários")
    void getTodosUsuarios() {

        Response response = apiClient.getAll("usuarios");

        assertEquals(200, response.statusCode());
        assertTrue(response.jsonPath().getList("usuarios").size() > 0);
    }

    @Order(3)
    @Test
    @DisplayName("Trazer usuário recém-criado")
    void getByIDUsuario() {
        Usuario usuario = gerarPessoaAdmin();

        Response responsePost = apiClient.post("usuarios", usuario);

        assertEquals(201, responsePost.statusCode());
        String id = responsePost.jsonPath().getString("_id");

        Response responseGet = apiClient.getById("usuarios", id);

        assertEquals(200, responseGet.statusCode());
        assertEquals(usuario.getNome(), responseGet.jsonPath().getString("nome"));
        assertEquals(usuario.getEmail(), responseGet.jsonPath().getString("email"));
        assertEquals(usuario.getPassword(), responseGet.jsonPath().getString("password"));
        assertEquals(id, responseGet.jsonPath().getString("_id"));

    }

    @Order(4)
    @Test
    @DisplayName("Deletar usuário")
    void deletarUsuario() {
        Response responsePost = apiClient.post("usuarios", gerarPessoaAdmin());

        assertEquals(201, responsePost.statusCode());
        assertEquals("Cadastro realizado com sucesso", responsePost.jsonPath().getString("message"));

        String id = responsePost.jsonPath().getString("_id");
        Response responseDelete = apiClient.delete("usuarios", id);

        assertEquals(200, responseDelete.statusCode());
        assertEquals("Registro excluído com sucesso", responseDelete.jsonPath().getString("message"));
    }

    @Order(5)
    @Test
    @DisplayName("Editar usuário")
    void editarUsuario() {
        Usuario usuario = gerarPessoaAdmin();

        Response responsePost = apiClient.post("usuarios", usuario);

        assertEquals(201, responsePost.statusCode());
        assertEquals("Cadastro realizado com sucesso", responsePost.jsonPath().getString("message"));

        String id = responsePost.jsonPath().getString("_id");

        usuario.setNome(gerarNome());

        Response responsePut = apiClient.put("usuarios", id, usuario);

        assertEquals(200, responsePut.statusCode());
        assertEquals("Registro alterado com sucesso", responsePut.jsonPath().getString("message"));

        // Validar edição
        Response responseGet = apiClient.getById("usuarios", id);

        assertEquals(200, responseGet.statusCode());
        assertEquals(usuario.getNome(), responseGet.jsonPath().getString("nome"));
        assertEquals(usuario.getEmail(), responseGet.jsonPath().getString("email"));
        assertEquals(usuario.getPassword(), responseGet.jsonPath().getString("password"));
        assertEquals(id, responseGet.jsonPath().getString("_id"));
    }

    // TODO
    @DisplayName("Consultar por id inválido")
    void consultarPorIDInvalido() {

    }

    // Editar usuário inexistente

    // Editar usuário com email existente duplicado
}
