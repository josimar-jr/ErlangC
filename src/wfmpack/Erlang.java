package wfmpack;

public class Erlang {
	
	public static final double maxAccuracy = 0.00001;
	
	// declaração dos atributos
	private int minutosIntervalo;
	private int segundosIntervalo;
	
	private int agenteEstimado;
	private double nivelServicoEstimado;

	//declaração dos contrutores
	/** Construtor básico para inicialização posterior dos valores 
	 * @return objeto do tipo Erlang para uso dos métodos agent e SLA
	 */
	public Erlang() {
	}
	
	/** construtor específico para iniciar calculando a quantidade de recursos necessários
	 * considerando as informações de Nível de Serviço, Tempo Aceitável e TMA
	 * 
	 * @param intervaloSegundos	int 	- determina o intervalo em segundos para uso nos cálculos (ex. 900, 1800)
	 * @param SLAMeta	double	- indica qual o nível de serviço deverá ser utilizado como meta no cálculo de recursos
	 * @param tempoEsperaAceitavel	int	- define qual o tempo de resposta para contar uma chamada no nível de serviço (até quando pode ser respondida)
	 * @param chamadas 	double	- quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param TMA	double	- tempo médio de atendimento planejado para o intervalo
	 * @return objeto Erlang com as informações preenchidas
	 */
	public Erlang( int intervaloSegundos, double SLAMeta, int tempoEsperaAceitavel, double chamadas, double TMA ){
		this.setSegundosIntervalo(intervaloSegundos);
		this.agent(SLAMeta, tempoEsperaAceitavel, chamadas, TMA);
	}
	
	/** Construtor específico para iniciar calculando o nível de serviço
	 *  considerando a quantidade de recursos informada
	 * @param intervaloSegundos, determina o intervalo em segundos para uso nos cálculos (ex. 900, 1800)
	 * @param numAgentes, define qual o número de agentes a ser utilizado no cálculo do nível de serviço
	 * @param tempoEsperaAceitavel, define qual o tempo de resposta para contar uma chamada no nível de serviço (até quando pode ser respondida)
	 * @param chamadas, quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param TMA, tempo médio de atendimento planejado para o intervalo
	 * @return objeto Erlang
	 */
	public Erlang( int intervaloSegundos, int numAgentes, int tempoEsperaAceitavel, double chamadas, double TMA ){
		this.setMinutoIntervalo(intervaloSegundos);
		this.SLA(numAgentes, tempoEsperaAceitavel, chamadas, TMA);
	}
	
	// início declaração dos métodos públicos
	/** exibe as informações em saída básica (texto... System.out.println)
	 * exibe o número de recursos e o nível de serviço
	 */
	public void exibir(){
		System.out.println( "Recurso = " + agenteEstimado );
		System.out.println( "Nivel de Servico = " + nivelServicoEstimado );
	};
	
	/** Calcula o número de recursos considerando as informações de nível de serviço e chamadas para o intervalo
	 * @param SLA	double	- indica qual o nível de serviço deverá ser utilizado como meta no cálculo de recursos
	 * @param serviceTime	int 	- define qual o tempo de resposta para contar uma chamada no nível de serviço (até quando pode ser respondida)
	 * @param callsPerHour	double	- quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param AHT	double	- tempo médio de atendimento planejado para o intervalo
	 * @return agent	int	- número de recursos calculados
	 */
	// utiliza o nível de serviço planejado (SLA), o tempo aceitável de espera (serviceTime), 
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
	
			// calcula a intensidade de tráfego
			trafficRate = birthRate / deathRate;
	
			// calcula o numero de Erlangs/horas
			erlangs = ( (int)(birthRate * (AHT)) ) / segundosIntervalo + 0.5;
	
			// inicia o número de agentes para 100% de uso
			if (erlangs < 1)
				noAgents = 1;
			else
				noAgents = ( (int)(erlangs) );
			utilisation = trafficRate / noAgents;
			// agora busca o número real e o número abaixo de 100% de uso
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
	
					// encontra o nível do SLA com o número de agentes informado
					SLQueued = 1 - C * ( Math.pow( Math.E, ((trafficRate - server) * serviceTime / AHT) ) ); // usa a constante de Euller
	
					if (SLQueued < 0)
						SLQueued = 0;
	
					if (SLQueued >= SLA)
						count = maxIterate;
	
