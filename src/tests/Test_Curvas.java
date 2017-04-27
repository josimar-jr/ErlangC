package tests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;
import wfmpack.Curvas;
import wfmpack.tipoCurva;

public class Test_Curvas {

	@Test
	public void testSetTipoCurva() {
		tipoCurva tp = tipoCurva.SAB;
		
		Curvas curva = new Curvas();
		curva.setTipoCurva(tp);
		
		assertEquals( "Tipo da curva deveria ser o mesmo", tipoCurva.getStringPeloTipo(tp), tipoCurva.getStringPeloTipo( curva.getTipoCurva() ) );
	}

	@Test
	public void testAdicionarInformacaoGregorianCalendarDoubleDouble() {
		Curvas cv = new Curvas();
		GregorianCalendar horario = new GregorianCalendar(2017, 1, 1, 8, 0);
		double volume = 1900.87;
		double tma = 130.61;
		
		assertFalse( "Deveria ser falso", cv.adicionarInformacao(horario, volume, 0));
		assertFalse( "Deveria ser falso", cv.adicionarInformacao(horario, 0, tma));
		assertTrue( "Deveria ser falso", cv.adicionarInformacao(horario, volume, tma));
	}

	@Test
	public void testAdicionarInformacaoDoubleDouble() {
		Curvas cv = new Curvas();
		double volume = 1900.87;
		double tma = 130.61;
		
		assertFalse( "Deveria ser falso", cv.adicionarInformacao(volume, 0));
		assertFalse( "Deveria ser falso", cv.adicionarInformacao(0, tma));
		assertTrue( "Deveria ser falso", cv.adicionarInformacao(volume, tma));
	}

	@Test
	public void testCarregarTotais() {
		Curvas cv = new Curvas();
		double volume = 249.603501;
		double tma = 167.0964129;
		
		double volume1 = 35.5695327822188;
		double tma1 = 176.154421445548;
		
		double volume2 = 50.5146547049472;
		double tma2 = 185.390529174845;
		
		double volume3 = 72.2549584942596;
		double tma3 = 161.615965389454;

		double volume4 = 91.2643549856689;
		double tma4 = 157.779296856979;
		
		assertTrue( "Deveria inserir", cv.adicionarInformacao( volume1, tma1 ) );
		assertTrue( "Deveria inserir", cv.adicionarInformacao( volume2, tma2 ) );
		assertTrue( "Deveria inserir", cv.adicionarInformacao( volume3, tma3 ) );
		assertTrue( "Deveria inserir", cv.adicionarInformacao( volume4, tma4 ) );
		
		// Carrega os totais
		cv.carregarTotais();
		
		assertEquals( "Deveria ser o total de chamadas", volume, cv.getTotalVolume(), 0.000001 );
		assertEquals( "Deveria ser a média do tma", tma, cv.getTotalTMA(), 0.000001 );
	}
	
	@Test
	public void testExibir() {
		Curvas cv = new Curvas();
		double volume = 1900.87;
		double tma = 130.61;
		
		cv.setTipoCurva(tipoCurva.SEM);
		assertTrue( "Deveria ser falso", cv.adicionarInformacao(volume, tma));
		cv.exibir();
	}
}
