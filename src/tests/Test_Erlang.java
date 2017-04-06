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
		Erlang erlang = new Erlang();
		double chamadas = 180;
		double TMA = 450;
		int segundos = 0;
		double blocking = 0.01;
		
		// valores alvo
		int trunks1h = 33;
		int trunks30m = 58;
		int trunks15m = 107;
		int trunks10m = 154;
		
		segundos = 3600;
		assertEquals(trunks1h, erlang.nLines((chamadas * TMA / segundos), blocking ), 0.0);
		
		segundos = 1800;
		assertEquals(trunks30m, erlang.nLines((chamadas * TMA / segundos), blocking ), 0.0);
		
		segundos = 900;
		assertEquals(trunks15m, erlang.nLines((chamadas * TMA / segundos), blocking ), 0.0);
		
		segundos = 600;
		assertEquals(trunks10m, erlang.nLines((chamadas * TMA / segundos), blocking ), 0.0);
	}

	@Test
	public void testASA() {
		Erlang erlang = new Erlang();
		double chamadas = 180;
		double TMA = 450;
		int segundos = 0;
		int agents = 0;
		
		// valores alvo
		double asa1h = 0.600;
		double asa30m = 84.100;
		double asa15m = 9.800;
		double asa10m = 51.100;
		
		agents = 34;
		segundos = 3600;
		erlang.setSegundosIntervalo( segundos );
		assertEquals(asa1h, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 48;
		segundos = 1800;
		erlang.setSegundosIntervalo( segundos );
		assertEquals(asa30m, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 100;
		segundos = 900;
		erlang.setSegundosIntervalo( segundos );
		assertEquals(asa15m, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 140;
		segundos = 600;
		erlang.setSegundosIntervalo( segundos );
		assertEquals(asa10m, erlang.ASA(agents, chamadas, TMA), 0.05);
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
