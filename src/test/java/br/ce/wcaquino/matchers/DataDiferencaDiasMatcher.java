package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{
	
	private Integer qtDias;
	
	public DataDiferencaDiasMatcher(Integer qtDias) {
		this.qtDias = qtDias;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data,DataUtils.obterDataComDiferencaDias(qtDias));
	}

}
