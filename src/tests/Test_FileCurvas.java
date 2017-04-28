package tests;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import junit.framework.TestCase;
import wfmpack.FileCurvas;
import wfmpack.tipoCurva;
import wfmpack.Curvas;

public class Test_FileCurvas extends TestCase {
		
	public void testFileCurvas() {
    	String cPathFile = "src/tests/resources/testeimport.xlsx";
    	String cPathFileErro = "src/tests/resources/testeimporta.xlsx";
		FileCurvas fc = new FileCurvas();
		
		fc.setPath(cPathFileErro);
		assertFalse( "Não deveria conseguir ler o arquivo.", fc.lerArquivo() );
		
		fc.setPath(cPathFile);
		
		if (fc.lerArquivo()) {
			fc.exibir();
		}
		else {
			fail("Deveria conseguir ler o arquivo");
		}
	}

	public void testSetPath() {
    	String cpathfile = "src/tests/resources/testeimporta.xlsx";
		FileCurvas fc = new FileCurvas();
		
		fc.setPath(cpathfile);
		assertEquals( "Deveria ser o mesmo caminho de arquivo", cpathfile, fc.getPath() );
	}

	public void testGetCurvas() {
		ArrayList<Curvas> curvas;
		String cPathFile = "src/tests/resources/testeimport.xlsx";
		FileCurvas fcSetUp = new FileCurvas();
		
		fcSetUp.setPath(cPathFile);
		assertTrue( "Deveria ler o arquivo.", fcSetUp.lerArquivo() );
		
		curvas = fcSetUp.getCurvas();
		assertTrue( "Deveria existir quatro curvas.", (curvas.size() == 4) );
		
		// Verifica se o primeiro item é uma curva de semana
		assertEquals( "Deveria ser uma curva de semana.", tipoCurva.SEM, curvas.get(0).getTipoCurva() );
		
		// Verifica se o segundo item é uma curva de sábado
		assertEquals( "Deveria ser uma curva de sábado.", tipoCurva.SAB, curvas.get(1).getTipoCurva() );
		
		// Verifica se o terceiro item é uma curva de domingo
		assertEquals( "Deveria ser uma curva de domingo.", tipoCurva.DOM, curvas.get(2).getTipoCurva() );
		
		// Verifica se o quarto item é uma curva de feriado
		assertEquals( "Deveria ser uma curva de feriado.", tipoCurva.FER, curvas.get(3).getTipoCurva() );
	}
}
