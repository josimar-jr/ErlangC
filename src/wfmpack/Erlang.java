package wfmpack;

public class Erlang {
	
	public static final double maxAccuracy = 0.00001;
	
	// declara��o dos atributos
	private int minutosIntervalo;
	private int segundosIntervalo;
	
	private int agenteEstimado;
	private double nivelServicoEstimado;

	//declara��o dos contrutores
	/** Construtor b�sico para inicializa��o posterior dos valores 
	 * @return objeto do tipo Erlang para uso dos m�todos agent e SLA
	 */
	public Erlang() {
	}
	
	/** construtor espec�fico para iniciar calculando a quantidade de recursos necess�rios
	 * considerando as informa��es de N�vel de Servi�o, Tempo Aceit�vel e TMA
	 * 
	 * @param intervaloSegundos	int 	- determina o intervalo em segundos para uso nos c�lculos (ex. 900, 1800)
	 * @param SLAMeta	double	- indica qual o n�vel de servi�o dever� ser utilizado como meta no c�lculo de recursos
	 * @param tempoEsperaAceitavel	int	- define qual o tempo de resposta para contar uma chamada no n�vel de servi�o (at� quando pode ser respondida)
	 * @param chamadas 	double	- quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param TMA	double	- tempo m�dio de atendimento planejado para o intervalo
	 * @return objeto Erlang com as informa��es preenchidas
	 */
	public Erlang( int intervaloSegundos, double SLAMeta, int tempoEsperaAceitavel, double chamadas, double TMA ){
		this.setSegundosIntervalo(intervaloSegundos);
		this.agent(SLAMeta, tempoEsperaAceitavel, chamadas, TMA);
	}
	
	/** Construtor espec�fico para iniciar calculando o n�vel de servi�o
	 *  considerando a quantidade de recursos informada
	 * @param intervaloSegundos, determina o intervalo em segundos para uso nos c�lculos (ex. 900, 1800)
	 * @param numAgentes, define qual o n�mero de agentes a ser utilizado no c�lculo do n�vel de servi�o
	 * @param tempoEsperaAceitavel, define qual o tempo de resposta para contar uma chamada no n�vel de servi�o (at� quando pode ser respondida)
	 * @param chamadas, quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param TMA, tempo m�dio de atendimento planejado para o intervalo
	 * @return objeto Erlang
	 */
	public Erlang( int intervaloSegundos, int numAgentes, int tempoEsperaAceitavel, double chamadas, double TMA ){
		this.setMinutoIntervalo(intervaloSegundos);
		this.SLA(numAgentes, tempoEsperaAceitavel, chamadas, TMA);
	}
	
	// in�cio declara��o dos m�todos p�blicos
	/** exibe as informa��es em sa�da b�sica (texto... System.out.println)
	 * exibe o n�mero de recursos e o n�vel de servi�o
	 */
	public void exibir(){
		System.out.println( "Recurso = " + agenteEstimado );
		System.out.println( "Nivel de Servico = " + nivelServicoEstimado );
	};
	
