package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private LocacaoService service;
	
    @Rule
    public ErrorCollector error = new ErrorCollector();
    
    @Before
    public void setup() {
    	service = new LocacaoService();
    }
    
    @Test
    public void testeLocacao() throws Exception {

        // Cenario
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 2, 27.99);
        
        // Acao
        Locacao locacao;
        locacao = service.alugarFilme(usuario, filme);

        // Verificacao
        error.checkThat(locacao.getValor(), is(equalTo(27.99)));
        error.checkThat(locacao.getValor(), is(not(30.00)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmesSemEstoqueException.class)
    public void testLocacao_filmeSemEstoque() throws Exception {
        // Cenario
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 0, 27.99);

        // Acao
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testLocacao_usuarioVazio() throws FilmesSemEstoqueException {
        // Cenario
        Filme filme = new Filme("Filme 2", 1, 4.0);

        // Acao
        try {
            service.alugarFilme(null, filme);
            Assert.fail();
        } catch (LocadoraException e) {
            MatcherAssert.assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void testLocacao_filmeVazio() {
        // Cenario
        Usuario usuario = new Usuario("Otavio");

        // Acao
        Exception exception = Assert.assertThrows(LocadoraException.class, () -> service.alugarFilme(usuario, null));
        MatcherAssert.assertThat(exception.getMessage(),is("Filme vazio"));
    }
}
