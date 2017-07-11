package projeto;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Controller;

public class ControllerTest {
	Controller controller;

	@Before
	public void setup() {
		controller = new Controller(1000, 0.01);
	}

	@Test
	public void valorTotalApostasPerdedorasTest() {
		/*
		 * Verifica se o valor total das apostas perdedoras est� correto
		 */
		controller.cadastrarCenario("Seria esse apenas o inicio?");
		controller.cadastrarAposta(1, "Chuck Norris", 30000, "n vai acontecer");
		controller.cadastrarAposta(1, "Shang Tsung", 2000, "vai acontecer");
		controller.fecharAposta(1, false);
		assertEquals(2000, controller.valorTotalApostasPerdedoras(1));
		controller.cadastrarCenario("� o fim!");
		controller.cadastrarAposta(2, "Raiden", 20000, "vai acontecer");
		controller.cadastrarAposta(2, "Shao Kahn", 6000, "n vai acontecer");
		controller.cadastrarAposta(2, "Goro", 4000, "n vai acontecer");
		controller.cadastrarAposta(2, "Kintaro", 2000, "n vai acontecer");
		controller.fecharAposta(2, true);
		assertEquals(12000, controller.valorTotalApostasPerdedoras(2));
	}

}