	/** Calcula o n�mero de recursos considerando as informa��es de n�vel de servi�o e chamadas para o intervalo
	 * @param SLA	double	- indica qual o n�vel de servi�o dever� ser utilizado como meta no c�lculo de recursos
	 * @param serviceTime	int 	- define qual o tempo de resposta para contar uma chamada no n�vel de servi�o (at� quando pode ser respondida)
	 * @param callsPerHour	double	- quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param AHT	double	- tempo m�dio de atendimento planejado para o intervalo
	 * @return agent	int	- n�mero de recursos calculados
	 */
	// utiliza o n�vel de servi�o planejado (SLA), o tempo aceit�vel de espera (serviceTime), 
	public int agent( double SLA, int serviceTime, double callsPerHour, double AHT ){	
		double birthRate, deathRate, trafficRate;
		double erlangs, utilisation, C, SLQueued;
		int maxIterate, count;
		int noAgents = 0;
		double server;

		if (segundosIntervalo > 0) {
			if (SLA > 1)
				SLA = 1;
	
			birthRate = callsPerHour;
			deathRate = segundosIntervalo / AHT;
	
			// calcula a intensidade de tr�fego
			trafficRate = birthRate / deathRate;
	
			// calcula o numero de Erlangs/horas
			erlangs = ( (int)(birthRate * (AHT)) ) / segundosIntervalo + 0.5;
	
			// inicia o n�mero de agentes para 100% de uso
			if (erlangs < 1)
				noAgents = 1;
			else
				noAgents = ( (int)(erlangs) );
			utilisation = trafficRate / noAgents;
			// agora busca o n�mero real e o n�mero abaixo de 100% de uso
			while (utilisation > 1){
				noAgents += 1;
				utilisation = trafficRate / noAgents;
			}
	
			maxIterate = noAgents * 100;
	
			for (count = 1 ; count <= maxIterate; count++){
				utilisation = trafficRate / noAgents;
				if (utilisation < 1){
					server = noAgents;
					C = erlangC(server, trafficRate);
	
					// encontra o n�vel do SLA com o n�mero de agentes informado
					SLQueued = 1 - C * ( Math.pow( Math.E, ((trafficRate - server) * serviceTime / AHT) ) ); // usa a constante de Euller
	
					if (SLQueued < 0)
						SLQueued = 0;
	
					if (SLQueued >= SLA)
						count = maxIterate;
	
					// insere um limite na precis�o do SL (nunca ir� atingir 100%)
					if (SLQueued > (1 - maxAccuracy))
						count = maxIterate;
				}
				if (count != maxIterate)
					noAgents += 1;
			}
		}
		agenteEstimado = noAgents;
		return noAgents;
	}
	/** SLA: Calcula o n�vel de servi�o baseado nos par�metros recebidos
	 * 
	 * @param agents	int	- n�mero de recursos base para c�lculo do n�vel de servi�o
	 * @param serviceTime	int - tempo aceit�vel de espera para c�lculo do n�vel de servi�o
	 * @param callsPerHour	double	- quantidade de chamadas projetada 
	 * @param AHT	double	- tempo m�dio de atendimento para o intervalo
	 * @return SLA	double	- valor calculado do n�vel de servi�o 
	 */
	public double SLA( int agents, int serviceTime, double callsPerHour, double AHT ) { 
		double SLA = 0.0;

		double birthRate, deathRate, trafficRate, server;
		double utilisation, C, SLQueued;

		birthRate = callsPerHour;
		deathRate = segundosIntervalo / AHT;

		// calcula a intensidade de tr�fego
		trafficRate = birthRate / deathRate;
		utilisation = trafficRate / agents;

		if (utilisation >= 1)
			utilisation = 0.99;

		server = agents;
		C = erlangC(server, trafficRate);

		// Calcula o n�vel de servi�o considerando a fila e chamadas n�o enfileiradas
		// revisada a f�rmula com agradecimento a Tim Bolte e Jorn Lodahl pela ajuda/inser��o
		SLQueued = 1 - C * Math.pow( Math.E, (trafficRate-server)*serviceTime / AHT );

		nivelServicoEstimado = SLA = minMax(SLQueued, 0, 1); // garante que o resultado esteja dentro dos limites
		return SLA;
	}
	/**  nLines - calcula o n�mero de linha/troncos necess�rios considerando 
	 *  o % de bloqueio e a intensidade (chamadas x tma)
	 * @param intensity double - n�mero de erlangs para o intervalo
	 * @param blocking double - % de chamadas que ser�o bloqueadas
	 * @return nLines double - n�mero de linhas/troncos necess�rios
	 */
	public double nLines( double intensity, double blocking ){
		double nLines = 0;
		double B = 0;
		double count = 0;
		double sngCount = 0;
		int maxIterate = 0;
		
		if ( intensity >= 0 && blocking >= 0 ) {
			maxIterate = 65535;
			
			for ( count = intCeiling( intensity ); count <= maxIterate; count++ ){
				sngCount = count;
				B = erlangB(sngCount, intensity);
				
				if ( B <= blocking)
					break;
			}
			
			if (count == maxIterate)
				count = 0;

			// retorna a quantidade identificada
			nLines = count;
		}
		
		return nLines;
	}
	/** ASA - calcula o tempo estimado de espera para o atendimento 
	 * @param agents int - quantidade de agentes para o intervalo
	 * @param callsPerHour double - quantidade de chamadas para o intervalo
	 * @param AHT - tempo m�dio de atendimento planejado para o intervalo
	 * @return aveAnswer int - tempo de espera calculado
	 */
	public double ASA(int agents, double calls, double AHT){
		double birthRate = 0;
		double deathRate = 0;
		double trafficRate = 0;
		double utilisation = 0;
		double answerTime = 0;
		double aveAnswer = 0;
		double C = 0;
		double server = 0;
		
		birthRate = calls;
		deathRate = segundosIntervalo / AHT;
		
		// calcula a intensidade de tr�fego
		trafficRate = birthRate / deathRate;
		server = agents;
		utilisation = trafficRate / server;
		
		if (utilisation >= 1)
			utilisation = 0.99;
		
		C = erlangC(server, trafficRate);
		answerTime = C / ( server * deathRate * ( 1 - utilisation) );
		aveAnswer = secs(answerTime);
		
		return aveAnswer;
	}
	/** setMinutoIntervalo: define o valor em minutos do intervalo para calcular os recursos e n�vel de servi�o
	 * tamb�m preenche o valor em segundos do intervalo
	 * 
	 * @param minutos	int	- quantidade em minutos de dura��o do intervalo
	 */
	public void setMinutoIntervalo( int minutos ){
		minutosIntervalo = minutos;
		segundosIntervalo = minutos * 60 ;
		return ;
	}
	/** setSegundosIntervalo: define o valor em segundos do intervalo para calcular os recursos e n�vel de servi�o
	 * tamb�m preenche o valor em minutos do intervalo 
	 * 
	 * @param minutos	int	- quantidade em segundos de dura��o do intervalo
	 */
	public void setSegundosIntervalo( int segundos ){
		segundosIntervalo = segundos;
		minutosIntervalo = segundos / 60;
		return;
	}
	/** getSegundosIntervalo identifica a quantidade de segundos definida para os c�lculos
	 * 
	 * @return segundosIntervalo	int - retorna o valor definido
	 */
	public int getSegundosIntervalo(){
		return segundosIntervalo;
	}

