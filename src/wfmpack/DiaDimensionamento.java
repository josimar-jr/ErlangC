package wfmpack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class DiaDimensionamento extends Schedule {
	
	private tipoCurva tipoCurvaDia;
	private ArrayList<Intervalo> intervalos = new ArrayList<Intervalo>();
	private GregorianCalendar dia;
	private Curvas curvaDistribuicao;
	private double NsMeta = 0;
	private int tempoAceitavelNs = 0;
	private double blocking = 0;
	private int segundosIntervalo = 0;
	private double TMA = 0.0;
	private double chamadas = 0.0;

	private int agentes = 0;
	private double NsDimensionado = 0.0;
	private double produtividade = 0.0;
	private int posicoesAtendimento = 0;
	
	/** Construtor sem parâmetros
	 */
	public DiaDimensionamento(){
	}
	
	//-----------------------------------
	// métodos restritos
	/** setAgentes
	 * @param agentes the agentes to set
	 */
	protected void setAgentes(int agentes) {
		this.agentes = agentes;
	}
	/** setNsDimensionado
	 * @param nsDimensionado the nsDimensionado to set
	 */
	protected void setNsDimensionado(double nsDimensionado) {
		NsDimensionado = nsDimensionado;
	}
	/** setProdutividade
	 * @param produtividade the produtividade to set
	 */
	protected void setProdutividade(double produtividade) {
		this.produtividade = produtividade;
	}
	/** setPosicoesAtendimento
	 * @param posicoes the posicoes to set
	 */
	protected void setPosicoesAtendimento(int posicoes) {
		this.posicoesAtendimento = posicoes;
	}
	//-----------------------------------------
	// métodos públicos
	/** getProdutividade
	 * @return the produtividade
	 */
	public double getProdutividade() {
		return produtividade;
	}
	/** getNsDimensionado
	 * @return the nsDimensionado
	 */
	public double getNsDimensionado() {
		return NsDimensionado;
	}
	/** getAgentes
	 * @return the agentes
	 */
	public int getAgentes() {
		return agentes;
	}
	/** getPosicoesAtendimento
	 * @return the posicoesAtendimento
	 */
	public int getPosicoesAtendimento() {
		return posicoesAtendimento;
	}
	/** getChamadas
	 * @return the chamadas
	 */
	public double getChamadas() {
		return chamadas;
	}
	/** setChamadas
	 * @param chamadas the chamadas to set
	 */
	public void setChamadas(double chamadas) {
		this.chamadas = chamadas;
	}
	/** getTMA
	 * @return the tMA
	 */
	public double getTMA() {
		return TMA;
	}
	/** setTMA
	 * @param tMA the tMA to set
	 */
	public void setTMA(double tMA) {
		TMA = tMA;
	}
	/** getTipoCurvaDia
	 * @return the tipoCurvaDia
	 */
	public tipoCurva getTipoCurvaDia() {
		return tipoCurvaDia;
	}
	/** setTipoCurvaDia
	 * @param tipoCurvaDia the tipoCurvaDia to set
	 */
	public void setTipoCurvaDia(tipoCurva tipoCurvaDia) {
		this.tipoCurvaDia = tipoCurvaDia;
	}	
	/** getIntervalos
	 * @return the intervalos
	 */
	public ArrayList<Intervalo> getIntervalos() {
		return intervalos;
	}
	/** getNsMeta
	 * @return the nsMeta
	 */
	public double getNsMeta() {
		return NsMeta;
	}
	/** setNsMeta
	 * @param NsMeta the NsMeta to set
	 */
	public void setNsMeta(double NsMeta) {
		if(NsMeta < 1 && NsMeta > 0) {
			this.NsMeta = NsMeta;
		}
	}
	
	/** getSegundosIntervalo
	 * @return the segundosIntervalo
	 */
	public int getSegundosIntervalo() {
		return segundosIntervalo;
	}
	/** setSegundosIntervalo
	 * @param segundosIntervalo the segundosIntervalo to set
	 */
	public void setSegundosIntervalo(int segundosIntervalo) {
		this.segundosIntervalo = segundosIntervalo;
	}
	
	/** setBlocking
	 * @param blocking - double, o percentual a ser usado cálculo de linhas
	 */
	public void setBlocking( double blocking ){
		this.blocking = blocking;
	}
	
	/** getBlocking
	 * @return blocking double - o percentual definido de bloqueio no cálculo de linhas
	 */
	public double getBlocking(){
		return this.blocking;
	}
	
	/** getCurvaDistribuicao
	 * @return the curvaDistribuicao
	 */
	public Curvas getCurvaDistribuicao() {
		return curvaDistribuicao;
	}
	/** setCurvaDistribuicao
	 * @param curvaDistribuicao the curvaDistribuicao to set
	 */
	public void setCurvaDistribuicao(Curvas curvaDistribuicao) {
		this.curvaDistribuicao = curvaDistribuicao;
	}
	/** getTempoAceitavelNs
	 * @return the tempoAceitavelNs
	 */
	public int getTempoAceitavelNs() {
		return tempoAceitavelNs;
	}
	/** setTempoAceitavelNs
	 * @param tempoAceitavelNs the tempoAceitavelNs to set
	 */
	public void setTempoAceitavelNs(int tempoAceitavelNs) {
		this.tempoAceitavelNs = tempoAceitavelNs;
	}
	/**
	 * @return the dia
	 */
	public GregorianCalendar getDia() {
		return dia;
	}
	/** setDia
	 * @param dia the dia to set
	 */
	public void setDia(GregorianCalendar dia) {
		this.dia = dia;
	}
	
	/** carregarTotais - carrega as informações dos totais para o dia das informações dimensionadas
	 * Total de Agentes, Nível de Serviço e Produtividade do dia
	 * @return true/false - indica se a carga das informações aconteceu com sucesso
	 */
	protected boolean carregarTotais(){
		boolean lOk = true;
		Iterator<Intervalo> infosIntervalo = this.getIntervalos().iterator();
		Intervalo tempIntervalo;
		
		double tempNS = 0.0;
		double tempProdutividade = 0.0;
		int tempAgentes = 0;
		int maxAgentes = 0;
		
		while (infosIntervalo.hasNext()){
			tempIntervalo = infosIntervalo.next();
			
			tempNS += ( tempIntervalo.getNsDimensionado() * tempIntervalo.getChamadas() );
			tempProdutividade += ( tempIntervalo.getProdutividadeDimensionada() * tempIntervalo.getAgentesDimensionados() );
			tempAgentes += tempIntervalo.getAgentesDimensionados();
			
			maxAgentes = (tempIntervalo.getAgentesDimensionados() > maxAgentes ? tempIntervalo.getAgentesDimensionados() : maxAgentes);
		}
		
		this.setNsDimensionado( tempNS / this.getChamadas() );
		this.setProdutividade( tempProdutividade / tempAgentes );
		this.setAgentes( tempAgentes / this.getQtdeIntervalosAgentes());
		this.setPosicoesAtendimento(maxAgentes);
		
		return lOk;
	}
	
	/** carregarIntervalos - carrega as informações dos intervalos e preenche para o dimensionamento
	 * @return true/false - indica se a carga das informações aconteceu com sucesso
	 */
	protected boolean carregarIntervalos(){
		boolean lOk = true;
		Iterator<IntervaloCurvas> itCurva;
		IntervaloCurvas tempItCurvas;
		int ultimoAdicionado = 0;
		Intervalo tempIntervalo;
		GregorianCalendar tempDiaHora;
		
		if( this.dia == null || this.tipoCurvaDia == null || this.tempoAceitavelNs <= 0 || 
				( this.NsMeta <= 0.0 && this.NsMeta >= 1 ) || this.chamadas <= 0.0 || this.TMA <= 0  ||
				this.curvaDistribuicao == null || this.getQtdeIntervalosAgentes() <= 0 || this.getSegundosIntervalo() <= 0 ) {
			
			lOk = false;
		}
		else {
			
			itCurva = this.getCurvaDistribuicao().getDadosIntervalo().iterator();
			
			while ( itCurva.hasNext() && lOk ) { 
				
				// captura os dados para a curva a ser adicionada
				tempItCurvas = itCurva.next();
				
				if ( tempItCurvas.getPercentVolume() > 0.0 || tempItCurvas.getTma() > 0.0 ) {

					// adiciona e posiciona no item de intervalo sendo adicionado
					this.getIntervalos().add( new Intervalo() );
					ultimoAdicionado = ( this.getIntervalos().size() - 1 );
					tempIntervalo = this.getIntervalos().get(ultimoAdicionado);
					
					//	Atribui conteúdo aos atributos principais da classe
					// valores fixos para o dia: NS e Tempo Espera
					tempIntervalo.setNsMeta(this.getNsMeta());
					tempIntervalo.setTempoEsperaAceitavel(this.getTempoAceitavelNs());
					tempIntervalo.setIntervalInSeconds( this.getSegundosIntervalo() );
					tempIntervalo.setBlockingPercentage( this.getBlocking() );
	
					// valores variáveis para o dia
					tempDiaHora = new GregorianCalendar(dia.get(Calendar.YEAR), dia.get(Calendar.MONTH), dia.get(Calendar.DAY_OF_MONTH), 
							tempItCurvas.getHora().get(Calendar.HOUR_OF_DAY), 
							tempItCurvas.getHora().get(Calendar.MINUTE));
					
					tempIntervalo.setDataHora( tempDiaHora );  // data e hora do intervalo
					tempIntervalo.setChamadas( tempItCurvas.getPercentVolume() * this.getChamadas() );  // volume proporcional ao valor do dia
					tempIntervalo.setTMA( tempItCurvas.getPercentTMA() * this.getTMA() );  // tma proporcional ao valor do dia
					
					// calcula o "agents" para o intervalo, número de recursos necessários
					lOk = tempIntervalo.calcularAgentes();
				}
			}
			// compara o tamanho das listas para não ter problemas com
			// Curvas com tamanho diferente do indicado para a distribuição na classe
			lOk = lOk && ( this.getIntervalos().size() == this.schedule.length );
		}
		
		return lOk;
	}

	/** setAgentesHorario
	 * Realiza a atribuição da quantidade x de agentes na linha/horario
	 * @param  	nLinha 	int, número da linha a 
	 * @param 	nQtdAgentes	int, quantidade de agentes a ser inserido
	 * @return 	inseriu	boolean, indica se a inclusão e atualização das informações aconteceu com sucesso
	 */
	public boolean setAgentesHorario( int nLinha, int nQtdAgentes ){
		int nLinhaAte = nLinha + this.getQtdeIntervalosAgentes();
		Intervalo tempInfo;
		
		boolean inseriu = ( nLinhaAte <= this.getIntervalos().size() );
		
		// chama o método da classe pai para executar a distribuição das informações no array
		inseriu = inseriu && super.setAgentesHorario(nLinha, nQtdAgentes);
		
		for( int nPos = nLinha; ( inseriu && nPos < nLinhaAte ); nPos++ ){
			// identifica o objeto do intervalo
			// e atualiza o novo valor total de agentes do intervalo
			tempInfo = this.getIntervalos().get( nPos );
			tempInfo.setAgentesDimensionados(this.getTotalAgentesLinha(nPos));
		}
		
		inseriu = inseriu && this.carregarTotais();
		
		return inseriu;
	}
	
	/** setAgentesHorario
	 * Realiza a atribuição da quantidade x de agentes na linha/horario
	 * @param  	datahora 	{@link GregorianCalendar}, data e hora que deve inserir a informação 
	 * @param 	nQtdAgentes	int, quantidade de agentes a ser inserido
	 * @return 	inseriu	boolean, indica se a inclusão e atualização das informações aconteceu com sucesso
	 */
	public boolean setAgentesHorario( GregorianCalendar datahora, int nQtdAgentes ){
		return this.setAgentesHorario( this.getLinhaPelaHora(datahora), nQtdAgentes);
	}
	
	/** exibir - exibe em saída básica (texto) as informações preenchidas no objeto
	 */
	public void exibir(){
		
		GregorianCalendar tpDia = this.getDia();
		String tempDia = tpDia.get(Calendar.DATE)+"/"+tpDia.get(Calendar.MONTH)+"/"+tpDia.get(Calendar.YEAR);
		
		System.out.println("__________ Dados do Dia __________");
		System.out.println("Segundos Intervalo = " + this.getSegundosIntervalo());
		System.out.printf("Nível de Serviço Meta = %.2f \n", this.getNsMeta()*100);
		System.out.println("Tempo de Espera Aceitável = " + this.getTempoAceitavelNs());
		System.out.println("Data = " + tempDia );
		System.out.println("___________ Intervalos ___________");
		System.out.println("Horário \tVolume \tTMA \tAgents \tHC Dime \t% NS Dime \t% Prod");
		
		for ( Intervalo x : this.getIntervalos() ){
		
			GregorianCalendar tempHora = x.getDataHora();
			String cHora = ( tempHora.get(Calendar.HOUR_OF_DAY) < 10 ? "0"+ tempHora.get(Calendar.HOUR_OF_DAY) : tempHora.get(Calendar.HOUR_OF_DAY) )
							+":"+
							( tempHora.get(Calendar.MINUTE) < 10 ? "0" + tempHora.get(Calendar.MINUTE) : tempHora.get(Calendar.MINUTE) );
			
			System.out.print( cHora + " \t\t"); // horario
			System.out.format( "%.2f \t", x.getChamadas() );  // volume
			System.out.printf( "%.2f \t", x.getTMA() );  // TMA
			System.out.printf( "%d \t", x.getAgentesNecessarios() );  // Agents
			System.out.printf( "%d \t\t", x.getAgentesDimensionados() );  // HC Dime
			System.out.printf( "%.2f \t\t", x.getNsDimensionado() * 100 );  // NS Dime
			System.out.printf( "%.2f \n", x.getProdutividadeDimensionada() * 100 );  // Produtividade
		}
		
		System.out.println("Volume = " + this.getChamadas());
		System.out.println("TMA = " + this.getTMA());
		System.out.println("Agentes = " + this.getAgentes());
		System.out.printf( "Nível de Serviço = %.3f \n", this.getNsDimensionado() * 100 );  // NS Dime
		System.out.printf( "Produtividade = %.3f \n", this.getProdutividade() * 100 );  // Produtividade
	}
	/** getLinhaPelaHora
	 *    Identifica pelo horário qual linha correspondente na lista de intervalos
	 * @param horario {@link GregorianCalendar} - horário
	 * @return linha int - número da linha que possui o horário, retorna -1 quando não encontra
	 */
	public int getLinhaPelaHora( GregorianCalendar horario ) {
		int linha = 0;
		
		for ( Intervalo x : this.getIntervalos() ){
			if (x.getDataHora().equals(horario) ) {
				break;
			}
			
			linha++;
		}
		// caso não identifique retorna -1
		if (linha > this.getIntervalos().size() )
			linha = -1;
		
		return linha;
	}
	/** getHoraPelaLinha
	 *  identifica qual o horário conforme a linha indicada na lista de intervalos
	 *  @param linha int - indica a linha alvo
	 *  @return horario {@link GregorianCalendar} - horário identificado, retorna null quando fora da lista
	 */
	public GregorianCalendar getHoraPelaLinha( int linha ) {
		GregorianCalendar horario = null;
		
		if (linha < this.getIntervalos().size() && linha >= 0 ){
			horario = this.getIntervalos().get( linha ).getDataHora();
		}
		return horario;
	}
	
	
	public boolean start(){
		boolean isOk = super.start();
		
		isOk = isOk && this.carregarIntervalos();
		
		return isOk;
	}
}