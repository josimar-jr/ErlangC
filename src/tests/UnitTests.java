package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Test_Erlang.class,
	Test_tipoCurva.class,
	Test_Intervalo.class,
	Test_IntervaloCurvas.class,
	Test_Curvas.class,
	Test_FileCurvas.class })
public class UnitTests {
}