	/** Retorna o n�mero de recursos
	 * @return agenteEstimado	int - valor calculado de agentes usando o m�todo 'agent' 
	 */
	public int getAgenteEstimado() {
		return agenteEstimado;
	}
	/** Retorna o n�vel de servi�o
	 * @return the nivelServicoEstimado	double	- valor calculado usando o m�todo 'SLA'
	 */
	public double getNivelServicoEstimado() {
		return nivelServicoEstimado;
	}
	
	// in�cio declara��o dos m�todos private
	/** calcula o erlangB
	 * @param servers int - n�mero de atendentes
	 * @param intensity - propor��o de chamadas x tempo m�dio de atendimento
	 * @return erlangB double - valor calculado de n�vel de servi�o
	 */
	private double erlangB(double servers, double intensity){
		double erlangB = 0.0;
		double val = 0;
		double last = 0;
		double B = 0;
		int count, maxIterate;

		if (servers > 0 && intensity > 0){
			maxIterate = (int)(servers);
			val = intensity;
			last = 1;
			for (count = 1; count <= maxIterate; count++){
				B = (val * last) / (count + (val * last));
				last = B;
			}
		}
		erlangB = minMax( B, 0, 1 );
		return erlangB;
	}
	
	/** calcula o erlangC
	 * @param servers int - n�mero de atendentes
	 * @param intensity - propor��o de chamadas x tempo m�dio de atendimento
	 * @return erlangC double - valor calculado de n�vel de servi�o
	 */
	private double erlangC(double servers, double intensity){ 
		double erlangC = 0.0;
		double B, C;

		if (servers > 0 && intensity > 0 ){
			B = erlangB(servers, intensity);
			C = B / (((intensity / servers ) * B ) + ( 1 - (intensity / servers ) ) );
			erlangC = minMax(C, 0, 1);
		}

		return erlangC;
	}
	
	/** restringe o resultado entre a faixa informada
	 * @param val double - valor a ser avaliado
	 * @param min double - limite inferior
	 * @param max double - limite superior
	 * @return minMax double - valor corrigido caso estivesse fora da faixa informada
	 */
	private double minMax(double val, double min, double max){	
		double minMax = val;

		if (val < min )
			minMax = min;
		else
			if (val > max)
				minMax = max;

		return minMax;
	}
	/** intCeiling - arredonda para o n�mero maior mais pr�ximo
	 * 
	 * @param val double - valor a ser avaliado
	 * @return intCeiling double - valor alterado
	 */
	private double intCeiling( double val ){
		double intCeiling = 0;
		
		if ( val < 0 )
			intCeiling = val - 0.9999;
		else
			intCeiling = val + 0.9999;
		
		intCeiling = (int)(intCeiling);
		
		return intCeiling;
	}
	/** secs - converte um n�mero de horas em segundos
	 * @param amount double - 
	 * @return secs double - quantidade de segundos  
	 */
	private double secs( double amount ){
		return ( (amount * segundosIntervalo ) );
		// return ( (int)( (amount * 3600) + 0.5 ) ); // linha original no erlangC
	}
}
