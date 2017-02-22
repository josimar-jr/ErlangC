package wfmpack;

public class Turno{
	
	private String idTurno = "";
	private String descricao = "";
	
	private String tempoAtendenteJornada = "";
	private String tempoAtendenteAlmoco = "";
	private String tempoAtendentePausa1 = "";
	private String tempoAtendentePausa2 = "";
	
	protected String[] granularidadeDimensionamento = { "00:05", "00:10", "00:15", "00:30" };
	private int granularidadeEscolhida = WFMPack.DIME_INT_10_MINUTOS;

	private int qtdeIntervalosJornada = 0;
	private int qtdeIntervalosAlmoco = 0;
	private int qtdeIntervalosPausa1 = 0;
	private int qtdeIntervalosPausa2 = 0;
	
	// dias para folga            { null, 	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }
	private boolean[] diasFolga = { false, 	true, 	false, 	false, 	 false, 	false, 	  false,  true };
	
	private boolean usaHorarioDiferenteFDS = true;
	private boolean usaHorarioDiferenteSemana = false;
	/**
	 * @return the idTurno
	 */
	public String getIdTurno() {
		return idTurno;
	}
	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the tempoAtendenteJornada
	 */
	public String getTempoAtendenteJornada() {
		return tempoAtendenteJornada;
	}
	/**
	 * @param tempoAtendenteJornada the tempoAtendenteJornada to set
	 */
	public void setTempoAtendenteJornada(String tempoAtendenteJornada) {
		this.tempoAtendenteJornada = tempoAtendenteJornada;
	}
	/**
	 * @return the tempoAtendenteAlmoco
	 */
	public String getTempoAtendenteAlmoco() {
		return tempoAtendenteAlmoco;
	}
	/**
	 * @param tempoAtendenteAlmoco the tempoAtendenteAlmoco to set
	 */
	public void setTempoAtendenteAlmoco(String tempoAtendenteAlmoco) {
		this.tempoAtendenteAlmoco = tempoAtendenteAlmoco;
	}
	/**
	 * @return the tempoAtendentePausa1
	 */
	public String getTempoAtendentePausa1() {
		return tempoAtendentePausa1;
	}
	/**
	 * @param tempoAtendentePausa1 the tempoAtendentePausa1 to set
	 */
	public void setTempoAtendentePausa1(String tempoAtendentePausa1) {
		this.tempoAtendentePausa1 = tempoAtendentePausa1;
	}
	/**
	 * @return the tempoAtendentePausa2
	 */
	public String getTempoAtendentePausa2() {
		return tempoAtendentePausa2;
	}
	/**
	 * @param tempoAtendentePausa2 the tempoAtendentePausa2 to set
	 */
	public void setTempoAtendentePausa2(String tempoAtendentePausa2) {
		this.tempoAtendentePausa2 = tempoAtendentePausa2;
	}
	/**
	 * @return the qtdeIntervalosJornada
	 */
	public int getQtdeIntervalosJornada() {
		return qtdeIntervalosJornada;
	}
	/**
	 * @param qtdeIntervalosJornada the qtdeIntervalosJornada to set
	 */
	public void setQtdeIntervalosJornada(int qtdeIntervalosJornada) {
		this.qtdeIntervalosJornada = qtdeIntervalosJornada;
	}
	/**
	 * @return the qtdeIntervalosAlmoco
	 */
	public int getQtdeIntervalosAlmoco() {
		return qtdeIntervalosAlmoco;
	}
	/**
	 * @param qtdeIntervalosAlmoco the qtdeIntervalosAlmoco to set
	 */
	public void setQtdeIntervalosAlmoco(int qtdeIntervalosAlmoco) {
		this.qtdeIntervalosAlmoco = qtdeIntervalosAlmoco;
	}
	/**
	 * @return the qtdeIntervalosPausa1
	 */
	public int getQtdeIntervalosPausa1() {
		return qtdeIntervalosPausa1;
	}
	/**
	 * @param qtdeIntervalosPausa1 the qtdeIntervalosPausa1 to set
	 */
	public void setQtdeIntervalosPausa1(int qtdeIntervalosPausa1) {
		this.qtdeIntervalosPausa1 = qtdeIntervalosPausa1;
	}
	/**
	 * @return the qtdeIntervalosPausa2
	 */
	public int getQtdeIntervalosPausa2() {
		return qtdeIntervalosPausa2;
	}
	/**
	 * @param qtdeIntervalosPausa2 the qtdeIntervalosPausa2 to set
	 */
	public void setQtdeIntervalosPausa2(int qtdeIntervalosPausa2) {
		this.qtdeIntervalosPausa2 = qtdeIntervalosPausa2;
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
	 * @return the diasFolga
	 */
	public boolean[] getDiasFolga() {
		return diasFolga;
	}
	/**
	 * @param diasFolga the diasFolga to set
	 */
	public void setDiasFolga(boolean[] diasFolga) {
		this.diasFolga = diasFolga;
	}
	/**
	 * @return the usaHorarioDiferenteFDS
	 */
	public boolean isUsaHorarioDiferenteFDS() {
		return usaHorarioDiferenteFDS;
	}
	/**
	 * @param usaHorarioDiferenteFDS the usaHorarioDiferenteFDS to set
	 */
	public void setUsaHorarioDiferenteFDS(boolean usaHorarioDiferenteFDS) {
		this.usaHorarioDiferenteFDS = usaHorarioDiferenteFDS;
	}
	/**
	 * @return the usaHorarioDiferenteSemana
	 */
	public boolean isUsaHorarioDiferenteSemana() {
		return usaHorarioDiferenteSemana;
	}
	/**
	 * @param usaHorarioDiferenteSemana the usaHorarioDiferenteSemana to set
	 */
	public void setUsaHorarioDiferenteSemana(boolean usaHorarioDiferenteSemana) {
		this.usaHorarioDiferenteSemana = usaHorarioDiferenteSemana;
	}
	
	
}