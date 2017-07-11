import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FacadeTest {
	Facade facade = new Facade();

	@Before
	/*
	 * Inicializa o sistema antes de realizar os testes
	 */
	public void setup() {
		facade.inicializa(1000, 0.01);
	}

	@Test
	public void inicializaTest() {
		/*
		 * Verifica se um sistema com caixa e taxa inválidos está retornando
		 * exceção
		 */
		try {
			facade.inicializa(-10, 0);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		try {
			/*
			 * Verifica se taxa negativa está retornando exceção
			 */
			facade.inicializa(100, -1);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		try {
			/*
			 * Verifica se caixa negativo está retornando exceção
			 */
			facade.inicializa(-10, 0.10);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		/*
		 * Verifica se a criação de um controlador com caixa e taxa válidos está
		 * inicializando
		 */
		facade.inicializa(100, 0.01);
		assertEquals(100, facade.getCaixa());
		assertEquals(0.01, facade.getTaxa(), 0);
	}

	@Test
	public void cadastrarCenarioTest() {
		/*
		 * Verifica se a criação de um cenário com descrição nula está
		 * retornando exceção
		 */
		try {
			facade.cadastrarCenario(null);
		} catch (Exception e) {
			assertEquals("descricao nula", e.getMessage());
		}
		/*
		 * Verifica se a criação de um cenário com descrição vazia está
		 * retornando exceção
		 */
		try {
			facade.cadastrarCenario("     ");
		} catch (Exception e) {
			assertEquals("descricao invalida", e.getMessage());
		}
		/*
		 * Verifica se um cenário válido está sendo cadastrado corretamente
		 */
		assertEquals(1, facade.cadastrarCenario("Todo mundo vai pagar cálculo 2"));
		assertEquals("Todo mundo vai pagar cálculo 2", facade.controller.getCenario(1).getDescricao());
	}

	@Test
	public void exibirCenarioTest() {
		/*
		 * Verifica se a tentativa de exibição de um cenário inexistente está
		 * retornando exceção
		 */
		try {
			facade.controller.getCenario(1);
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibição de um cenário válido está sendo
		 * realizada corretamente
		 */
		facade.cadastrarCenario("Rumo ao CRA 9");
		String expected = "1 - Rumo ao CRA 9 - Não finalizado" + Utilidades.LN;
		assertEquals(expected, facade.exibirCenario(1));
	}

	@Test
	public void exibirCenariosTest() {
		/*
		 * Verifica se a exibição dos cenários está acontecendo corretamente
		 */
		facade.cadastrarCenario("Conseguir uma bolsa");
		facade.cadastrarCenario("Pagar todas as cadeiras por media");
		String actual = facade.exibirCenarios();
		String expected = facade.controller.getCenario(1).toString() + facade.controller.getCenario(2).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void cadastrarApostaTest() {
		/*
		 * Verifica se a aposta está sendo cadastrada no cenário
		 */
		facade.cadastrarCenario("Pagar P2");
		facade.cadastrarAposta(1, "Ash Ketchun", 1000, "VAI ACONTECER");
		assertEquals(1, facade.controller.getCenario(1).getApostas().size());
		/*
		 * Verifica se a tentativa de cadastrar uma aposta em um cenário
		 * inválido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(0, "Godzilla", 10, "n vai acontecer");
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com apostador nulo
		 * em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, null, 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("apostador ou previsao nulos", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com valor inválido
		 * em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "PotatoHead", -1, "vai acontecer");
		} catch (Exception e) {
			assertEquals("valor invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com previsão
		 * inválida em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "Eu mesmo", 200, "nenhuma previsao");
		} catch (Exception e) {
			assertEquals("previsao invalida", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com apostador vazio
		 * está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "  ", 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("apostador ou previsao vazios", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com previsão vazia
		 * está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "Donkey Kong", 100020, " ");
		} catch (Exception e) {
			assertEquals("apostador ou previsao vazios", e.getMessage());
		}
	}

	@Test
	public void valorTotalDasApostasTest() {
		/*
		 * Verifica se o valor total das apostas de um cenário está sendo
		 * exibido corretamente
		 */
		facade.cadastrarCenario("Ganhar corrida");
		facade.cadastrarAposta(1, "Mario do Armario", 24, "vai acontecer");
		facade.cadastrarAposta(1, "Toad", 24, "n vai acontecer");
		assertEquals(48, facade.valorTotalDasApostas(1));
		/*
		 * Verifica se a tentativa de exibição do valor total das apostas para
		 * um cenário inválido está retornando exceção
		 */
		try {
			facade.valorTotalDasApostas(3);
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibição do valor total das apostas para
		 * um cenário que recebe uma aposta inválida está retornando exceção
		 */
		try {
			facade.cadastrarCenario("Vai nada");
			facade.cadastrarAposta(2, null, 10, "vai acontecer");
		} catch (Exception e) {
			assertEquals("apostador ou previsao nulos", e.getMessage());
		}
	}

	@Test
	public void exibeApostasTest() {
		/*
		 * Verifica se a representação textual das apostas de um cenário está
		 * funcionando. Como não há ordem entre as apostas, o teste utiliza
		 * apenas uma para verificar o funcionamento
		 */
		facade.cadastrarCenario("Zerando a vida");
		facade.cadastrarAposta(1, "Eu", 999, "vai acontecer");
		Aposta aposta = new Aposta("Eu", 999, "vai acontecer");
		assertEquals(aposta.toString(), facade.exibeApostas(1));
	}

	@Test
	public void fecharApostaTest() {
		/*
		 * Verifica se o estado de um cenário de apostas que não ocorreu está
		 * sendo modificado corretamente
		 */
		facade.cadastrarCenario("Será que agora vai?");
		facade.fecharAposta(1, false);
		assertEquals("Finalizado (n ocorreu)", facade.controller.getCenario(1).getEstado());
		/*
		 * Verifica se o estado de um cenário de apostas que ocorreu está sendo
		 * modificado corretamente
		 */
		facade.cadastrarCenario("Bilionário antes dos 30");
		facade.fecharAposta(2, true);
		assertEquals("Finalizado (ocorreu)", facade.controller.getCenario(2).getEstado());
	}

	@Test
	public void getCaixaCenarioTest() {
		/*
		 * Verifica se o valor destinado ao caixa ao fim de um cenário de
		 * apostas está correto
		 */
		facade.cadastrarCenario("Será que um dia eu termino esse lab?");
		facade.cadastrarAposta(1, "Eu", 1000, "vai acontecer");
		facade.cadastrarAposta(1, "Snoop Dogg", 500, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(5.0, facade.getCaixaCenario(1), 0.2);
	}

	@Test
	public void getTotalRateioCenario() {
		/*
		 * Verifica se o valor destinado ao rateio entre os vencedores está
		 * correto
		 */
		facade.cadastrarCenario("O fim está próximo");
		facade.cadastrarAposta(1, "Mãe Diná", 2000, "vai acontecer");
		facade.cadastrarAposta(1, "Pessimista", 2000, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(1980, facade.getTotalRateioCenario(1));
	}
}