					// insere um limite na precisão do SL (nunca irá atingir 100%)
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
	/** SLA: Calcula o nível de serviço baseado nos parâmetros recebidos
	 * 
	 * @param agents	int	- número de recursos base para cálculo do nível de serviço
	 * @param serviceTime	int - tempo aceitável de espera para cálculo do nível de serviço
	 * @param callsPerHour	double	- quantidade de chamadas projetada 
	 * @param AHT	double	- tempo médio de atendimento para o intervalo
	 * @return SLA	double	- valor calculado do nível de serviço 
	 */
	public double SLA( int agents, int serviceTime, double callsPerHour, double AHT ) { 
		double SLA = 0.0;

		double birthRate, deathRate, trafficRate, server;
		double utilisation, C, SLQueued;

		birthRate = callsPerHour;
		deathRate = segundosIntervalo / AHT;

		// calcula a intensidade de tráfego
		trafficRate = birthRate / deathRate;
		utilisation = trafficRate / agents;

		if (utilisation >= 1)
			utilisation = 0.99;

		server = agents;
		C = erlangC(server, trafficRate);

		// Calcula o nível de serviço considerando a fila e chamadas não enfileiradas
		// revisada a fórmula com agradecimento a Tim Bolte e Jorn Lodahl pela ajuda/inserção
		SLQueued = 1 - C * Math.pow( Math.E, (trafficRate-server)*serviceTime / AHT );

		nivelServicoEstimado = SLA = minMax(SLQueued, 0, 1); // garante que o resultado esteja dentro dos limites
		return SLA;
	}
	/**  nLines - calcula o número de linha/troncos necessários considerando 
	 *  o % de bloqueio e a intensidade (chamadas x tma)
	 * @param intensity double - número de erlangs para o intervalo
	 * @param blocking double - % de chamadas que serão bloqueadas
	 * @return nLines double - número de linhas/troncos necessários
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
	 * @param AHT - tempo médio de atendimento planejado para o intervalo
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
		
		// calcula a intensidade de tráfego
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
	/** setMinutoIntervalo: define o valor em minutos do intervalo para calcular os recursos e nível de serviço
	 * também preenche o valor em segundos do intervalo
	 * 
	 * @param minutos	int	- quantidade em minutos de duração do intervalo
	 */
	public void setMinutoIntervalo( int minutos ){
		minutosIntervalo = minutos;
		segundosIntervalo = minutos * 60 ;
		return ;
	}
	/** setSegundosIntervalo: define o valor em segundos do intervalo para calcular os recursos e nível de serviço
	 * também preenche o valor em minutos do intervalo 
	 * 
	 * @param minutos	int	- quantidade em segundos de duração do intervalo
	 */
	public void setSegundosIntervalo( int segundos ){
		segundosIntervalo = segundos;
		minutosIntervalo = segundos / 60;
		return;
	}
	/** getSegundosIntervalo identifica a quantidade de segundos definida para os cálculos
	 * 
	 * @return segundosIntervalo	int - retorna o valor definido
	 */
	public int getSegundosIntervalo(){
		return segundosIntervalo;
	}

	/** Retorna o número de recursos
	 * @return agenteEstimado	int - valor calculado de agentes usando o método 'agent' 
	 */
	public int getAgenteEstimado() {
		return agenteEstimado;
	}
	/** Retorna o nível de serviço
	 * @return the nivelServicoEstimado	double	- valor calculado usando o método 'SLA'
	 */
	public double getNivelServicoEstimado() {
		return nivelServicoEstimado;
	}
	
	// início declaração dos métodos private
	/** calcula o erlangB
	 * @param servers int - número de atendentes
	 * @param intensity - proporção de chamadas x tempo médio de atendimento
	 * @return erlangB double - valor calculado de nível de serviço
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
	 * @param servers int - número de atendentes
	 * @param intensity - proporção de chamadas x tempo médio de atendimento
	 * @return erlangC double - valor calculado de nível de serviço
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
	/** intCeiling - arredonda para o número maior mais próximo
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
	/** secs - converte um número de horas em segundos
	 * @param amount double - 
	 * @return secs double - quantidade de segundos  
	 */
	private double secs( double amount ){
		return ( (amount * segundosIntervalo ) );
		// return ( (int)( (amount * 3600) + 0.5 ) ); // linha original no erlangC
	}
}
