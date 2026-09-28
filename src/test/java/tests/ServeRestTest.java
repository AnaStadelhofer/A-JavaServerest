package tests;

import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import net.datafaker.Faker;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServeRestTest {

    @Test
    void deveRetornarStatus200() {

        given()
                .baseUri("https://serverest.dev")
                .when()
                .get("/usuarios")
                .then()
                .statusCode(200)
                .body("usuarios", notNullValue())
                .log().body();
    }

    @Test
    @DisplayName("Cadastrar usuário com sucesso")
    void deveCadastrarUsuario() {
        String body = """
                {
                "nome": "Ana QA",
                "email": "anaqa1234@email.com",
                "password": "123456",
                "administrador": "true"
                }
                """;


        given()
                .baseUri("https://serverest.dev")
                .contentType("application/json")
                .body(body)
                .when()
                .post("/usuarios")
                .then()
                .log().body()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Cadastrar usuário e salvar responde em uma var")
    void deveCadastrarESalvarResponse() {

        String body = """
                {
                "nome": "Ana QA",
                "email": "anaqa1234@email.com",
                "password": "123456",
                "administrador": "true"
                }
                """;

        Response response =
                given()
                        .baseUri("https://serverest.dev")
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/usuarios");

        System.out.println(response.asPrettyString());

        String id = response.jsonPath().getString("_id");

        System.out.println("ID criado: " + id);
    }

    @Test
    @DisplayName("Cadastrar usando Faker")
    void cadastrarComFaker() {
        Faker faker = new Faker();

        String nome = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String senha = "Senha@123";

        String body = """
                {
                    "nome": "%s",
                    "email": "%s",
                    "password": "%s",
                    "administrador": "true"
                }
                """.formatted(nome, email, senha);

        Response response = given()
                .baseUri("https://serverest.dev")
                .contentType("application/json")
                .body(body)
                .when()
                .post("/usuarios");

        String id = response.jsonPath().getString("_id");
        String mensagem = response.jsonPath().getString("message");

        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertEquals(201, response.statusCode());
        assertNotNull(id);

        // Precisa chamar o get pra validar esses campos, pois não tem no response do post
        //assertEquals(nome, response.jsonPath().getString("nome"));
        //assertEquals(email, response.jsonPath().getString("email"));

    }

    @Test
    @DisplayName("Cadastrar usuário e consultar pelo ID")
    void cadastrarUsuarioEConsultar() {
        Faker faker = new Faker();

        String nome = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String senha = "Senha@123";

        String body = """
                {
                    "nome": "%s",
                    "email": "%s",
                    "password": "%s",
                    "administrador": "true"
                }
                """.formatted(nome, email, senha);

        Response response = given()
                .baseUri("https://serverest.dev")
                .contentType("application/json")
                .body(body)
                .when()
                .post("/usuarios");

        String id = response.jsonPath().getString("_id");
        String mensagem = response.jsonPath().getString("message");

        assertEquals(201, response.statusCode());
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertNotNull(id);

        Response responseGet = given()
                .baseUri("https://serverest.dev")
                .contentType("application/json")
                .when()
                .get("/usuarios/" + id);

        assertEquals(200, responseGet.statusCode());
        assertEquals(nome, responseGet.jsonPath().getString("nome"));
        assertEquals(email, responseGet.jsonPath().getString("email"));
    }

    @Test
    @DisplayName("Deletar usuário cadastrado")
    void deletarUsuarioEConsultar() {
        Faker faker = new Faker();

        String nome = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String senha = "Senha@123";

        String body = """
                {
                    "nome": "%s",
                    "email": "%s",
                    "password": "%s",
                    "administrador": "true"
                }
                """.formatted(nome, email, senha);

        Response response = given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/usuarios");

        String id = response.jsonPath().getString("_id");
        String mensagem = response.jsonPath().getString("message");

        assertEquals(201, response.statusCode());
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertNotNull(id);

        Response responseDelete = given()
                .baseUri(ApiConfig.BASE_URL)
                .when()
                .delete("/usuarios/" + id);

        assertEquals(200, responseDelete.statusCode());
        assertEquals(
                "Registro excluído com sucesso",
                responseDelete.jsonPath().getString("message")
        );

        Response responseGet = given()
                .baseUri(ApiConfig.BASE_URL)
                .when()
                .get("/usuarios/" + id);

        assertEquals(400, responseGet.statusCode());
        assertEquals("Usuário não encontrado", responseGet.jsonPath().getString("message"));
    }
}