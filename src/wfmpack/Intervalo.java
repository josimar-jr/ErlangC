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
	// métodos restritos


	//--------------------------------------
	// métodos públicos
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
	 * @return nsMeta 	double - devolve o valor definido para o nível de serviço do intervalo
	 */
	public double getNsMeta() {
		return NsMeta;
	}
	/** setNsMeta
	 * @param nsMeta double - define o valor de nível de serviço para o intervalo
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
	 * @param tempoEsperaAceitavel int - o valor de tempo aceitável para o Nível de Serviço
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
	 * @return tMA 	double - tempo médio de atendimento definido para o intervalo
	 */
	public double getTMA() {
		return TMA;
	}
	/** setTMA
	 * @param TMA 	double - valor tempo médio de atendimento para o intervalo
	 */
	public void setTMA(double tMA) {
		TMA = tMA;
	}
	/** getAgentesNecessarios()
	 * @return agentesNecessarios	int - número de recursos exigidos para atingir ao nível de serviço [valor calculado pelo Erlang.agent()]
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
	 * @param agentesDimensionados int - número de recursos dimensionado para o intervalo
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

			// calcula o níve de serviço com a nova quantidade 
			this.NsDimensionado = this.SLA(this.getAgentesDimensionados(), this.getTempoEsperaAceitavel(), this.getChamadas(), this.getTMA());
			
			// calcula a produtividade considerando o número de recursos inseridos para o intervalo
			this.produtividadeDimensionada = (( this.getChamadas() * this.getTMA() ) / 
												( this.getAgentesDimensionados() * this.getSecondsInterval() ) );
			if (this.produtividadeDimensionada > 1)
				this.produtividadeDimensionada = 1;
		}
		return parametrosOk;
	}
	/** getNsDimensionado()
	 * @return nsDimensionado	double - nível de serviço dimensionado
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
	
	/** calcularAgentes: calcula o número de recursos necessários para o intervalo
	 * 
	 * @return boolean - indica se foi possível calcular os recursos ou não
	 */
	public boolean calcularAgentes() {
		agentesNecessarios = this.agent(this.getNsMeta(), this.getTempoEsperaAceitavel(), this.getChamadas(), this.getTMA());
		return !this.hasError();
	}
	
	/** exibir as informações em saída básica (texto... System.out.println)
	 * exibe o número de recursos e o nível de serviço
	 */
	public void exibir(){
		System.out.println( "---------------------------" );
		System.out.println( "Segundos Intervalo = " + getSecondsInterval() );
		System.out.println( "Nível de Serviço = " +  getNsMeta() );
		System.out.println( "Tempo Aceitável = " + getTempoEsperaAceitavel() );
		System.out.println( "Volume = " + getChamadas() );
		System.out.println( "TMA = " + getTMA() );
		System.out.println( "HC Necessário = " + getAgentesNecessarios() );
		System.out.println( "HC Dimensionado = " + getAgentesDimensionados() );
		System.out.println( "NS Dimensionado = " + getNsDimensionado() );
		System.out.println( "Produtividade = " + getProdutividadeDimensionada() );
		System.out.println( "---------------------------" );
	};
}
