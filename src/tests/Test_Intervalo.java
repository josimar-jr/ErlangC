package tests;

import wfmpack.Intervalo;
import static org.junit.Assert.*;
import org.junit.Test;

public class Test_Intervalo {

	@Test
	public void testExibir() {
		Intervalo int3= new Intervalo();
		int3.setIntervalInSeconds(900); // intervalo de 15 minutos
		int3.exibir();
	}

	@Test
	public void testGetDataHora() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDataHora() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalcularAgentes() {
		fail("Not yet implemented");
	}

}
