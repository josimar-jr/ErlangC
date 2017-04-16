package tests;

import wfmpack.Intervalo;
import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import org.junit.Test;

public class Test_Intervalo {

	@Test
	public void testExibir() {
		Intervalo int3= new Intervalo();
		int3.setIntervalInSeconds(900); // intervalo de 15 minutos
		int3.exibir();
	}

	@Test
	public void testSetGetDataHora() {
		GregorianCalendar gc = new GregorianCalendar( 2017, 1, 1, 8, 0 );
		Intervalo intDtHr = new Intervalo();
		intDtHr.setDataHora(gc);
		assertEquals( "Data e Hora deveriam ser iguais", gc, intDtHr.getDataHora() );
	}

	@Test
	public void testCalcularAgentes() {
		Intervalo int15m = new Intervalo();
		
		int agentsAlvo = 103;
		double slaAlvo = 0.8262;
		
		int15m.setIntervalInSeconds(900); // intervalo de 15 minutos
		int15m.setBlockingPercentage(0.01);
		int15m.setNsMeta(0.90); // 90%
		int15m.setTempoEsperaAceitavel(10); // 10 segundos
		int15m.setChamadas(180); // qtde chamadas
		
		assertFalse( "Não deveria calcular o agents().", int15m.calcularAgentes() );
		assertFalse( "Não deveria calcular o sla().", int15m.setAgentesDimensionados(100));
		
		int15m.setTMA(450); // valor do tma em segundos
		assertTrue( "Deveria calcular o agents().", int15m.calcularAgentes());
		assertTrue( "Deveria calcular o sla().", int15m.setAgentesDimensionados(100)); // recursos dimensionados para o intervalo
		
		assertEquals( "Agentes necessários deveriam ser 103", agentsAlvo, int15m.getAgentesNecessarios());
		assertEquals( "Nível de serviço deveria ser aproximadamente 82,62%.", slaAlvo, int15m.getNsDimensionado(), 0.000001 );
	}

}
