package wfmjr;

import wfmpack.DiaDimensionamento;
import wfmpack.Erlang;
import wfmpack.FileCurvas;
import wfmpack.Intervalo;
import wfmpack.tipoCurva;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WFMjr {

	public static void main(String[] args) {
		
		String cpathfile;
		FileCurvas fc;
		String path = WFMjr.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = "";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Opções das classes para teste");
		System.out.println("1 - Classe Erlang");
		System.out.println("2 - Classe Intervalo");
		System.out.println("3 - Classe FileCurvas");
		System.out.println("4 - Classe DiaDimensionamento");
		System.out.println("Digite o número do teste desejado:");
		 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    String a = s.substring(0, 1);
		    
		    switch (a) {

			    case "1" :
					Erlang erlc1h = new Erlang();
					Erlang erlc30m = new Erlang();
					Erlang erlc15m = new Erlang();

					double NS = 0.90;
					int tempoAceitavel = 10;
					double chamadas = 667;
					double TMA = 150;
					int numAgentes = 34;

			    	// adiciona o período como 1 hora
					erlc1h.setIntervalInSeconds(3600); 
					erlc1h.agent(NS, tempoAceitavel, chamadas, TMA);
					erlc1h.SLA(numAgentes, tempoAceitavel,chamadas,TMA);
		
					System.out.println( "Valores para 1 hora" );
					erlc1h.exibir();
					System.out.println("ASA: " + erlc1h.ASA( 34, 180, 450 ));
					
					chamadas = 180;
					TMA = 450;
					numAgentes = 48;
		
					// adiciona o período com 1/2 hora
					erlc30m.setIntervalInMinutes(30); 
					erlc30m.agent(NS, tempoAceitavel, chamadas, TMA);
					erlc30m.SLA(numAgentes,tempoAceitavel,chamadas,TMA);
		
					System.out.println("Valores para 1/2 hora" );
					erlc30m.exibir();
		
					chamadas = 180;
					TMA = 250;
					numAgentes = 59;
		
					// adiciona o período com 15 minutos
					erlc15m.setIntervalInMinutes(15); 
					erlc15m.agent(NS, tempoAceitavel, chamadas, TMA);
					erlc15m.SLA(numAgentes,tempoAceitavel,chamadas,TMA);
		
					System.out.println("Valores para 15 minutos" );
					erlc15m.exibir();
					break;
			
			    case "2" :
					Intervalo int1 = new Intervalo();
					Intervalo int2 = new Intervalo();
					Intervalo int3 = new Intervalo();
					
					//---------------------------------------------------
					// teste classe intervalo
					int1.setIntervalInSeconds(3600); // intervalo de 1h
					int1.setNsMeta(0.90); // 90%
					int1.setTempoEsperaAceitavel(10); // 10 segundos
					int1.setChamadas(667); // qtde chamadas
					int1.setTMA(150); // valor do tma em segundos
					int1.calcularAgentes();
					int1.setAgentesDimensionados(34); // recursos dimensionados para o intervalo
					int1.exibir();
					
					int2.setIntervalInSeconds(1800); // intervalo de 1h
					int2.setNsMeta(0.90); // 90%
					int2.setTempoEsperaAceitavel(10); // 10 segundos
					int2.setChamadas(180); // qtde chamadas
					int2.setTMA(450); // valor do tma em segundos
					int2.calcularAgentes();
					int2.setAgentesDimensionados(48); // recursos dimensionados para o intervalo
					int2.exibir();
					
					int3.setIntervalInSeconds(900); // intervalo de 1h
					int3.setNsMeta(0.90); // 90%
					int3.setTempoEsperaAceitavel(10); // 10 segundos
					int3.setChamadas(180); // qtde chamadas
					int3.setTMA(250); // valor do tma em segundos
					int3.calcularAgentes();
					int3.setAgentesDimensionados(59); // recursos dimensionados para o intervalo
					int3.exibir();
					break;
				
			    case "3" :
			    	cpathfile = "src/tests/resources/testeimport.xlsx";
					fc = new FileCurvas();
					
					fc.setPath(cpathfile);
					if (fc.lerArquivo())
						fc.exibir();
					else
						System.out.println("Leitura sem sucesso");
					break;
					
			    case "4" :
			    	//cpathfile = "src/tests/resources/testeimport.xlsx";
			    	cpathfile = "src/tests/resources/curva_teste1.xlsx";
					fc = new FileCurvas();
					double diaDimeChamadas = 2348;
					double diaDimeTMA = 450;
					
					DiaDimensionamento diaDime = new DiaDimensionamento();

					// carrega os dados do arquivo e monta a curva conforme as informações contidas nele
					// os dados serão cruzados com o arquivo PL.xlsx aba "mini"
					fc.setPath(cpathfile);
					if ( fc.lerArquivo() ) {
						
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
						if ( diaDime.start() ) {
							
							// linha | qtde recursos
							// 0 = 37 agentes
							// 3 = 11 agentes
							// 4 = 11 agentes
							// 6 = 25 agentes
							diaDime.setAgentesHorario(0, 37);
							diaDime.setAgentesHorario(3, 11);
							diaDime.setAgentesHorario(4, 11);
							diaDime.setAgentesHorario(6, 25);
							
							// exibe o conteúdo em saída básica
							diaDime.exibir();
							
						}
						else
							System.out.println("Erro na carga das informações dos intervalos");
					}
					else
						System.out.println("Leitura das curvas sem sucesso");
					break;
				
			    default:
			    	System.out.println("Opção não identificada!");
		    }
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
