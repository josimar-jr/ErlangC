package tests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;
import wfmpack.DiaDimensionamento;
import wfmpack.FileCurvas;
import wfmpack.tipoCurva;

public class Test_DiaDimensionamento {

	@Test
	public void testDiaDimensionamentoOk() {

		String cpathfile = "src/tests/resources/curva_teste1.xlsx";
		FileCurvas fc = new FileCurvas();

		double diaDimeChamadas = 2348;
		double diaDimeTMA = 450;
		
		DiaDimensionamento diaDime = new DiaDimensionamento();

		// carrega os dados do arquivo e monta a curva conforme as informações contidas nele
		// os dados serão cruzados com o arquivo PL.xlsx aba "mini"
		fc.setPath(cpathfile);
		assertTrue( ( fc.lerArquivo() ) );
			
		// atribui a quantidade total de intervalos do dimensionamento
		diaDime.setTotalIntervalos(18);
	
		// o número de intervalos que cada agente atuará
		diaDime.setQtdeIntervalosAgentes(12);
		
		// adiciona um tipo de curva qualquer para o dia
		diaDime.setTipoCurvaDia( tipoCurva.SEM );
		
		// define a quantidade de segundos do intervalo
		diaDime.setSegundosIntervalo(1800); // adiciona 30 minutos para o intervalo
		
		// inclui o Nível de Serviço meta e tempo de espera aceitável
		diaDime.setNsMeta(0.90); // 90%
		diaDime.setTempoAceitavelNs(10);  // 10 segundos
		diaDime.setBlocking(0.01);  // 1% de blocking
		
		// adiciona o dia do dimensionamento
		diaDime.setDia(new GregorianCalendar(2015, 06, 12 )); // 12/06/2015
		
		//chamadas = 2.348 e TMA = 450
		diaDime.setChamadas(diaDimeChamadas);
		diaDime.setTMA(diaDimeTMA);
		
		// captura a única curva inserida
		diaDime.setCurvaDistribuicao(fc.getCurvaEspecifica(0));
		
		// dispara a carga dos dados nos intervalos do dia a ser dimensionado
		assertTrue( ( diaDime.start() ) );
			
		// linha | qtde recursos
		// 0 = 37 agentes
		// 3 = 11 agentes
		// 4 = 11 agentes
		// 6 = 25 agentes
		assertTrue( diaDime.setAgentesHorario(0, 37) );
		assertTrue( diaDime.setAgentesHorario(3, 11) );
		assertTrue( diaDime.setAgentesHorario(4, 11) );
		assertTrue( diaDime.setAgentesHorario(6, 25) );
		
		diaDime.exibir();
		
		// Verificações
		assertEquals( "Deveriam ser 84 agentes ao todo.", 84, diaDime.getAgentes() );

		assertEquals( "NS deveria ser aproximadamente 0.9039.", 0.9039, diaDime.getNsDimensionado(), 0.0009 );

		assertEquals( "Produtividade deveria ser aproximadamente 0.58234", 0.58234, diaDime.getProdutividade(), 0.00001 ) ;

		assertEquals( "Deveriam ser 84 posições dimensionadas.", 84, diaDime.getPosicoesAtendimento() );
		
	}

	@Test
	public void testDiaDimensionamentoErroIntervalos() {

		String cpathfile = "src/tests/resources/testeimport.xlsx";
		FileCurvas fc = new FileCurvas();

		double diaDimeChamadas = 2348;
		double diaDimeTMA = 450;
		
		DiaDimensionamento diaDime = new DiaDimensionamento();

		// carrega os dados do arquivo e monta a curva conforme as informações contidas nele
		// os dados serão cruzados com o arquivo PL.xlsx aba "mini"
		fc.setPath(cpathfile);
		assertTrue( ( fc.lerArquivo() ) );
			
		// atribui a quantidade total de intervalos do dimensionamento
		diaDime.setTotalIntervalos(18);
	
		// o número de intervalos que cada agente atuará
		diaDime.setQtdeIntervalosAgentes(12);
		
		// adiciona um tipo de curva qualquer para o dia
		diaDime.setTipoCurvaDia( tipoCurva.SEM );
		
		// define a quantidade de segundos do intervalo
		diaDime.setSegundosIntervalo(1800); // adiciona 30 minutos para o intervalo
		
		// inclui o Nível de Serviço meta e tempo de espera aceitável
		diaDime.setNsMeta(0.90); // 90%
		diaDime.setTempoAceitavelNs(10);  // 10 segundos
		diaDime.setBlocking(0.01);  // 1% de blocking
		
		// adiciona o dia do dimensionamento
		diaDime.setDia(new GregorianCalendar(2015, 06, 12 )); // 12/06/2015
		
		//chamadas = 2.348 e TMA = 450
		diaDime.setChamadas(diaDimeChamadas);
		diaDime.setTMA(diaDimeTMA);
		
		// captura a única curva inserida
		diaDime.setCurvaDistribuicao(fc.getCurvaEspecifica(0));
		
		// dispara a carga dos dados nos intervalos do dia a ser dimensionado
		assertFalse( ( diaDime.start() ) );
	}
}
