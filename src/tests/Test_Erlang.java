package tests;

//import org.junit.AfterClass;
//import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import wfmpack.Erlang;

public class Test_Erlang {
	
	@Test
	public void testErlang() {
		Erlang erlang = new Erlang();
	}

	@Test
	public void testErlangIntDoubleIntDoubleDouble() {
		int intervaloEmSegundos = 1800;
		double SLAMeta = 0.90;
		int tempoEsperaAceitavel = 10;
		double chamadas = 667;
		double TMA = 150;

		Erlang erlang = new Erlang( intervaloEmSegundos, SLAMeta, tempoEsperaAceitavel, chamadas, TMA );
	}

	@Test
	public void testErlangIntIntIntDoubleDouble() {
		int intervaloEmSegundos = 900;
		int tempoEsperaAceitavel = 10;
		double chamadas = 180;
		double TMA = 450;
		int numAgentes = 59;

		Erlang erlang = new Erlang(intervaloEmSegundos, numAgentes, tempoEsperaAceitavel, chamadas, TMA);
	}

	@Test
	public void testExibir() {
		int intervaloEmSegundos = 1800;
		double SLAMeta = 0.90;
		int tempoEsperaAceitavel = 10;
		double chamadas = 667;
		double TMA = 150;

		Erlang erlang = new Erlang( intervaloEmSegundos, SLAMeta, tempoEsperaAceitavel, chamadas, TMA );
		erlang.exibir();
	}

	@Test
	public void testAgent() {
		fail("Not yet implemented");
	}

	@Test
	public void testSLA() {
		fail("Not yet implemented");
	}

	@Test
	public void testNLines() {
		fail("Not yet implemented");
	}

	@Test
	public void testASA() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMinutoIntervalo() {
		int minutos = 15;
		int segundos = minutos * 60;
		Erlang erlang = new Erlang();
		erlang.setMinutoIntervalo(minutos);
		assertEquals( erlang.getSegundosIntervalo(), segundos);
	}

	@Test
	public void testSetSegundosIntervalo() {
		int segundos = 900;
		Erlang erlang = new Erlang();
		erlang.setSegundosIntervalo(segundos);
		assertEquals( erlang.getSegundosIntervalo(), segundos);
	}

	@Test
	public void testGetSegundosIntervalo() {
		int segundos = 900;
		Erlang erlang = new Erlang();
		erlang.setSegundosIntervalo(segundos);
		assertEquals( erlang.getSegundosIntervalo(), segundos);
	}

	@Test
	public void testGetAgenteEstimado() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNivelServicoEstimado() {
		fail("Not yet implemented");
	}

}
