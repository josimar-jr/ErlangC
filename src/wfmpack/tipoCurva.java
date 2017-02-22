package wfmpack;

public enum tipoCurva {
	SEM(0), SEG(1), TER(2), QUA(3), QUI(4), SEX(5), SAB(6), DOM(7), FER(8);
	private final int valor;
	
	/** Inicialização do ENUM para determinar os dias da semana que a CURVA atua
	 * @param valorOpcao	int, valor que determina qual a opção
	 */
	tipoCurva(int valorOpcao){
		valor = valorOpcao;
	}
	
	/** getValor - retorna qual o conteúdo da opção
	 * @return valor 	int, conforme a opção utilizada
	 */
	public int getValor(){
		return valor;
	}
	
	/** getTipoPelaString - para ser chamado sem necessidade de instanciar a classe e identificar
	 * o tipo exato a ser atribuído quando o valor principal for String
	 * @param cTipo 	String, determina qual a string será analisada. É utilizado só as primeiras três letras por exemplo DOMINGO, vira DOM
	 * @return x 	int, define o tipo conforme a string recebida 
	 */
	public static tipoCurva getTipoPelaString( String cTipo ){
		tipoCurva x = SEM;
		cTipo = cTipo.toUpperCase();
		
		if (cTipo.length() > 3)
			cTipo = cTipo.substring(0, 3);
		
		switch (cTipo) {
			case "SEG" : 
				x = SEG;
				break;
			
			case "TER" : 
				x = TER;
				break;
			
			case "QUA" : 
				x = QUA;
				break;
			
			case "QUI" : 
				x = QUI;
				break;
			
			case "SEX" : 
				x = SEX;
				break;
			
			case "SAB" : 
				x = SAB;
				break;
			
			case "DOM" : 
				x = DOM;
				break;
			
			case "FER" : 
				x = FER;
				break;

			default :
				x = SEM;
				break;
		}
		return x;
	}
	
	/** getStringPeloTipo - identifica qual o dia em formato de {@link String} a partir do tipoCurva
	 * 
	 * @param tipo	tipoCurva, 
	 * @return x	String, retorna as três primeiras letras conforme o tipo, SEM, DOM, FER, etc
	 */
	public static String getStringPeloTipo( tipoCurva tipo ){
		String x = "";
		
		switch (tipo) {
			case SEG : 
				x = "SEG";
				break;
			
			case TER : 
				x = "TER";
				break;
			
			case QUA : 
				x = "QUA";
				break;
			
			case QUI : 
				x = "QUI";
				break;
			
			case SEX : 
				x = "SEX";
				break;
			
			case SAB : 
				x = "SAB";
				break;
			
			case DOM : 
				x = "DOM";
				break;
			
			case FER : 
				x = "FER";
				break;

			case SEM :
				x = "SEM";
				break;
		}
		return x;
	}
}