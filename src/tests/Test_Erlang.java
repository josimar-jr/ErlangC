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
		assertTrue(erlang.hasError());
		assertEquals( 5, erlang.getErrors().size() );
		erlang.finalize();
		
		// criar instância do objeto usando todos os parâmetros
		int intervalInSeconds = 900;
		double targetSLA = 0.90;
		int targetTime = 10;
		double calls = 900;
		double averageAnswerTime = 145;
		int insertedAgents = 80;
		double blockingPercentage = 0.01;
		erlang = new Erlang( intervalInSeconds, targetSLA, targetTime, calls, averageAnswerTime, insertedAgents, blockingPercentage );
		assertFalse( erlang.hasError() );
	}

	@Test
	public void testErlangIntDoubleIntDoubleDouble() {
		int intervaloEmSegundos = 1800;
		double SLAMeta = 0.90;
		int tempoEsperaAceitavel = 10;
		double chamadas = 667;
		double TMA = 150;

		Erlang erlang = new Erlang( intervaloEmSegundos, SLAMeta, tempoEsperaAceitavel, chamadas, TMA );
		assertFalse( erlang.getErrors().containsKey( Erlang.errorAgent ));
		assertTrue( erlang.getErrors().containsKey( Erlang.errorSLA ));
	}

	@Test
	public void testErlangIntIntIntDoubleDouble() {
		int intervaloEmSegundos = 900;
		int tempoEsperaAceitavel = 10;
		double chamadas = 180;
		double TMA = 450;
		int numAgentes = 59;

		Erlang erlang = new Erlang(intervaloEmSegundos, numAgentes, tempoEsperaAceitavel, chamadas, TMA);
		assertFalse( erlang.getErrors().containsKey( Erlang.errorSLA ));
		assertTrue( erlang.getErrors().containsKey( Erlang.errorAgent ));
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
		erlc30m.setIntervalInMinutes(30); 
		erlc30m.agent(NS, tempoAceitavel, chamadas, TMA);
		assertEquals(55, erlc30m.getNecessaryAgents(), 0);
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
		erlc30m.setIntervalInMinutes(30);
		erlc30m.SLA(numAgentes, tempoAceitavel, chamadas, TMA);
		assertEquals(nsAlvo, erlc30m.getSLA(), tolerancia);
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
		erlang.setIntervalInSeconds( segundos );
		assertEquals(asa1h, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 48;
		segundos = 1800;
		erlang.setIntervalInSeconds( segundos );
		assertEquals(asa30m, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 100;
		segundos = 900;
		erlang.setIntervalInSeconds( segundos );
		assertEquals(asa15m, erlang.ASA(agents, chamadas, TMA), 0.05);
		
		agents = 140;
		segundos = 600;
		erlang.setIntervalInSeconds( segundos );
		assertEquals(asa10m, erlang.ASA(agents, chamadas, TMA), 0.05);
	}

	@Test
	public void testSetIntervalInMinutes() {
		int minutos = 15;
		int segundos = minutos * 60;
		Erlang erlang = new Erlang();
		erlang.setIntervalInMinutes(minutos);
		assertEquals( erlang.getSecondsInterval(), segundos);
	}

	@Test
	public void testSetIntervalInSeconds() {
		int segundos = 900;
		Erlang erlang = new Erlang();
		erlang.setIntervalInSeconds(segundos);
		assertEquals( erlang.getSecondsInterval(), segundos);
	}

	@Test
	public void testGetSecondsInterval() {
		int segundos = 900;
		Erlang erlang = new Erlang();
		erlang.setIntervalInSeconds(segundos);
		assertEquals( erlang.getSecondsInterval(), segundos);
	}

	@Test
	public void testGetNecessaryAgents() {
		Erlang erlang = new Erlang();
		erlang.getNecessaryAgents();
	}

	@Test
	public void testGetSLA() {
		Erlang erlang = new Erlang();
		erlang.getSLA();
	}

}
