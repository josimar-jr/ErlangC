package wfmpack;

import java.util.ArrayList;

public class Erlang {
	
	public static final double maxAccuracy = 0.00001;
	
	// declaração dos atributos
	private int intervalInSeconds;
	private int necessaryAgents;
	private int insertedAgents;
	private double targetSLA;
	private double calcSLA;
	private int targetTime;
	private double calls;
	private double averageAnswerTime;
	private double nLines;
	private double intensity;
	private double calcWaitingTime;
	private double blockingPercentage;
	private double productivity;
	private boolean hasError;
	private ArrayList<String> errors = new ArrayList<>();

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
		this.setIntervalInSeconds(intervaloSegundos);
		this.setTargetSLA(SLAMeta);
		this.setTargetTime(tempoEsperaAceitavel);
		this.setCalls(chamadas);
		this.setAverageAnswerTime(TMA);
		this.agent();
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
		this.setIntervalInMinutes(intervaloSegundos);
		this.setInsertedAgents(numAgentes);
		this.setTargetTime(tempoEsperaAceitavel);
		this.setCalls(chamadas);
		this.setAverageAnswerTime(TMA);
		this.SLA();
	}
	
	// início declaração dos métodos públicos
	/** exibe as informações em saída básica (texto... System.out.println)
	 * exibe o número de recursos e o nível de serviço
	 */
	public void exibir(){
		System.out.println( "Recurso = " + necessaryAgents );
		System.out.println( "Nivel de Servico = " + calcSLA );
	};

	/** Calcula o número de recursos considerando as informações no objeto
	 * @return boolean: determina se conseguiu realizar a operação ou não
	 */
	protected boolean agent(){
		double birthRate, deathRate, trafficRate;
		double erlangs, utilisation, C, SLQueued;
		int maxIterate, count;
		int noAgents = 0;
		double server;

		if ( ( this.intervalInSeconds > 0 ) &&
				( this.targetSLA > 0 ) && 
				( this.targetTime > 0 ) && 
				( this.calls > 0 ) &&
				( this.averageAnswerTime > 0 ) ) {

			this.hasError = false;

			if (this.targetSLA > 1){
				this.targetSLA = 1;
			}
			
			birthRate = this.calls;
			deathRate = this.intervalInSeconds / this.averageAnswerTime;
	
			// calcula a intensidade de tráfego
			trafficRate = birthRate / deathRate;
	
			// calcula o numero de Erlangs/horas
			erlangs = ( (int)(birthRate * (this.averageAnswerTime)) ) / this.intervalInSeconds + 0.5;
	
			// inicia o número de agentes para 100% de uso
			if (erlangs < 1){
				noAgents = 1;
			}
			else {
				noAgents = ( (int)(erlangs) );
			}
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
					SLQueued = 1 - C * ( Math.pow( Math.E, ((trafficRate - server) * this.targetTime / this.averageAnswerTime) ) ); // usa a constante de Euller
	
					if (SLQueued < 0){
						SLQueued = 0;
					}
					if (SLQueued >= this.targetSLA) {
						count = maxIterate;
					}
					// insere um limite na precisão do SL (nunca irá atingir 100%)
					if (SLQueued > (1 - maxAccuracy)) {
						count = maxIterate;
					}
				}
				if (count != maxIterate) {
					noAgents += 1;
				}
			}
		}
		else {
			// caso os parâmetros não estejam preenchidos
			this.hasError = true;
			noAgents  = -1;
			this.errors.add("Informe todos os parâmetros para a execução do método.");
		}
		this.necessaryAgents = noAgents;
		return !this.hasError();
	}
	
	/** Calcula o número de recursos considerando as informações de nível de serviço e chamadas para o intervalo
	 * @param SLA	double	- indica qual o nível de serviço deverá ser utilizado como meta no cálculo de recursos
	 * @param serviceTime	int 	- define qual o tempo de resposta para contar uma chamada no nível de serviço (até quando pode ser respondida)
	 * @param callsPerHour	double	- quantidade de chamadas esperadas/planejadas para o intervalo
	 * @param AHT	double	- tempo médio de atendimento planejado para o intervalo
	 * @return agent	int	- número de recursos calculados
	 */
	// utiliza o nível de serviço planejado (SLA), o tempo aceitável de espera (serviceTime), 
	public int agent( double SLA, int serviceTime, double callsPerHour, double AHT ){
		int numberOfAgents = 0;
		this.setTargetSLA(SLA);
		this.setTargetTime(serviceTime);
		this.setCalls(callsPerHour);
		this.setAverageAnswerTime(AHT);
		
		if ( this.agent() ){
			numberOfAgents = this.getNecessaryAgents();
		}
		return numberOfAgents;
	}
	
	/** SLA: Calcula o nível de serviço baseado nas informações no objeto
	 * @return boolean - determina se conseguiu calcular 
	 */
	//public double SLA( int agents, int serviceTime, double callsPerHour, double AHT ) { 
	protected boolean SLA() {
		double birthRate, deathRate, trafficRate, server;
		double utilisation, C, SLQueued;

		if (( this.insertedAgents > 0 ) && 
				( this.targetTime > 0 ) && 
				( this.calls > 0 ) && 
				( this.averageAnswerTime > 0) &&
				( this.intervalInSeconds > 0 ) ){

			this.hasError = false;
			
			birthRate = this.calls;
			deathRate = this.intervalInSeconds / this.averageAnswerTime;
	
			// calcula a intensidade de tráfego
			trafficRate = birthRate / deathRate;
			utilisation = trafficRate / this.insertedAgents;
	
			if (utilisation >= 1)
				utilisation = 0.99;
	
			server = this.insertedAgents;
			C = erlangC(server, trafficRate);
	
			// Calcula o nível de serviço considerando a fila e chamadas não enfileiradas
			// revisada a fórmula com agradecimento a Tim Bolte e Jorn Lodahl pela ajuda/inserção
			SLQueued = 1 - C * Math.pow( Math.E, (trafficRate-server)*this.targetTime / this.averageAnswerTime );
	
			this.calcSLA = minMax(SLQueued, 0, 1); // garante que o resultado esteja dentro dos limites
		}
		else {
			this.hasError = true;
			this.calcSLA = 0;
		}
			
		return !this.hasError();
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

		this.setInsertedAgents(agents);
		this.setTargetTime(serviceTime);
		this.setCalls(callsPerHour);
		this.setAverageAnswerTime(AHT);
		
		if ( this.SLA() ){
			SLA = this.getSLA();
		}
		else {
			SLA = -1;
		}
		
		return SLA;
	}
	/**  nLines - calcula o número de linha/troncos necessários considerando o % de bloqueio
	 * @param blocking double - % de chamadas que serão bloqueadas
	 * @return boolean determina se conseguiu calcular
	 */
	protected boolean nLines( ){
		double B = 0;
		double count = 0;
		double sngCount = 0;
		int maxIterate = 0;
		
		if ( this.intensity == 0 ) {
			this.intensity = ( this.calls * this.averageAnswerTime / this.intervalInSeconds );
		}
		
		if ( this.intensity >= 0 && this.blockingPercentage >= 0 ) {
			this.hasError = false;
			maxIterate = 65535;
			
			for ( count = intCeiling( this.intensity ); count <= maxIterate; count++ ){
				sngCount = count;
				B = erlangB(sngCount, this.intensity);
				
				if ( B <= this.blockingPercentage)
					break;
			}
			
			if (count == maxIterate)
				count = 0;

			// retorna a quantidade identificada
			this.nLines = count;
		}
		else {
			this.hasError = true;
			this.nLines = -1;
		}
		
		return !this.hasError();
	}
	/**  nLines - calcula o número de linha/troncos necessários considerando 
	 *  o % de bloqueio e a intensidade (chamadas x tma)
	 * @param intensity double - número de erlangs para o intervalo
	 * @param blocking double - % de chamadas que serão bloqueadas
	 * @return nLines double - número de linhas/troncos necessários
	 */
	public double nLines( double intensity, double blocking ){
		double nLines = -1;
		this.intensity = intensity;
		this.setBlockingPercentage(blocking);
		if ( this.nLines() ){
			nLines = this.getLines();
		}
		return nLines;
	}
	/** ASA - calcula o tempo estimado de espera para o atendimento 
	 * @return boolean determina se conseguiu calcular ou não
	 */
	protected boolean ASA(){
		double birthRate = 0;
		double deathRate = 0;
		double trafficRate = 0;
		double utilisation = 0;
		double answerTime = 0;
		double aveAnswer = 0;
		double C = 0;
		double server = 0;
		
		if( (this.calls > 0) &&
				( this.intervalInSeconds > 0 ) && 
				( this.averageAnswerTime > 0 ) ) {
			
			this.hasError = false;
			
			birthRate = this.calls;
			deathRate = this.intervalInSeconds / this.averageAnswerTime;
			
			// calcula a intensidade de tráfego
			trafficRate = birthRate / deathRate;
			server = this.insertedAgents;
			utilisation = trafficRate / server;
			
			if (utilisation >= 1) {
				utilisation = 0.99;
			}
			
			C = erlangC(server, trafficRate);
			answerTime = C / ( server * deathRate * ( 1 - utilisation) );
			aveAnswer = secs(answerTime);
		}
		else {
			this.hasError = true;
		}
		this.calcWaitingTime = aveAnswer;
		return !this.hasError();
	}
	/** ASA - calcula o tempo estimado de espera para o atendimento 
	 * @param agents int - quantidade de agentes para o intervalo
	 * @param callsPerHour double - quantidade de chamadas para o intervalo
	 * @param AHT - tempo médio de atendimento planejado para o intervalo
	 * @return aveAnswer int - tempo de espera calculado
	 */
	public double ASA(int agents, double calls, double AHT){
		double aveAnswer = 0;
		this.setInsertedAgents(agents);
		this.setCalls(calls);
		this.setAverageAnswerTime(AHT);
		
		if( this.ASA() ){
			aveAnswer = this.getWaitingTime();
		}
		return aveAnswer;
	}
	/** hasError: indica se existe erro na carga ou preenchimento do objeto
	 * @return boolean, determina se existe erro ou não.
	 */
	public boolean hasError(){
		return this.hasError;
	}
	
	//--------------------------------------------------------
	//  Setters e Getters // INÍCIO
	//--------------------------------------------------------
	/** setIntervalInMinutes: define o valor em minutos do intervalo para calcular os recursos e nível de serviço
	 * também preenche o valor em segundos do intervalo
	 * @param minutos	int	- quantidade em minutos de duração do intervalo
	 */
	public void setIntervalInMinutes( int minutos ){
		intervalInSeconds = minutos * 60 ;
		return ;
	}
	/** setIntervalInSeconds: define o valor em segundos do intervalo para calcular os recursos e nível de serviço
	 * também preenche o valor em minutos do intervalo 
	 * @param minutos	int	- quantidade em segundos de duração do intervalo
	 */
	public void setIntervalInSeconds( int segundos ){
		intervalInSeconds = segundos;
		return;
	}
	/** getSecondsInterval: identifica a quantidade de segundos definida para os cálculos
	 * @return segundosIntervalo	int - retorna o valor definido
	 */
	public int getSecondsInterval(){
		return intervalInSeconds;
	}
	/** setInsertedAgents: Define a quantidade de atendentes
	 * @param agents int - quantidade de agentes para o intervalo
	 */
	public void setInsertedAgents( int agents ){
		this.insertedAgents = agents;
		this.load();
		return;
	}
	/** getInsertedAgents: Retorna a quantidade de atendentes
	 * @return agents int - quantidade de agentes para o intervalo
	 */
	public int getInsertedAgents(){
		return this.insertedAgents;
	}
	/** setTargetSLA: Define qual o nível de serviço meta
	 * @param SLA double - Nível de serviço objetivo
	 */
	public void setTargetSLA( double sla ){
		this.targetSLA = sla;
		this.load();
		return;
	}
	/** getTargetSLA: Retorna qual o nível de serviço meta definido
	 * @return targetSLA double - Nível de serviço objetivo
	 */
	public double getTargetSLA(){
		return this.targetSLA;
	}
	/** setTargetTime: Define qual o tempo aceitável de espera para o nível de serviço
	 * @param time int - tempo aceitável de espera
	 */
	public void setTargetTime( int time ){
		this.targetTime = time;
		this.load();
		return;
	}
	/** getTargetTime: Retorna qual o tempo aceitável de espera para o nível de serviço
	 * @return targetSLA double - tempo aceitável de espera
	 */
	public double getTargetTime(){
		return this.targetSLA;
	}
	/** setCalls: Define qual a quantidade de chamadas para os cáculos
	 * @param calls double - quantidade de chamadas
	 */
	public void setCalls( double calls ){
		this.calls = calls;
		this.load();
		return;
	}
	/** getCalls: Retorna a quantidade de chamadas definida para os cálculos
	 * @return calls double - quantidade de chamadas
	 */
	public double getCalls(){
		return this.calls;
	}
	/** setAverageAnswerTime: Define o tempo médio de atendimento
	 * @param aveTime double - tempo médio de atendimento
	 */
	public void setAverageAnswerTime( double aveTime ){
		this.averageAnswerTime = aveTime;
		this.load();
		return;
	}
	/** getAverageAnswerTime: Retorna o tempo médio de atendimento
	 * @return averageAnswerTime double - tempo médio de atendimento
	 */
	public double getAverageAnswerTime(){
		return this.averageAnswerTime;
	}
	/** setBlockingPercentage: Define o percentual de chamadas que poderão ser bloqueadas
	 * @param blocking double - percentual de bloqueio
	 */
	public void setBlockingPercentage( double blocking ){
		this.blockingPercentage = blocking;
		this.load();
		return;
	}
	/** getBlockingPercentage: Retorna o percentual de bloqueio
	 * @return blockingPercentage double - percentual de bloqueio
	 */
	public double getBlockingPercentage(){
		return this.blockingPercentage;
	}
	/** getNecessaryAgents: Retorna o número de recursos
	 * @return agenteEstimado	int - valor calculado de agentes usando o método 'agent' 
	 */
	public int getNecessaryAgents() {
		return necessaryAgents;
	}
	/** getSLA: Retorna o nível de serviço
	 * @return nivelServicoEstimado	double	- valor calculado usando o método 'SLA'
	 */
	public double getSLA() {
		return calcSLA;
	}
	/** getWaitingTime: Retorna o tempo de espera em segundos para os dados da classe
	 * @return calcWaitingTime double - tempo de espera
	 */
	public double getWaitingTime(){
		return this.calcWaitingTime;
	}
	/** getLines: Retorna a quantidade de linhas calculados
	 * @return nLines double - tempo de espera
	 */
	public double getLines(){
		return this.nLines;
	}
	/** getProductivity: Retorna a produtividade para o intervalo considerando chamadas, tma e atendentes.
	 * @return SLA double - Nível de serviço objetivo
	 */
	public double getProductivity(){
		return this.productivity;
	}
	//--------------------------------------------------------
	//  Setters e Getters // FIM
	//--------------------------------------------------------
	
	//-----------------------------------------------------
	// início declaração dos métodos private
	//-----------------------------------------------------
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
	private double erlangC(double servers, double trafficRate){ 
		double erlangC = 0.0;
		double B, C;

		if (servers > 0 && trafficRate > 0 ){
			B = erlangB(servers, trafficRate);
			C = B / (((trafficRate / servers ) * B ) + ( 1 - (trafficRate / servers ) ) );
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

		if (val < min ){
			minMax = min;
		}
		else {
			if (val > max){
				minMax = max;
			}
		}
		return minMax;
	}
	/** intCeiling - arredonda para o número maior mais próximo
	 * 
	 * @param val double - valor a ser avaliado
	 * @return intCeiling double - valor alterado
	 */
	private double intCeiling( double val ){
		double intCeiling = 0;
		
		if ( val < 0 ){
			intCeiling = val - 0.9999;
		}
		else {
			intCeiling = val + 0.9999;
		}
		
		intCeiling = (int)(intCeiling);
		
		return intCeiling;
	}
	/** secs - converte um número de horas em segundos
	 * @param amount double - 
	 * @return secs double - quantidade de segundos  
	 */
	private double secs( double amount ){
		return ( (amount * intervalInSeconds ) );
	}
	/** load - Realiza a carga das informações conforme os conteúdos vão sendo inseridos no objeto
	 */
	private void load(){
		// calculando intensidade de tráfego
		this.intensity();
		
		// calculando a produtividade
		if (this.intervalInSeconds > 0){
			// calcula a produtividade
			this.productivity = (this.calls * this.averageAnswerTime * this.insertedAgents / this.intervalInSeconds);
			// limita o resultado a 100%
			if (this.productivity > 1){
				this.productivity = 1;
			}
		}
		// calculando os agentes necessários
		this.agent();
		
		// calculando o nível de serviço para o intervalo
		this.SLA();
		
		// calculando o tempo de espera
		this.ASA();
		
		// calculando a quantidade de linhas/troncos
		this.nLines();
		return;
	}
	/** intensity - calcula a intensidade de tráfego conforme chamadas, tma e intervalo são definidos
	 */
	private void intensity(){
		if ((this.calls >0) && 
				(this.averageAnswerTime > 0) &&
				(this.intervalInSeconds > 0)){
			
			this.intensity = ( this.calls * this.averageAnswerTime / this.intervalInSeconds );
		}
	}
}
