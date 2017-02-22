package wfmpack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WFMPack{

	public static int DIME_INT_05_MINUTOS = 0;
	public static int DIME_INT_10_MINUTOS = 1;
	public static int DIME_INT_15_MINUTOS = 2;
	public static int DIME_INT_30_MINUTOS = 3;
	
	public static int DIME_NS_PERIODO = 0;
	public static int DIME_NS_DIA = 1;
	public static int DIME_NS_INTERVALO = 2;
	
	public static int DOMINGO 	= Calendar.SUNDAY;
	public static int SEGUNDA 	= Calendar.MONDAY;
	public static int TERCA 	= Calendar.TUESDAY;
	public static int QUARTA	= Calendar.WEDNESDAY;
	public static int QUINTA 	= Calendar.THURSDAY;
	public static int SEXTA 	= Calendar.FRIDAY;
	public static int SABADO 	= Calendar.SATURDAY;

	// lista dos atributos privados ao dimensionamento
	private String tituloDimensionamento = "";
	private String descricaoDimensionamento = "";
	
	// informa��es do per�odo do dimensionamento
	// dias e as curvas
	private GregorianCalendar diaInicial;
	private GregorianCalendar diaFinal;
	private ArrayList <DiaDimensionamento> listOfDiasDimes;
	private String pathFileCurvas;
	private FileCurvas arquivoCurvas;
	private ArrayList <GregorianCalendar> feriadosPeriodo;
	
	// regras do dimensionamento
	private boolean dime24h = false;
	private String horaInicial;
	private String horaFinal;
	protected String[] granularidadeDimensionamento = { "00:05", "00:10", "00:15", "00:30" };
	private int granularidadeEscolhida = DIME_INT_10_MINUTOS;
	
	private boolean dimensionaAlmoco = true;
	private boolean dimensionaPausa1 = true;
	private boolean dimensionaPausa2 = true;
	private boolean dimensionaFolga = true;
	
	//-------------------------------------------------
	// Vari�vel para carregar os turnos padr�es
	// 6h20 (6 x 1) e 7h10 (5 x 2)
	private ArrayList<Turno> turnosPadroes;
	
	private double NsMeta;
	private int tempoAceitavel;
	protected String[] conceitoNS = { "Per�odo", "Dia", "Intervalo" };
	private int conceitoNsEscolhido = DIME_NS_DIA;
	
	private double volumeChamadasPeriodo;
	private double tmaResumoPeriodo;
	private int recursosPeriodo;
	private int posicoesNecessarias; // quantidade de posicoes de atendimento (PAs) necess�rias
	
	// indica o in�cio do processamento
	private boolean activate = false;
	private String erros = "";
	
	/**
	 * @return the tituloDimensionamento
	 */
	public String getTituloDimensionamento() {
		return tituloDimensionamento;
	}

	/**
	 * @param tituloDimensionamento the tituloDimensionamento to set
	 */
	public void setTituloDimensionamento(String tituloDimensionamento) {
		this.tituloDimensionamento = tituloDimensionamento;
	}

	/**
	 * @return the descricaoDimensionamento
	 */
	public String getDescricaoDimensionamento() {
		return descricaoDimensionamento;
	}

	/**
	 * @param descricaoDimensionamento the descricaoDimensionamento to set
	 */
	public void setDescricaoDimensionamento(String descricaoDimensionamento) {
		this.descricaoDimensionamento = descricaoDimensionamento;
	}

	/**
	 * @return the diaInicial
	 */
	public GregorianCalendar getDiaInicial() {
		return diaInicial;
	}

	/**
	 * @param diaInicial the diaInicial to set
	 */
	public void setDiaInicial(GregorianCalendar diaInicial) {
		this.diaInicial = diaInicial;
	}

	/**
	 * @return the diaFinal
	 */
	public GregorianCalendar getDiaFinal() {
		return diaFinal;
	}

	/**
	 * @param diaFinal the diaFinal to set
	 */
	public void setDiaFinal(GregorianCalendar diaFinal) {
		this.diaFinal = diaFinal;
	}

	/**
	 * @return the feriadosPeriodo
	 */
	public ArrayList<GregorianCalendar> getFeriadosPeriodo() {
		return feriadosPeriodo;
	}

	/**
	 * @param feriadosPeriodo the feriadosPeriodo to set
	 */
	public void setFeriadosPeriodo(ArrayList<GregorianCalendar> feriadosPeriodo) {
		this.feriadosPeriodo = feriadosPeriodo;
	}

	/**
	 * @return the pathFileCurvas
	 */
	public String getPathFileCurvas() {
		return pathFileCurvas;
	}

	/**
	 * @param pathFileCurvas the pathFileCurvas to set
	 */
	public void setPathFileCurvas(String pathFileCurvas) {
		this.pathFileCurvas = pathFileCurvas;
	}

	/**
	 * @return the arquivoCurvas
	 */
	public FileCurvas getArquivoCurvas() {
		return arquivoCurvas;
	}

	/**
	 * @param arquivoCurvas the arquivoCurvas to set
	 */
	public void setArquivoCurvas(FileCurvas arquivoCurvas) {
		this.arquivoCurvas = arquivoCurvas;
	}

	/**
	 * @return the dime24h
	 */
	public boolean isDime24h() {
		return dime24h;
	}

	/**
	 * @param dime24h the dime24h to set
	 */
	public void setDime24h(boolean dime24h) {
		this.dime24h = dime24h;
	}

	/**
	 * @return the horaInicial
	 */
	public String getHoraInicial() {
		return horaInicial;
	}

	/**
	 * @param horaInicial the horaInicial to set
	 */
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	/**
	 * @return the horaFinal
	 */
	public String getHoraFinal() {
		return horaFinal;
	}

	/**
	 * @param horaFinal the horaFinal to set
	 */
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	/**
	 * @return the granularidadeDimensionamento
	 */
	public String[] getGranularidadeDimensionamento() {
		return granularidadeDimensionamento;
	}

	/**
	 * @param granularidadeDimensionamento the granularidadeDimensionamento to set
	 */
	public void setGranularidadeDimensionamento(
			String[] granularidadeDimensionamento) {
		this.granularidadeDimensionamento = granularidadeDimensionamento;
	}

	/**
	 * @return the granularidadeEscolhida
	 */
	public int getGranularidadeEscolhida() {
		return granularidadeEscolhida;
	}

	/**
	 * @param granularidadeEscolhida the granularidadeEscolhida to set
	 */
	public void setGranularidadeEscolhida(int granularidadeEscolhida) {
		this.granularidadeEscolhida = granularidadeEscolhida;
	}

	/**
	 * @return the dimensionaAlmoco
	 */
	public boolean isDimensionaAlmoco() {
		return dimensionaAlmoco;
	}

	/**
	 * @param dimensionaAlmoco the dimensionaAlmoco to set
	 */
	public void setDimensionaAlmoco(boolean dimensionaAlmoco) {
		this.dimensionaAlmoco = dimensionaAlmoco;
	}

	/**
	 * @return the dimensionaPausa1
	 */
	public boolean isDimensionaPausa1() {
		return dimensionaPausa1;
	}

	/**
	 * @param dimensionaPausa1 the dimensionaPausa1 to set
	 */
	public void setDimensionaPausa1(boolean dimensionaPausa1) {
		this.dimensionaPausa1 = dimensionaPausa1;
	}

	/**
	 * @return the dimensionaPausa2
	 */
	public boolean isDimensionaPausa2() {
		return dimensionaPausa2;
	}

	/**
	 * @param dimensionaPausa2 the dimensionaPausa2 to set
	 */
	public void setDimensionaPausa2(boolean dimensionaPausa2) {
		this.dimensionaPausa2 = dimensionaPausa2;
	}

	/**
	 * @return the dimensionaFolga
	 */
	public boolean isDimensionaFolga() {
		return dimensionaFolga;
	}

	/**
	 * @param dimensionaFolga the dimensionaFolga to set
	 */
	public void setDimensionaFolga(boolean dimensionaFolga) {
		this.dimensionaFolga = dimensionaFolga;
	}

	/**
	 * @return the nsMeta
	 */
	public double getNsMeta() {
		return NsMeta;
	}

	/**
	 * @param nsMeta the nsMeta to set
	 */
	public void setNsMeta(double nsMeta) {
		NsMeta = nsMeta;
	}

	/**
	 * @return the tempoAceitavel
	 */
	public int getTempoAceitavel() {
		return tempoAceitavel;
	}

	/**
	 * @param tempoAceitavel the tempoAceitavel to set
	 */
	public void setTempoAceitavel(int tempoAceitavel) {
		this.tempoAceitavel = tempoAceitavel;
	}

	/**
	 * @return the conceitoNS
	 */
	public String[] getConceitoNS() {
		return conceitoNS;
	}

	/**
	 * @param conceitoNS the conceitoNS to set
	 */
	public void setConceitoNS(String[] conceitoNS) {
		this.conceitoNS = conceitoNS;
	}

	/**
	 * @return the conceitoNsEscolhido
	 */
	public int getConceitoNsEscolhido() {
		return conceitoNsEscolhido;
	}

	/**
	 * @param conceitoNsEscolhido the conceitoNsEscolhido to set
	 */
	public void setConceitoNsEscolhido(int conceitoNsEscolhido) {
		this.conceitoNsEscolhido = conceitoNsEscolhido;
	}

	/**
	 * @return the volumeChamadasPeriodo
	 */
	public double getVolumeChamadasPeriodo() {
		return volumeChamadasPeriodo;
	}

	/**
	 * @param volumeChamadasPeriodo the volumeChamadasPeriodo to set
	 */
	public void setVolumeChamadasPeriodo(double volumeChamadasPeriodo) {
		this.volumeChamadasPeriodo = volumeChamadasPeriodo;
	}

	/**
	 * @return the tmaResumoPeriodo
	 */
	public double getTmaResumoPeriodo() {
		return tmaResumoPeriodo;
	}

	/**
	 * @param tmaResumoPeriodo the tmaResumoPeriodo to set
	 */
	public void setTmaResumoPeriodo(double tmaResumoPeriodo) {
		this.tmaResumoPeriodo = tmaResumoPeriodo;
	}

	/**
	 * @return the recursosPeriodo
	 */
	public int getRecursosPeriodo() {
		return recursosPeriodo;
	}

	/**
	 * @param recursosPeriodo the recursosPeriodo to set
	 */
	public void setRecursosPeriodo(int recursosPeriodo) {
		this.recursosPeriodo = recursosPeriodo;
	}

	/**
	 * @return the posicoesNecessarias
	 */
	public int getPosicoesNecessarias() {
		return posicoesNecessarias;
	}

	/**
	 * @param posicoesNecessarias the posicoesNecessarias to set
	 */
	public void setPosicoesNecessarias(int posicoesNecessarias) {
		this.posicoesNecessarias = posicoesNecessarias;
	}

	/**
	 * @return the listOfDiasDimes
	 */
	public ArrayList<DiaDimensionamento> getListOfDiasDimes() {
		return listOfDiasDimes;
	}

	/**
	 * @param listOfDiasDimes the listOfDiasDimes to set
	 */
	public void setListOfDiasDimes(ArrayList<DiaDimensionamento> listOfDiasDimes) {
		this.listOfDiasDimes = listOfDiasDimes;
	}

	/**
	 * @return the activate
	 */
	public boolean isActivate() {
		return activate;
	}

	/**
	 * @param activate the activate to set
	 */
	public void setActivate(boolean activate) {
		this.activate = activate;
	}
	
	public String getErros(){
		return this.erros;
	}

	// m�todos privados
	/** setErro
	 *   
	 * @param inicializa boolean - indica se deve inicializar o objeto com o conte�do
	 * @param erro String - indica qual conte�do deve ser adicionado a string de erro
	 */
	private void setErro( boolean inicializa, String erro ){
		if (inicializa)
			this.erros = erro + ( erro.isEmpty() ? "\n" : "");
		else
			this.erros += erro + "\n"  ;
	}
	private boolean canAtivar(){
		boolean lOk = true;
		
		// inicializa o objeto para captura de erros
		setErro( true, "" );
		
		if (this.getTituloDimensionamento().isEmpty())
			setErro( false, "T�tulo n�o preenchido");
		
		if (this.getDescricaoDimensionamento().isEmpty())
			setErro( false, "Descri��o dimensionamento n�o preenchida");
		
		if (this.getDiaInicial() == null || this.getDiaFinal() == null)
			setErro( false, "Data inicial ou final n�o definidas");
		
		if (this.getPathFileCurvas().isEmpty())
			setErro(false, "Caminho do arquivo com as curvas n�o definido");
		
		if (this.getNsMeta() <= 0 || this.getNsMeta() >= 1 )
			setErro(false, "Valor de n�vel de servi�o n�o definido ou inv�lido. [0 < NS < 1]");
		
		if (this.getTempoAceitavel() <= 0)
			setErro(false, "Valor n�o definido ou inv�lido. [TE > 0]");
		
		if (this.getVolumeChamadasPeriodo() <= 0)
			setErro(false, "Quantidade de chamadas n�o definida ou inv�lida. [Volume > 0]");
			
		return lOk;
	}
	/** carregaTurnosPadroes
	 * 	realiza a carga dos turnos padr�es a serem utilizados no dimensionamento
	 */
	private void carregarTurnosPadroes(){
		int ultimoAdicionado = 0;
		Turno tempTurno;
		
		this.turnosPadroes.add( new Turno() ); // turno 6h20
		ultimoAdicionado = (turnosPadroes.size() - 1);
		tempTurno = turnosPadroes.get(ultimoAdicionado);
		//------------------------------------------------------
		// informa��es do turno para 6h20
		tempTurno.setIdTurno("001");
		tempTurno.setDescricao("6h20 - 1 folga fds");
		tempTurno.setTempoAtendenteJornada("6h20");
		tempTurno.setTempoAtendenteAlmoco("00:20");
		tempTurno.setTempoAtendentePausa1("00:10");
		tempTurno.setTempoAtendentePausa2("00:10");
		tempTurno.setGranularidadeEscolhida(WFMPack.DIME_INT_30_MINUTOS); // pensar em como carregar corretamente baseado em sele��o pr�via do usu�rio
		tempTurno.setQtdeIntervalosJornada(12); // n�mero de intervalos que faz atingir 6h 20min
		tempTurno.setQtdeIntervalosAlmoco(0);
		tempTurno.setQtdeIntervalosPausa1(0);
		tempTurno.setQtdeIntervalosPausa2(0);
		// dias de folga : deixa o padr�o de folga aos s�bados e domingos
		tempTurno.setUsaHorarioDiferenteFDS(false);  // define se deve ser o mesmo hor�rio utilizado durante a semana
		tempTurno.setUsaHorarioDiferenteSemana(false); // define se deve ser o mesmo hor�rio todos os dias
		
		//------------------------------------------------------

		this.turnosPadroes.add( new Turno() ); // turno 7h10
		ultimoAdicionado = (turnosPadroes.size() - 1);
		tempTurno = turnosPadroes.get(ultimoAdicionado);
		//------------------------------------------------------
		// informa��es do turno para 7h10
		tempTurno.setIdTurno("002");
		tempTurno.setDescricao("7h10 - 5 x 2 : folga fds");
		tempTurno.setTempoAtendenteJornada("7h10");
		tempTurno.setTempoAtendenteAlmoco("00:40");
		tempTurno.setTempoAtendentePausa1("00:10");
		tempTurno.setTempoAtendentePausa2("00:10");
		tempTurno.setGranularidadeEscolhida(WFMPack.DIME_INT_30_MINUTOS); // pensar em como carregar corretamente baseado em sele��o pr�via do usu�rio
		tempTurno.setQtdeIntervalosJornada(14); // n�mero de intervalos que faz atingir 6h 20min
		tempTurno.setQtdeIntervalosAlmoco(0);
		tempTurno.setQtdeIntervalosPausa1(0);
		tempTurno.setQtdeIntervalosPausa2(0);
		// dias de folga : deixa o padr�o de folga aos s�bados e domingos
		tempTurno.setUsaHorarioDiferenteFDS(false);  // define se deve ser o mesmo hor�rio utilizado durante a semana
		tempTurno.setUsaHorarioDiferenteSemana(false); // define se deve ser o mesmo hor�rio todos os dias
		
		//------------------------------------------------------
	}
	
	/** inicializar
	 * Ativa o objeto da classe para realizar o processamento e distribui��o dos agentes
	 * @return boolean - indica se a ativa��o aconteceu com sucesso
	 */
	public boolean inicializar(){
		boolean lOk = this.canAtivar();
		
		if (lOk){
			this.carregarTurnosPadroes(); // carrega os turnos padr�es
			this.setActivate(true); // indica que a classe est� ativada
		}
		
		return lOk;
	}
}