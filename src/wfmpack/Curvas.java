package wfmpack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Curvas {

	ArrayList <IntervaloCurvas> dadosIntervalo = new ArrayList<IntervaloCurvas>();
	// caracter�stica da curva
	// indicar� se � semana <SEM(0)>
	// algum dia espec�fico dom(7), seg(1), ter(2)... sab(6)
	tipoCurva tipo;

	double totalVolume = 0;
	double totalTMA = 0;
	
	/**
	 * @return the dadosIntervalo
	 */
	public ArrayList<IntervaloCurvas> getDadosIntervalo() {
		return dadosIntervalo;
	}

	/** adicionarInformacao - adiciona as informa��es de um intervalo para a lista de intervalos da curva
	 * @param horario 	{@link GregorianCalendar}, formato de hor�rio para inclus�o no intervalo da curva
	 * @param volume	double, quantidade de chamadas para o intervalo da curva sendo adicionada
	 * @param tma	double, valor de tma para o intervalo da curva sendo adicionada
	 * @return	lok boolean, determina se a adi��o aconteceu com sucesso (true) ou n�o (false)
	 */
	public boolean adicionarInformacao( GregorianCalendar horario, double volume, double tma ){
		boolean lok = true;
		int ultimoItem = 0;
		IntervaloCurvas intAdd;

		if ( volume > 0.0 && tma > 0.0 ) {
			dadosIntervalo.add(new IntervaloCurvas());
			ultimoItem = ( dadosIntervalo.size() - 1 );
			intAdd = dadosIntervalo.get(ultimoItem);
	
			intAdd.setHora(horario);
			intAdd.setVolume(volume);
			intAdd.setTma(tma);
		}
		else {
			lok = false;
		}
	
		return lok;
	}

	/** adicionarInformacao - adiciona as informa��es de um intervalo para a lista de intervalos da curva
	 * @param volume	double, quantidade de chamadas para o intervalo da curva sendo adicionada
	 * @param tma	double, valor de tma para o intervalo da curva sendo adicionada
	 * @return	lok boolean, determina se a adi��o aconteceu com sucesso (true) ou n�o (false)
	 */
	public boolean adicionarInformacao( double volume, double tma ){
		GregorianCalendar horario = new GregorianCalendar(1901, 1, 1, 0, 0); 
		boolean lOk = this.adicionarInformacao(horario, volume, tma);
		return lOk;
	}
	
	public void setTipoCurva( tipoCurva tipo ){
		this.tipo = tipo;
	}
	
	public tipoCurva getTipoCurva(){
		return this.tipo;
	}
	
	/** carregarTotais : navega na lista de informa��es do intervalo da curva e calcula
	 * o tma e o volume total da curva
	 */
	public void carregarTotais(){
		double tempTMA = 0;
		double tempVolume = 0;
		
		for (IntervaloCurvas dado : this.dadosIntervalo){
			// soma os volumes dos intervalos
			tempVolume += dado.getVolume();
			// soma os produtos do tma e volume dos intervalos
			tempTMA += ( dado.getVolume() * dado.getTma() );
		}
		// identifica a m�dia ponderada dos tmas dos intervalos
		tempTMA /= tempVolume;
		
		this.totalTMA = tempTMA;
		this.totalVolume = tempVolume;
		
		for (IntervaloCurvas dado : this.dadosIntervalo){
			// calcula o percentual do intervalo baseado no total 
			dado.setPercentVolume( dado.getVolume() / this.totalVolume );
			dado.setPercentTMA( dado.getTma() / this.totalTMA );
		}
	}
	
	/** getTotalVolume: retorna o volume de chamadas 
	 * @return totalVolume - total de chamadas dos intervalos
	 */
	public double getTotalVolume(){
		return this.totalVolume;
	}
	
	/** getTotalTMA: retorna a m�dia de tma  
	 * @return totalTMA - m�dia do tma dos intevalos
	 */
	public double getTotalTMA(){
		return this.totalTMA;
	}
	
	/** exibir - sa�da b�sica das informa��es lidas
	 */
	public void exibir(){
		
		// formato ___
		// tipo do dia: SEM/DOM/FER/
		// hora 	volume 	tma
		// 06:00	100 	150
		System.out.println("Tipo do dia: " + tipoCurva.getStringPeloTipo(getTipoCurva()));
		System.out.println("hora \t volume \t tma" );

		for ( IntervaloCurvas eachIntervalo : dadosIntervalo ) {
			
			GregorianCalendar intHora = eachIntervalo.getHora();
			String hora = intHora.get(Calendar.HOUR) + ":" + intHora.get(Calendar.MINUTE);
			
			double intVolume = eachIntervalo.getVolume();
			double intTma = eachIntervalo.getTma();
			
			System.out.println( hora + " \t " + intVolume + " \t " + intTma );
		}
	}
	
	/** finalize: Finaliza o objeto e limpa os dados de erros
	 */
	public void finalize(){
		this.dadosIntervalo.clear();
	}
}