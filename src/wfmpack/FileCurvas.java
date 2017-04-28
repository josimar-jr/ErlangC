package wfmpack;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileCurvas {
	
	ArrayList<Curvas> curvas = new ArrayList<Curvas>();
	String path = "";
	
	/** FileCurvas - construtor sem parâmetros
	 */
	public FileCurvas(){
	}
	
	/** setPath - define o diretório e arquivo a serem utilizado na leitura das curvas
	 * @param path	String, caminho completo com o nome do arquivo para leitura. Exemplo: c:\testes\arquivos\curvas.xlsx
	 */
	public void setPath( String path ){
		if ( !path.isEmpty() ) {
				this.path = path;
		}
	}
	
	/** getPath - retorna o caminho/diretório e arquivo configurado para leitura
	 * @return path	String, caminho e diretório com o arquivo
	 */
	public String getPath(){
		return this.path;
	}

	/** getCurvas - retorna a lista de curvas identificadas a partir da leitura do arquivo.
	 * @return curvas	{@link ArrayList} {@link Curvas}, lista dos valores lidos através do arquivo de entrada
	 */
	public ArrayList<Curvas> getCurvas(){
		return curvas;
	}
	
	/** getCurvaEspecifica - captura as informações de uma curva em um objeto de com a lista de {@link Curvas} [ {@link FileCurvas} ]
	 * @param x	int, indica qual o elemento da lista deseja utilizar
	 * @return	tempCv	{@link Curvas}, objeto com as informações da curva para o dia intervalo a intervalo
	 */
	public Curvas getCurvaEspecifica( int x ){
		Curvas tempCv = null;
		
		if ( x < this.curvas.size() ) {
			tempCv = this.curvas.get(x);
		}
			
		return tempCv;
	}

	/** lerArquivo - executa a leitura do arquivo indicado no path e se a importação dos dados aconteceu com sucesso
	 * @return lOk 	boolean, determina se conseguiu realizar a leitura com sucesso do arquivo
	 */
	public boolean lerArquivo(){
		boolean lOk = false;
		String pathFile = this.getPath();
		FileInputStream fileImport;
		XSSFWorkbook planilha;
		XSSFSheet aba;
		Iterator<Row> linhas;
		Row linha;
		int abas = 0;
		int ultimaCurva;
		Curvas tempCurvas;
		tipoCurva tipoCurvaImport;
		
		Date horario;
		GregorianCalendar gregCalFormat;
		double valorVolume;
		double valorTMA;
		
		try{
			if ( !pathFile.isEmpty() ){
				fileImport = new FileInputStream( new File(pathFile) );
				
				// Carregando workbook
				planilha = new XSSFWorkbook(fileImport);

				// identifica o número de aba na planilha
				abas = planilha.getNumberOfSheets();
				
				// marca a leitura com sucesso
				lOk = true;
				
				// itera pelas abas para leitura de todas as curvas
				for (int x = 0 ; (lOk && x < abas) ; x++){

					// Selecionando a aba a ler o conteúdo
					aba = planilha.getSheetAt(x);
				
					// cria o iterador para as linhas da aba
					linhas = aba.iterator();

					// adiciona e posiciona na nova curva
					curvas.add( new Curvas() );
					ultimaCurva = ( curvas.size() - 1) ;
					tempCurvas = curvas.get(ultimaCurva);
					
					//identifica o tipo da curva e adiciona o tipo
					tipoCurvaImport = tipoCurva.getTipoPelaString( aba.getSheetName() );
					tempCurvas.setTipoCurva(tipoCurvaImport);
					
					// identifica as linhas e captura o conteúdo delas
					while (linhas.hasNext() && lOk) {
						// Carregar a linha na variavel
						linha = linhas.next();
						
						// Se for a linha 1 ou mais adicionar valor
						if (linha.getRowNum() >= 1) {
							
							horario = linha.getCell(0).getDateCellValue();
							valorVolume = linha.getCell(1).getNumericCellValue();
							valorTMA = linha.getCell(2).getNumericCellValue();
							
							if (horario != null) {
								gregCalFormat = new GregorianCalendar(2015, 1, 1, horario.getHours(), horario.getMinutes());
								
								if(valorVolume > 0.0 && valorTMA > 0.0 && gregCalFormat != null) {
									lOk = tempCurvas.adicionarInformacao( gregCalFormat, valorVolume, valorTMA );
								} 
								else {
									lOk = tempCurvas.adicionarInformacao( valorVolume, valorTMA );
								}
							}
							else {
								if ( valorVolume != 0 && valorTMA != 0 ) {
									lOk = tempCurvas.adicionarInformacao( valorVolume, valorTMA );
								}
							}
						}
					}
					// adiciona as informações de sumarizadores do objeto
					tempCurvas.carregarTotais();
				}
				
				planilha.close();
				fileImport.close();
				
			}
		} catch (Exception e){
			lOk = false;
		}
		
		return lOk;
	}
	
	/** exibir - saída básica das informações lidas pela 
	 */
	public void exibir(){
		// itera pelas curvas e chama a exibição do objeto de curvas
		System.out.println("Arquivo: " + this.getPath());
		for ( Curvas x : curvas ){
			System.out.println("_______________________________________");
			x.exibir();
		}
	}
	
	/** finalize: Finaliza o objeto e limpa os dados dos intervalos
	 */
	public void finalize(){
		this.curvas.clear();
		this.path = "";
	}
}