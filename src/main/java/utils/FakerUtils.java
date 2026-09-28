package utils;

import model.Produto;
import model.Usuario;
import net.datafaker.Faker;

public class FakerUtils {

    private final Faker faker = new Faker();

    public String nomePessoa() {
        return faker.name().fullName();
    }

    public String email() {
        return faker.internet().emailAddress();
    }

    public String senha() {
        return "Senha@123";
    }

    public Usuario gerarPessoaAdmin() {

        return new Usuario(
                nomePessoa(),
                email(),
                senha(),
                "true"
        );
    }

    public String nomeProduto() {
        return faker.howToTrainYourDragon().characters() + " " + faker.number().digits(6);
    }

    public Integer precoProduto() {
        return faker.number().numberBetween(1, 1000);
    }

    public String descriccaoProduto() {
        return faker.lorem().paragraph();
    }

    public Integer quantidadeProduto() {
        return faker.number().numberBetween(1, 100);
    }

    public Produto gerarProduto() {
        return new Produto(
                nomeProduto(),
                precoProduto(),
                descriccaoProduto(),
                quantidadeProduto()
        );
    }
}
