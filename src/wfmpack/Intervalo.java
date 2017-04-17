package wfmpack;

import java.util.GregorianCalendar;

public class Intervalo extends Erlang {
	
	private GregorianCalendar dataHora;

	//--------------------------------------
	// m�todos restritos


	//--------------------------------------
	// m�todos p�blicos
	/** Construtor sem par�metros
	 */
	public Intervalo(){
	}
	
	/** getData()
	 * @return o dia e hora configurado para o intervalo
	 */
	public GregorianCalendar getDataHora() {
		return dataHora;
	}
	/** setData
	 * @param data - define o dia e hora do intervalo
	 */
	public void setDataHora(GregorianCalendar data) {
		this.dataHora = data;
	}
	/** getNsMeta()
	 * @return nsMeta 	double - devolve o valor definido para o n�vel de servi�o do intervalo
	 */
	public double getNsMeta() {
		return this.getTargetSLA();
	}
	/** setNsMeta
	 * @param nsMeta double - define o valor de n�vel de servi�o para o intervalo
	 */
	public void setNsMeta(double nsMeta) {
		this.setTargetSLA(nsMeta);
	}
	/** getTempoEsperaAceitavel()
	 * @return tempoEsperaAceitavel	int - devolve o valor definido para o tempo aceitavel
	 */
	public int getTempoEsperaAceitavel() {
		return this.getTargetTime();
	}
	/** setTempoEsperaAceitavel
	 * @param tempoEsperaAceitavel int - o valor de tempo aceit�vel para o N�vel de Servi�o
	 */
	public void setTempoEsperaAceitavel(int tempoEsperaAceitavel) {
		this.setTargetTime(tempoEsperaAceitavel);
	}
	/** getChamadas() 
	 * @return chamadas	double - quantidade de chamadas definida para o intervalo
	 */
	public double getChamadas() {
		return this.getCalls();
	}
	/** setChamadas
	 * @param chamadas 	double - valor para definir de chamadas ao intervalo
	 */
	public void setChamadas(double chamadas) {
		this.setCalls(chamadas);
	}
	/** getTMA() 
	 * @return tMA 	double - tempo m�dio de atendimento definido para o intervalo
	 */
	public double getTMA() {
		return this.getAverageAnswerTime();
	}
	/** setTMA
	 * @param TMA 	double - valor tempo m�dio de atendimento para o intervalo
	 */
	public void setTMA(double tMA) {
		this.setAverageAnswerTime(tMA);
	}
	/** getAgentesNecessarios()
	 * @return agentesNecessarios	int - n�mero de recursos exigidos para atingir ao n�vel de servi�o [valor calculado pelo Erlang.agent()]
	 */
	public int getAgentesNecessarios() {
		return this.getNecessaryAgents();
	}
	/** getAgentesDimensionados()
	 * @return agentesDimensionados int - quantidade de recursos dimensionados
	 */
	public int getAgentesDimensionados() {
		return this.getInsertedAgents();
	}
	/** setAgentesDimensionados: define a quantidade de recursos para o intervalo
	 * @param agentesDimensionados int - n�mero de recursos dimensionado para o intervalo
	 */
	public boolean setAgentesDimensionados(int agentesDimensionados) {
		this.SLA( agentesDimensionados,
				this.getTempoEsperaAceitavel(),
				this.getChamadas(),
				this.getTMA());
		
		return !this.hasError();
	}
	/** getNsDimensionado()
	 * @return nsDimensionado	double - n�vel de servi�o dimensionado
	 */
	public double getNsDimensionado() {
		return this.getSLA();
	}
	/** getProdutividadeDimensionada()
	 * @return produtividadeDimensionada	double - produtividade dimensionada
	 */
	public double getProdutividadeDimensionada() {
		return this.getProductivity();
	}
	
	/** calcularAgentes: calcula o n�mero de recursos necess�rios para o intervalo
	 * @return boolean - indica se foi poss�vel calcular os recursos ou n�o
	 */
	public boolean calcularAgentes() {
		this.agent(this.getNsMeta(),
				this.getTempoEsperaAceitavel(),
				this.getChamadas(),
				this.getTMA());
		
		return !this.hasError();
	}
	
	/** exibir as informa��es em sa�da b�sica (texto... System.out.println)
	 * exibe o n�mero de recursos e o n�vel de servi�o
	 */
	public void exibir(){
		System.out.println( "---------------------------" );
		System.out.println( "Segundos Intervalo = " + this.getSecondsInterval() );
		System.out.println( "N�vel de Servi�o = " +  this.getNsMeta() );
		System.out.println( "Tempo Aceit�vel = " + this.getTempoEsperaAceitavel() );
		System.out.println( "Volume = " + this.getChamadas() );
		System.out.println( "TMA = " + this.getTMA() );
		System.out.println( "HC Necess�rio = " + this.getAgentesNecessarios() );
		System.out.println( "HC Dimensionado = " + this.getAgentesDimensionados() );
		System.out.println( "NS Dimensionado = " + this.getNsDimensionado() );
		System.out.println( "Produtividade = " + this.getProdutividadeDimensionada() );
		System.out.println( "---------------------------" );
	};
}
