package br.ce.wcaquino.servicos;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {

	@Test
	public void teste() {
		
		// Cenário
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Otavio");
		Filme filme = new Filme("Velozes e Furiosos", 2, 27.99);
		
		// Ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		
		// Verificação
		MatcherAssert.assertThat(locacao.getValor(), is(equalTo(27.99)));
		MatcherAssert.assertThat(locacao.getValor(), is(not(30.00)));
		MatcherAssert.assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		MatcherAssert.assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
}
