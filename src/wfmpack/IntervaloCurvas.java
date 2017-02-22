package wfmpack;

import java.util.GregorianCalendar;

public class IntervaloCurvas {

	GregorianCalendar hora;
	double volume = 0;
	double tma = 0;
	double percentTMA = 0.0;
	/**
	 * @return the percentTMA
	 */
	public double getPercentTMA() {
		return percentTMA;
	}

	/**
	 * @param percentTMA the percentTMA to set
	 */
	public void setPercentTMA(double percentTMA) {
		this.percentTMA = percentTMA;
	}

	/**
	 * @return the percentVolume
	 */
	public double getPercentVolume() {
		return percentVolume;
	}

	/**
	 * @param percentVolume the percentVolume to set
	 */
	public void setPercentVolume(double percentVolume) {
		this.percentVolume = percentVolume;
	}
	double percentVolume = 0.0;
	
	/**
	 * Construtor para inicialização posterior dos valores
	 */
	public IntervaloCurvas(){
		
	}
	
	/** Construtor para inicialização com o devido conteúdo
	 * 
	 * @param horario	{@link GregorianCalendar}, horário a ser utilizado no intervalo
	 * @param volume	double, volume definido para o intervalo
	 * @param tma	double, tempo médio de atendimento para o intervalo
	 */
	public IntervaloCurvas( GregorianCalendar horario, double volume, double tma ){
		
	}
	
	/** Construtor para inicialização com o devido conteúdo de volume e tma
	 * 
	 * @param volume	double, volume definido para o intervalo
	 * @param tma	double, tempo médio de atendimento para o intervalo
	 */
	public IntervaloCurvas( double volume, double tma ){
		
	}
	
	/**
	 * @return the hora
	 */
	public GregorianCalendar getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}
	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		if (volume > 0)
			this.volume = volume;
		else
			this.volume = 0;
	}
	/**
	 * @return the tma
	 */
	public double getTma() {
		return tma;
	}
	/**
	 * @param tma the tma to set
	 */
	public void setTma(double tma) {
		if (tma > 0)
			this.tma = tma;
		else
			this.tma = 0;
	}
	
}