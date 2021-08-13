package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

@RunWith(ParallelRunner.class)
public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }
    
    @Test
    public void deveSomarDoisValores() {
        // Cenario
        int a = 5;
        int b = 3;

        // Acao
        int resultado = calc.somar(a,b);

        // Verificacao
        Assert.assertEquals(8,resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        // Cenario
        int a = 8;
        int b = 5;

        // Acao
        int resultado = calc.subtrair(a,b);

        // Verificacao
        Assert.assertEquals(3,resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        // Cenario
        int a = 6;
        int b = 3;

        // Acao
        int resultado = calc.dividir(a,b);

        // Verificacao
        Assert.assertEquals(2,resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        // Cenario
        int a = 10;
        int b = 0;

        // Acao
        calc.dividir(a,b);
    }
    
    @Test
    public void deveDividir() {
    	String a = "6";
    	String b = "3";
    	
    	int resultado = calc.divide(a, b);
    	
    	Assert.assertEquals(2, resultado);
    }
}
