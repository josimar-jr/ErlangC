package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import wfmpack.Schedule;

public class Test_Schedule {

	@Test
	public void testSchedule() {
		Schedule sched = new Schedule();
		int totalIntervalos = 18;
		int qtdeIntervaloAgente = 12;
		
		// não ativa por causa da quantidade de intervalos dos agentes 
		sched.setTotalIntervalos(totalIntervalos);
		sched.setQtdeIntervalosAgentes(0);
		assertFalse( "Não deveria ativar o objeto para uso", sched.start() );
		
		// não ativa por causa da quantidade total de intervalos
		sched.setTotalIntervalos(0);
		sched.setQtdeIntervalosAgentes(qtdeIntervaloAgente);
		assertFalse( "Não deveria ativar o objeto para uso", sched.start() );
		
		// não ativa por causa dos valores invertidos entre total e intervalos dos agentes
		sched.setTotalIntervalos(qtdeIntervaloAgente);
		sched.setQtdeIntervalosAgentes(totalIntervalos);
		assertFalse( "Não deveria ativar o objeto para uso", sched.start() );
		
		// ativa normalmente
		sched.setTotalIntervalos(totalIntervalos);
		sched.setQtdeIntervalosAgentes(qtdeIntervaloAgente);
		assertTrue( "Deveria ativar o objeto para uso", sched.start() );
	}
	
	@Test
	public void testScheduleSetAgents() {
		Schedule sched = new Schedule();
		int totalIntervalos = 18;
		int qtdeIntervaloAgente = 12;

		sched.setTotalIntervalos(totalIntervalos);
		sched.setQtdeIntervalosAgentes(qtdeIntervaloAgente);
		
		assertFalse( "Não deveria permitir a atribuir no schedule.", sched.setAgentesHorario(0, 37) );
		
		assertTrue( "Deveria ativar o objeto para uso", sched.start() );
		
		assertTrue( sched.setAgentesHorario(0, 37) );
		assertTrue( sched.setAgentesHorario(3, 11) );
		assertTrue( sched.setAgentesHorario(4, 11) );
		assertTrue( sched.setAgentesHorario(6, 25) );
		
		assertEquals( "Deveria ter 59 agentes no intervalo 5.", 59, sched.getTotalAgentesLinha(5) );
		
		assertEquals( "Deveria ter 47 agentes no intervalo 13", 47, sched.getTotalAgentesLinha(13) );
		
	}
}
