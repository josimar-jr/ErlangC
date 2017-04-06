package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import wfmpack.tipoCurva;

public class tipoCurvaTest {

	@Test
	public void testTipoCurva() {
		int valorSemana = 0;
		int valorDomingo = 7;
		int valorSexta = 5;
		
		String strSemana = "SEM";
		String strDomingo = "DOM";
		String strSexta = "SEX";
		
		tipoCurva tpCurvaSemana = tipoCurva.SEM;
		tipoCurva tpCurvaDomingo = tipoCurva.DOM;
		tipoCurva tpCurvaSexta = tipoCurva.SEX;
		
		assertEquals( tpCurvaSemana.getValor(), valorSemana );
		assertEquals( tpCurvaDomingo.getValor(), valorDomingo );
		assertEquals( tpCurvaSexta.getValor(), valorSexta );
		
		assertEquals( tipoCurva.getStringPeloTipo(tpCurvaSemana), strSemana );
		assertEquals( tipoCurva.getStringPeloTipo(tpCurvaDomingo), strDomingo );
		assertEquals( tipoCurva.getStringPeloTipo(tpCurvaSexta), strSexta );
		
	}

}
