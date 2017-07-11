package projeto;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Aposta;
import projeto.Facade;
import projeto.Utilidades;

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
		 * Verifica se um sistema com caixa e taxa inv�lidos est� retornando
		 * exce��o
		 */
		try {
			facade.inicializa(-10, 0);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		try {
			/*
			 * Verifica se taxa negativa est� retornando exce��o
			 */
			facade.inicializa(100, -1);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		try {
			/*
			 * Verifica se caixa negativo est� retornando exce��o
			 */
			facade.inicializa(-10, 0.10);
		} catch (Exception e) {
			assertEquals("caixa ou taxa invalidos", e.getMessage());
		}
		/*
		 * Verifica se a cria��o de um controlador com caixa e taxa v�lidos est�
		 * inicializando
		 */
		facade.inicializa(100, 0.01);
		assertEquals(100, facade.getCaixa());
		assertEquals(0.01, facade.getTaxa(), 0);
	}

	@Test
	public void cadastrarCenarioTest() {
		/*
		 * Verifica se a cria��o de um cen�rio com descri��o nula est�
		 * retornando exce��o
		 */
		try {
			facade.cadastrarCenario(null);
		} catch (Exception e) {
			assertEquals("descricao nula", e.getMessage());
		}
		/*
		 * Verifica se a cria��o de um cen�rio com descri��o vazia est�
		 * retornando exce��o
		 */
		try {
			facade.cadastrarCenario("     ");
		} catch (Exception e) {
			assertEquals("descricao invalida", e.getMessage());
		}
		/*
		 * Verifica se um cen�rio v�lido est� sendo cadastrado corretamente
		 */
		assertEquals(1, facade.cadastrarCenario("Todo mundo vai pagar c�lculo 2"));
		assertEquals("Todo mundo vai pagar c�lculo 2", facade.controller.getCenario(1).getDescricao());
	}

	@Test
	public void exibirCenarioTest() {
		/*
		 * Verifica se a tentativa de exibi��o de um cen�rio inexistente est�
		 * retornando exce��o
		 */
		try {
			facade.controller.getCenario(1);
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibi��o de um cen�rio v�lido est� sendo
		 * realizada corretamente
		 */
		facade.cadastrarCenario("Rumo ao CRA 9");
		String expected = "1 - Rumo ao CRA 9 - N�o finalizado" + Utilidades.LN;
		assertEquals(expected, facade.exibirCenario(1));
	}

	@Test
	public void exibirCenariosTest() {
		/*
		 * Verifica se a exibi��o dos cen�rios est� acontecendo corretamente
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
		 * Verifica se a aposta est� sendo cadastrada no cen�rio
		 */
		facade.cadastrarCenario("Pagar P2");
		facade.cadastrarAposta(1, "Ash Ketchun", 1000, "VAI ACONTECER");
		assertEquals(1, facade.controller.getCenario(1).getApostas().size());
		/*
		 * Verifica se a tentativa de cadastrar uma aposta em um cen�rio
		 * inv�lido est� retornando exce��o
		 */
		try {
			facade.cadastrarAposta(0, "Godzilla", 10, "n vai acontecer");
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de cria��o de uma aposta com apostador nulo
		 * em um cen�rio v�lido est� retornando exce��o
		 */
		try {
			facade.cadastrarAposta(1, null, 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("apostador ou previsao nulos", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de cria��o de uma aposta com valor inv�lido
		 * em um cen�rio v�lido est� retornando exce��o
		 */
		try {
			facade.cadastrarAposta(1, "PotatoHead", -1, "vai acontecer");
		} catch (Exception e) {
			assertEquals("valor invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de cria��o de uma aposta com previs�o
		 * inv�lida em um cen�rio v�lido est� retornando exce��o
		 */
		try {
			facade.cadastrarAposta(1, "Eu mesmo", 200, "nenhuma previsao");
		} catch (Exception e) {
			assertEquals("previsao invalida", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de cria��o de uma aposta com apostador vazio
		 * est� retornando exce��o
		 */
		try {
			facade.cadastrarAposta(1, "  ", 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("apostador ou previsao vazios", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de cria��o de uma aposta com previs�o vazia
		 * est� retornando exce��o
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
		 * Verifica se o valor total das apostas de um cen�rio est� sendo
		 * exibido corretamente
		 */
		facade.cadastrarCenario("Ganhar corrida");
		facade.cadastrarAposta(1, "Mario do Armario", 24, "vai acontecer");
		facade.cadastrarAposta(1, "Toad", 24, "n vai acontecer");
		assertEquals(48, facade.valorTotalDasApostas(1));
		/*
		 * Verifica se a tentativa de exibi��o do valor total das apostas para
		 * um cen�rio inv�lido est� retornando exce��o
		 */
		try {
			facade.valorTotalDasApostas(3);
		} catch (Exception e) {
			assertEquals("cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibi��o do valor total das apostas para
		 * um cen�rio que recebe uma aposta inv�lida est� retornando exce��o
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
		 * Verifica se a representa��o textual das apostas de um cen�rio est�
		 * funcionando. Como n�o h� ordem entre as apostas, o teste utiliza
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
		 * Verifica se o estado de um cen�rio de apostas que n�o ocorreu est�
		 * sendo modificado corretamente
		 */
		facade.cadastrarCenario("Ser� que agora vai?");
		facade.fecharAposta(1, false);
		assertEquals("Finalizado (n ocorreu)", facade.controller.getCenario(1).getEstado());
		/*
		 * Verifica se o estado de um cen�rio de apostas que ocorreu est� sendo
		 * modificado corretamente
		 */
		facade.cadastrarCenario("Bilion�rio antes dos 30");
		facade.fecharAposta(2, true);
		assertEquals("Finalizado (ocorreu)", facade.controller.getCenario(2).getEstado());
	}

	@Test
	public void getCaixaCenarioTest() {
		/*
		 * Verifica se o valor destinado ao caixa ao fim de um cen�rio de
		 * apostas est� correto
		 */
		facade.cadastrarCenario("Ser� que um dia eu termino esse lab?");
		facade.cadastrarAposta(1, "Eu", 1000, "vai acontecer");
		facade.cadastrarAposta(1, "Snoop Dogg", 500, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(5.0, facade.getCaixaCenario(1), 0.2);
	}

	@Test
	public void getTotalRateioCenario() {
		/*
		 * Verifica se o valor destinado ao rateio entre os vencedores est�
		 * correto
		 */
		facade.cadastrarCenario("O fim est� pr�ximo");
		facade.cadastrarAposta(1, "M�e Din�", 2000, "vai acontecer");
		facade.cadastrarAposta(1, "Pessimista", 2000, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(1980, facade.getTotalRateioCenario(1));
	}
}
