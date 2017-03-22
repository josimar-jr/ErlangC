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
		Erlang erlc30m = new Erlang();
		double chamadas = 180;
		double TMA = 450;
		double NS = 0.90;
		int tempoAceitavel = 10;
		
		// adiciona o período com 1/2 hora
		erlc30m.setMinutoIntervalo(30); 
		erlc30m.agent(NS, tempoAceitavel, chamadas, TMA);
		assertEquals(55, erlc30m.getAgenteEstimado());
	}

	@Test
	public void testSLA() {
		Erlang erlc30m = new Erlang();
		double chamadas = 180;
		double TMA = 450;
		int numAgentes = 48;
		int tempoAceitavel = 10;
		
		double nsAlvo = 0.47538605041284376;
		double tolerancia = 0.000001;
		
		// adiciona o período com 1/2 hora
		erlc30m.setMinutoIntervalo(30);
		erlc30m.SLA(numAgentes, tempoAceitavel, chamadas, TMA);
		assertEquals(nsAlvo, erlc30m.getNivelServicoEstimado(), tolerancia);
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
		Erlang erlang = new Erlang();
		erlang.getAgenteEstimado();
	}

	@Test
	public void testGetNivelServicoEstimado() {
		Erlang erlang = new Erlang();
		erlang.getNivelServicoEstimado();
	}

}
