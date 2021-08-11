package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

    public int somar(int a, int b) {
        System.out.println("Estou executando o método somar");
        return a + b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public int dividir(int a, int b) throws NaoPodeDividirPorZeroException {
        if (b == 0 ) {
            throw new NaoPodeDividirPorZeroException("Não é possível dividir por zero");
        }
        return a / b;
    }
    
    public int divide(String a, String b) {
    	return Integer.parseInt(a) / Integer.parseInt(b);
    }

    public void imprime(){
        System.out.println("Passei aqui");
    }
}
