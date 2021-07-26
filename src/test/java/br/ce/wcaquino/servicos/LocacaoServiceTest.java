package br.ce.wcaquino.servicos;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

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
		Assert.assertEquals(27.99, locacao.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}
	
}
