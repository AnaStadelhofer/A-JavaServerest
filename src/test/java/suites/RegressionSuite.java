package suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.Carrinho.CarrinhoTest;
import tests.Produto.ProdutoTest;
import tests.Usuario.UsuarioTest;

@Suite
@SelectClasses({
        UsuarioTest.class,
        ProdutoTest.class,
        CarrinhoTest.class
})

public class RegressionSuite {
}
