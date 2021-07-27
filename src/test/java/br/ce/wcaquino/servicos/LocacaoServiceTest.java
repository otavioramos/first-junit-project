package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void testeLocacao() throws Exception {

        // Cen�rio
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 2, 27.99);

        // A��o
        Locacao locacao;
        locacao = locacaoService.alugarFilme(usuario, filme);

        // Verifica��o
        error.checkThat(locacao.getValor(), is(equalTo(27.99)));
        error.checkThat(locacao.getValor(), is(not(30.00)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = Exception.class)
    public void testLocacao_filmeSemEstoque() throws Exception {
        // Cen�rio
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 0, 27.99);

        // A��o
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test
    public void testLocacao_filmeSemEstoque2() {
        // Cen�rio
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 0, 27.99);

        try {
        	// A��o
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lan�ado uma exce��o");
        } catch (Exception e) {
            MatcherAssert.assertThat(e.getMessage(),is("Filme sem estoque"));
        }
    }

    @Test
    public void testLocacao_filmeSemEstoque3() throws Exception {
        // Cen�rio
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 0, 27.99);

        Exception exception = Assert.assertThrows(Exception.class,() -> locacaoService.alugarFilme(usuario, filme));
        MatcherAssert.assertThat(exception.getMessage(),is("Filme sem estoque"));
    }
}
