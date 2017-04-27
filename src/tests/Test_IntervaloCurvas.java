package tests;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import org.junit.Test;
import wfmpack.IntervaloCurvas;

public class Test_IntervaloCurvas {

	@Test
	public void testIntervaloCurvas() {
		GregorianCalendar hora = new GregorianCalendar(2017, 1, 1, 8, 0);
		double volume = 1900.58;
		double tma = 159.34;
		double percVolume = 0.0390;
		double percTma = 0.975;
		
		IntervaloCurvas ic = new IntervaloCurvas();
		ic.setHora( hora );
		ic.setVolume(volume);
		ic.setTma(tma);
		ic.setPercentTMA(percTma);
		ic.setPercentVolume(percVolume);
		
		assertEquals( "Deveria ser a mesma hora", hora, ic.getHora() );
		assertEquals( "Deveria ser o mesmo valor de volume", volume, ic.getVolume(), 0.00001 );
		assertEquals( "Deveria ser o mesmo valor de tma", tma, ic.getTma(), 0.00001 );
		assertEquals( "Deveria ser o mesmo valor de percentual do Tma", percTma, ic.getPercentTMA(), 0.00001 );
		assertEquals( "Deveria ser o mesmo valor de percentual do volume", percVolume, ic.getPercentVolume(), 0.00001 );
	}

	@Test
	public void testIntervaloCurvasGregorianCalendarDoubleDouble() {
		GregorianCalendar hora = new GregorianCalendar(2017, 1, 1, 8, 0);
		double volume = 1900.58;
		double tma = 159.34;
		
		IntervaloCurvas ic = new IntervaloCurvas(hora, volume, tma);
	}

	@Test
	public void testIntervaloCurvasDoubleDouble() {
		double volume = 1900.58;
		double tma = 159.34;
		
		IntervaloCurvas ic = new IntervaloCurvas(volume, tma);
	}

}
