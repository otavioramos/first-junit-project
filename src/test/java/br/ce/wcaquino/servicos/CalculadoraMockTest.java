package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {

    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void devoMostrarDiferencaEntreMockSpy() {
        Mockito.when(calcMock.somar(1, 2)).thenReturn(5);
        // Alerta!
        // O Spy executa o método somar antes de gravar a expectativa
        // Mockito.when(calcSpy.somar(1, 2)).thenReturn(5);

        // Dessa forma o método somar não é executado pelo Spy
        Mockito.doReturn(5).when(calcSpy).somar(1,2);
        Mockito.doNothing().when(calcSpy).imprime();
    }

    @Test
    public void teste(){
        Calculadora calc = Mockito.mock(Calculadora.class);

        ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(calc.somar(argCapt.capture(),argCapt.capture())).thenReturn(5);

        Assert.assertEquals(5,calc.somar(1,8));
    }
}
