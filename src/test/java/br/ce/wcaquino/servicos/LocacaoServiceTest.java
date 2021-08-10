package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.mockito.Mockito;

import java.util.*;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.*;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class LocacaoServiceTest {

    private LocacaoService service;

    private SPCService spc;

    private LocacaoDAO dao;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
        service = new LocacaoService();
        dao = Mockito.mock(LocacaoDAO.class);
        service.setLocacaoDAO(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSpcService(spc);
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(verificarDiaSemana(new Date(),Calendar.SATURDAY));

        // Cenario
        Usuario usuario = umUsuario().agora();
        Filme filme = umFilme().comValor(27.99).agora();
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
        Usuario usuario = umUsuario().agora();
        Filme filme = umFilmeSemEstoque().agora();
        List<Filme> filmes = Collections.singletonList(filme);

        // Acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
        // Cenario
        Filme filme = umFilme().agora();
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
        Usuario usuario = umUsuario().agora();

        // Acao
        Exception exception = Assert.assertThrows(LocadoraException.class, () -> service.alugarFilme(usuario, null));
        MatcherAssert.assertThat(exception.getMessage(), is("Lista de filmes vazia ou nula"));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmesSemEstoqueException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        // Cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().agora());

        // Acao
        Locacao locacao = service.alugarFilme(usuario,filmes);

        // Verificacao
        MatcherAssert.assertThat(locacao.getDataRetorno(), caiNumaSegunda());
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() {
        // Cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().agora());

        when(spc.possuiNegativacao(usuario)).thenReturn(true);

        // Acao
        Exception exception = Assert.assertThrows(LocadoraException.class,
                () -> service.alugarFilme(usuario, filmes));
        MatcherAssert.assertThat(exception.getMessage(),is("Usuario Negativado"));
    }
}
