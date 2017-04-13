package wfmpack;

import java.util.GregorianCalendar;

public class Intervalo extends Erlang {
	
	private GregorianCalendar dataHora;
	private double NsMeta = 0.0;
	private int TempoEsperaAceitavel = 0;
	private double chamadas = 0.0;
	private double TMA = 0.0;
	
	private int agentesNecessarios = 0;

	private int agentesDimensionados = 0;
	private double NsDimensionado = 0.0;
	private double produtividadeDimensionada = 0.0;

	//--------------------------------------
	// m�todos restritos


	//--------------------------------------
	// m�todos p�blicos
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
		return NsMeta;
	}
	/** setNsMeta
	 * @param nsMeta double - define o valor de n�vel de servi�o para o intervalo
	 */
	public void setNsMeta(double nsMeta) {
		NsMeta = nsMeta;
	}
	/** getTempoEsperaAceitavel()
	 * @return tempoEsperaAceitavel	int - devolve o valor definido para o tempo aceitavel
	 */
	public int getTempoEsperaAceitavel() {
		return TempoEsperaAceitavel;
	}
	/** setTempoEsperaAceitavel
	 * @param tempoEsperaAceitavel int - o valor de tempo aceit�vel para o N�vel de Servi�o
	 */
	public void setTempoEsperaAceitavel(int tempoEsperaAceitavel) {
		TempoEsperaAceitavel = tempoEsperaAceitavel;
	}
	/** getChamadas() 
	 * @return chamadas	double - quantidade de chamadas definida para o intervalo
	 */
	public double getChamadas() {
		return chamadas;
	}
	/** setChamadas
	 * @param chamadas 	double - valor para definir de chamadas ao intervalo
	 */
	public void setChamadas(double chamadas) {
		this.chamadas = chamadas;
	}
	/** getTMA() 
	 * @return tMA 	double - tempo m�dio de atendimento definido para o intervalo
	 */
	public double getTMA() {
		return TMA;
	}
	/** setTMA
	 * @param TMA 	double - valor tempo m�dio de atendimento para o intervalo
	 */
	public void setTMA(double tMA) {
		TMA = tMA;
	}
	/** getAgentesNecessarios()
	 * @return agentesNecessarios	int - n�mero de recursos exigidos para atingir ao n�vel de servi�o [valor calculado pelo Erlang.agent()]
	 */
	public int getAgentesNecessarios() {
		return agentesNecessarios;
	}
	/** getAgentesDimensionados()
	 * @return agentesDimensionados int - quantidade de recursos dimensionados
	 */
	public int getAgentesDimensionados() {
		return agentesDimensionados;
	}
	/** setAgentesDimensionados: define a quantidade de recursos para o intervalo
	 * @param agentesDimensionados int - n�mero de recursos dimensionado para o intervalo
	 */
	public boolean setAgentesDimensionados(int agentesDimensionados) {
		
		boolean parametrosOk = false;
		
		if ( this.getNsMeta() > 0.0 && 
				this.getTempoEsperaAceitavel() > 0.0 &&
				this.getChamadas() > 0 && 
				this.getTMA() > 0.0 &&
				this.getSecondsInterval() > 0 ) {
			
			parametrosOk = true;
			
			this.agentesDimensionados = agentesDimensionados;

			// calcula o n�ve de servi�o com a nova quantidade 
			this.NsDimensionado = this.SLA(this.getAgentesDimensionados(), this.getTempoEsperaAceitavel(), this.getChamadas(), this.getTMA());
			
			// calcula a produtividade considerando o n�mero de recursos inseridos para o intervalo
			this.produtividadeDimensionada = (( this.getChamadas() * this.getTMA() ) / 
												( this.getAgentesDimensionados() * this.getSecondsInterval() ) );
			if (this.produtividadeDimensionada > 1)
				this.produtividadeDimensionada = 1;
		}
		return parametrosOk;
	}
	/** getNsDimensionado()
	 * @return nsDimensionado	double - n�vel de servi�o dimensionado
	 */
	public double getNsDimensionado() {
		return NsDimensionado;
	}
	/** getProdutividadeDimensionada()
	 * @return produtividadeDimensionada	double - produtividade dimensionada
	 */
	public double getProdutividadeDimensionada() {
		return produtividadeDimensionada;
	}
	
	/** calcularAgentes: calcula o n�mero de recursos necess�rios para o intervalo
	 * 
	 * @return boolean - indica se foi poss�vel calcular os recursos ou n�o
	 */
	public boolean calcularAgentes() {
		agentesNecessarios = this.agent(this.getNsMeta(), this.getTempoEsperaAceitavel(), this.getChamadas(), this.getTMA());
		return !this.hasError();
	}
	
	/** exibir as informa��es em sa�da b�sica (texto... System.out.println)
	 * exibe o n�mero de recursos e o n�vel de servi�o
	 */
	public void exibir(){
		System.out.println( "---------------------------" );
		System.out.println( "Segundos Intervalo = " + getSecondsInterval() );
		System.out.println( "N�vel de Servi�o = " +  getNsMeta() );
		System.out.println( "Tempo Aceit�vel = " + getTempoEsperaAceitavel() );
		System.out.println( "Volume = " + getChamadas() );
		System.out.println( "TMA = " + getTMA() );
		System.out.println( "HC Necess�rio = " + getAgentesNecessarios() );
		System.out.println( "HC Dimensionado = " + getAgentesDimensionados() );
		System.out.println( "NS Dimensionado = " + getNsDimensionado() );
		System.out.println( "Produtividade = " + getProdutividadeDimensionada() );
		System.out.println( "---------------------------" );
	};
}
