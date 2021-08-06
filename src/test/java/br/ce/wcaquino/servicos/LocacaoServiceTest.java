package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
        service = new LocacaoService();
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(verificarDiaSemana(new Date(),Calendar.SATURDAY));

        // Cenario
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 2, 27.99);
        List<Filme> filmes = Collections.singletonList(filme);

        // Acao
        Locacao locacao;
        locacao = service.alugarFilme(usuario, filmes);

        // Verificacao
        error.checkThat(locacao.getValor(), is(equalTo(27.99)));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
    }

    @Test(expected = FilmesSemEstoqueException.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
        // Cenario
        Usuario usuario = new Usuario("Otavio");
        Filme filme = new Filme("Velozes e Furiosos", 0, 27.99);
        List<Filme> filmes = Collections.singletonList(filme);

        // Acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
        // Cenario
        Filme filme = new Filme("Filme 2", 1, 4.0);
        List<Filme> filmes = Collections.singletonList(filme);

        // Acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            MatcherAssert.assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() {
        // Cenario
        Usuario usuario = new Usuario("Otavio");

        // Acao
        Exception exception = Assert.assertThrows(LocadoraException.class, () -> service.alugarFilme(usuario, null));
        MatcherAssert.assertThat(exception.getMessage(), is("Lista de filmes vazia ou nula"));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmesSemEstoqueException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        // Cenario
        Usuario usuario = new Usuario("Otavio");
        List<Filme> filmes = Collections.singletonList(new Filme("Filme 1", 1, 5.0));

        // Acao
        Locacao locacao = service.alugarFilme(usuario,filmes);

        // Verificacao
        MatcherAssert.assertThat(locacao.getDataRetorno(), caiNumaSegunda());
    }
}
