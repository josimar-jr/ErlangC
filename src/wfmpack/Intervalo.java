package wfmpack;

import java.util.GregorianCalendar;

public class Intervalo extends Erlang {
	
	private GregorianCalendar dataHora;

	//--------------------------------------
	// métodos restritos


	//--------------------------------------
	// métodos públicos
	/** Construtor sem parâmetros
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
	 * @return nsMeta 	double - devolve o valor definido para o nível de serviço do intervalo
	 */
	public double getNsMeta() {
		return this.getTargetSLA();
	}
	/** setNsMeta
	 * @param nsMeta double - define o valor de nível de serviço para o intervalo
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
	 * @param tempoEsperaAceitavel int - o valor de tempo aceitável para o Nível de Serviço
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
	 * @return tMA 	double - tempo médio de atendimento definido para o intervalo
	 */
	public double getTMA() {
		return this.getAverageAnswerTime();
	}
	/** setTMA
	 * @param TMA 	double - valor tempo médio de atendimento para o intervalo
	 */
	public void setTMA(double tMA) {
		this.setAverageAnswerTime(tMA);
	}
	/** getAgentesNecessarios()
	 * @return agentesNecessarios	int - número de recursos exigidos para atingir ao nível de serviço [valor calculado pelo Erlang.agent()]
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
	 * @param agentesDimensionados int - número de recursos dimensionado para o intervalo
	 */
	public boolean setAgentesDimensionados(int agentesDimensionados) {
		this.SLA( agentesDimensionados,
				this.getTempoEsperaAceitavel(),
				this.getChamadas(),
				this.getTMA());
		
		return !this.hasError();
	}
	/** getNsDimensionado()
	 * @return nsDimensionado	double - nível de serviço dimensionado
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
	
	/** calcularAgentes: calcula o número de recursos necessários para o intervalo
	 * @return boolean - indica se foi possível calcular os recursos ou não
	 */
	public boolean calcularAgentes() {
		this.agent(this.getNsMeta(),
				this.getTempoEsperaAceitavel(),
				this.getChamadas(),
				this.getTMA());
		
		return !this.hasError();
	}
	
	/** exibir as informações em saída básica (texto... System.out.println)
	 * exibe o número de recursos e o nível de serviço
	 */
	public void exibir(){
		System.out.println( "---------------------------" );
		System.out.println( "Segundos Intervalo = " + this.getSecondsInterval() );
		System.out.println( "Nível de Serviço = " +  this.getNsMeta() );
		System.out.println( "Tempo Aceitável = " + this.getTempoEsperaAceitavel() );
		System.out.println( "Volume = " + this.getChamadas() );
		System.out.println( "TMA = " + this.getTMA() );
		System.out.println( "HC Necessário = " + this.getAgentesNecessarios() );
		System.out.println( "HC Dimensionado = " + this.getAgentesDimensionados() );
		System.out.println( "NS Dimensionado = " + this.getNsDimensionado() );
		System.out.println( "Produtividade = " + this.getProdutividadeDimensionada() );
		System.out.println( "---------------------------" );
	};
}
