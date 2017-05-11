package wfmpack;

public class Schedule{
	
	public static int MAX_COLUNAS = 50; 
	public static int COLUNA_SOMA_AGENTES = 0;
	
	public static int TOTAL_QTDE = 0;
	public static int TOTAL_LINHA = 1;
	
	protected int[][] schedule;
	private int[][] totalAgents = new int[2][MAX_COLUNAS];

	private int qtdeIntervalosAgentes = 0;
	private int totalIntervalos = 0;
	
	private boolean activate = false;
	private boolean l24horas = false;

	/** Construtor sem parâmetros
	 */
	public Schedule(){
	}
	
	/** somarAgentesEmLinha
	 * Calcula o número de agente adicionados para a linha
	 * @param row	int, número da linha a ser avaliada
	 * @return	valor	int, soma dos agentes para a linha/horario
	 */
	protected int somarAgentesEmLinha( int row ){
		int valor = 0;
		
		for ( int n = 1; n < MAX_COLUNAS; n++ ) {
			valor += this.schedule[row][n];
		}
		
		return valor;
	}
	
	/** getColuna 
	 * método para identificar em qual coluna a quantidade de agentes deverá ser inserida
	 * @param nLinha	int, número da linha que a qtde será atribuída
	 * @return x	int, retorna o número da coluna para inserir a informação
	 */
	protected int getColuna( int nLinha ){
		int x = 1;
		
		for (int z = 1; (z < MAX_COLUNAS); z++ ) {
			if ( z == nLinha ) {
				x = z;
				break;
			}
		}
		return x;
	}
	
	/** getQtdeIntervalosAgentes
	 * @return the QtdeIntervalosAgentes
	 */
	public int getQtdeIntervalosAgentes() {
		return qtdeIntervalosAgentes;
	}
	/** setQtdeIntervalosAgentes
	 * @param QtdeIntervalosAgentes the QtdeIntervalosAgentes to set
	 */
	public void setQtdeIntervalosAgentes(int quantityIntervals) {
		if (!this.isActivate()) {
			this.qtdeIntervalosAgentes = quantityIntervals;
		}
	}
	/** getTotalIntervalos
	 * @return the totalIntervalos
	 */
	public int getTotalIntervalos() {
		return totalIntervalos;
	}
	/** setTotalIntervalos
	 * @param totalIntervalos the totalIntervalos to set
	 */
	public void setTotalIntervalos(int totalIntervals) {
		if (!this.isActivate()){
			this.totalIntervalos = totalIntervals;
		}
	}
	/** IsActivate - indica se o objeto está ativo para realizar a inclusão dos agentes
	 * @return activate 	boolean, indica se o objeto está ativo (true) ou não (false)
	 */
	public boolean isActivate(){
		return this.activate;
	}
	
	/** is24Horas - indica se a distribuição irá acontecer para períodos de 24horas
	 * @return l24horas 	boolean, indica se a distribuição será para 24horas (true) ou não (false)
	 */
	public boolean is24Horas(){
		return this.l24horas;
	}
	
	/** start - ativa o objeto para atribuição dos agentes
	 * @return	activate 	boolean, indica se ativou o objeto (true) ou não (false)
	 */
	public boolean start(){
		if (this.getQtdeIntervalosAgentes() > 0 &&
				this.getTotalIntervalos() > 0 ) {
			
			this.activate = true;
			this.schedule = new int[getTotalIntervalos()][MAX_COLUNAS];
		}
		
		return this.activate;
	}
	
	/** setScheduleAgentes
	 * Realiza a atribuição da quantidade x de agentes na linha 
	 * @param  	nLinha 	int, número da linha a 
	 * @param 	nQtdAgentes	int, quantidade de agentes a ser inserido
	 * @return 	inseriu	boolean, indica se a inclusão e atualização das informações aconteceu com sucesso
	 */
	public boolean setAgentesHorario( int nLinha, int nQtdeAgentes ){
		boolean inseriu = false;
		int linhaAte = nLinha + this.getQtdeIntervalosAgentes();
		int colunaInserir = 0;
		int diffAgentes = 0;
		
		if (isActivate() && 
				linhaAte <= this.getTotalIntervalos() && 
				linhaAte <= this.schedule.length ) {
			
			// identifica se a linha já sofreu uma atribuição anteriormente
			colunaInserir = this.getColuna( nLinha );
			
			for ( int k = nLinha; k < linhaAte; k++ ) {

				// identifica a diferença entre o valor anterior e novo
				diffAgentes = ( nQtdeAgentes - this.schedule[k][colunaInserir] );
				
				// atribui a quantidade na lista do schedule
				this.schedule[k][colunaInserir] = nQtdeAgentes;
				
				// atribui a soma dos agentes no horario
				this.schedule[k][COLUNA_SOMA_AGENTES] += diffAgentes;
				
			}
			
			// atribui a qtde ao resumo das informações
			this.totalAgents[TOTAL_QTDE][colunaInserir] = nQtdeAgentes;
			this.totalAgents[TOTAL_LINHA][colunaInserir] = nLinha;
			
			inseriu = true;
		}
		
		return inseriu;
	}
	
	/** getTotalAgentesLinha
	 *  Retorna quantidade total de agentes para um horário ou linha da tabela de distribuição
	 *  @param	linha	int, número da linha que deseja a informação
	 *  @return	total	int, qtde de agentes da linha/horário
	 */
	public int getTotalAgentesLinha( int linha ){
		int total = 0;
		
		if (linha < MAX_COLUNAS && linha >= 0 ) {
			total = this.schedule[linha][COLUNA_SOMA_AGENTES];
		}
		
		return total;
	}
}