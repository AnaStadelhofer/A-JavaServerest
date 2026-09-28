package client;

import config.ApiConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final String URL = ApiConfig.BASE_URL;

    public Response login(String email, String password) {
        return given()
                .baseUri(URL)
                .contentType("application/json")
                .body("""
                    {
                        "email": "%s",
                        "password": "%s"
                    }
                    """.formatted(email, password))
                .when()
                .post("/login");
    }

    public Response post(String endpoint, Object obj) {
        return given()
                .baseUri(URL)
                .contentType("application/json")
                .body(obj)
                .when()
                .post("/" + endpoint);
    }

    public Response post(String endpoint, Object obj, String token) {
        return (Response) given()
                .baseUri(URL)
                .contentType("application/json")
                .header("Authorization", token)
                .body(obj)
                .when()
                .post("/" + endpoint);
    }

    public Response getById(String endpoint, String id, String token) {
        return given()
                .baseUri(URL)
                .header("Authorization", token)
                .when()
                .get("/" + endpoint + "/" + id);
    }

    public Response getById(String endpoint, String id) {
        return given()
                .baseUri(URL)
                .when()
                .get("/" + endpoint + "/" + id);
    }

    public Response getAll(String endpoint) {
        return given()
                .baseUri(URL)
                .when()
                .get("/" + endpoint);
    }

    public Response getAll(String endpoint, String token) {
        return given()
                .baseUri(URL)
                .header("Authorization", token)
                .when()
                .get("/" + endpoint);
    }

    public Response delete(String endpoint, String id) {
        return given()
                .baseUri(URL)
                .when()
                .delete("/" + endpoint + "/" + id);
    }

    public Response delete(String endpoint, String id, String token) {
        return given()
                .baseUri(URL)
                .header("Authorization", token)
                .when()
                .delete("/" + endpoint + "/" + id);
    }

    public Response put(String endpoint, String id, Object obj) {
        return given()
                .baseUri(URL)
                .contentType("application/json")
                .body(obj)
                .when()
                .put("/" + endpoint + "/" + id);
    }

    public Response put(String endpoint, String id, Object obj, String token) {
        return given()
                .baseUri(URL)
                .contentType("application/json")
                .header("Authorization", token)
                .body(obj)
                .when()
                .put("/" + endpoint + "/" + id);
    }

    public Response deleteSemID(String endpoint, String token) {
        return given()
                .baseUri(URL)
                .header("Authorization", token)
                .when()
                .delete("/" + endpoint);
    }
}