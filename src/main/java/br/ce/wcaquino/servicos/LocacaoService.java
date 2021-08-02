package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueException,LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Lista de filmes vazia ou nula");
		}
		Locacao locacao = new Locacao();
		double precoTotal = 0.00;
		
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueException("Filme sem estoque");
			}
			Double valorFilme = filme.getPrecoLocacao();
			if (i == 2) {
				valorFilme = valorFilme * 0.75;
			}
			if (i == 3) {
				valorFilme = valorFilme * 0.5;
			}
			if (i == 4) {
				valorFilme = valorFilme * 0.25;
			}
			if (i == 5) {
				valorFilme = valorFilme * 0;
			}
			locacao.addFilme(filme);
			precoTotal = precoTotal + valorFilme;
		}

		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(precoTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}