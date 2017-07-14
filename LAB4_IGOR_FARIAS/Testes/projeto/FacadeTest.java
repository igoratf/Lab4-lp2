package projeto;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Aposta;
import projeto.Facade;

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
			assertEquals("Erro na inicializacao: Caixa nao pode ser inferior a 0", e.getMessage());
		}
		try {
			/*
			 * Verifica se taxa negativa está retornando exceção
			 */
			facade.inicializa(100, -1);
		} catch (Exception e) {
			assertEquals("Erro na inicializacao: Taxa nao pode ser inferior a 0", e.getMessage());
		}
		try {
			/*
			 * Verifica se caixa negativo está retornando exceção
			 */
			facade.inicializa(-10, 0.10);
		} catch (Exception e) {
			assertEquals("Erro na inicializacao: Caixa nao pode ser inferior a 0", e.getMessage());
		}
		/*
		 * Verifica se um sistema com caixa e taxa válidos está
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
			assertEquals("Erro no cadastro de cenario: Descricao nao pode ser vazia", e.getMessage());
		}
		/*
		 * Verifica se um cenário válido está sendo cadastrado corretamente
		 */
		assertEquals(1, facade.cadastrarCenario("Todo mundo vai pagar calculo 2"));
		assertEquals("Todo mundo vai pagar calculo 2", facade.controller.getCenario(1).getDescricao());
	}

	@Test
	public void exibirCenarioTest() {
		/*
		 * Verifica se a tentativa de exibição de um cenário inexistente está
		 * retornando exceção
		 */
		try {
			facade.exibirCenario(1);
		} catch (Exception e) {
			assertEquals("Erro na consulta de cenario: Cenario nao cadastrado", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibição de um cenário válido está sendo
		 * realizada corretamente
		 */
		facade.cadastrarCenario("Rumo ao CRA 9");
		String expected = "1 - Rumo ao CRA 9 - Nao finalizado";
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
		assertEquals(1, facade.controller.getCenario(1).totalDeApostas());
		/*
		 * Verifica se a tentativa de cadastrar uma aposta em um ceá�rio
		 * inválido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(0, "Godzilla", 10, "n vai acontecer");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Cenario invalido", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com apostador nulo
		 * em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, null, 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Apostador nao pode ser vazio ou nulo", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com valor inválido
		 * em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "PotatoHead", -1, "vai acontecer");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Valor nao pode ser menor ou igual a zero", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com previsão
		 * inválida em um cenário válido está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "Eu mesmo", 200, "nenhuma previsao");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Previsao invalida", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com apostador vazio
		 * está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "  ", 100, "vai acontecer");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Apostador nao pode ser vazio ou nulo", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de criação de uma aposta com previsão vazia
		 * está retornando exceção
		 */
		try {
			facade.cadastrarAposta(1, "Donkey Kong", 100020, " ");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Previsao nao pode ser vazia ou nula", e.getMessage());
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
		assertEquals(48, facade.valorTotalDeApostas(1));
		/*
		 * Verifica se a tentativa de exibição do valor total das apostas para
		 * um cenário inválido está retornando exceção
		 */
		try {
			facade.valorTotalDeApostas(3);
		} catch (Exception e) {
			assertEquals("Erro na consulta do valor total de apostas: Cenario nao cadastrado", e.getMessage());
		}
		/*
		 * Verifica se a tentativa de exibição do valor total das apostas para
		 * um cenário que recebe uma aposta inválida está retornando exceção
		 */
		try {
			facade.cadastrarCenario("Vai nada");
			facade.cadastrarAposta(2, null, 10, "vai acontecer");
		} catch (Exception e) {
			assertEquals("Erro no cadastro de aposta: Apostador nao pode ser vazio ou nulo", e.getMessage());
		}
	}

	@Test
	public void exibeApostasTest() {
		/*
		 * Verifica se a representação textual das apostas de um cenário está
		 * funcionando. 
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
		 * Verifica se o estado de um cen�rio de apostas que ocorreu est� sendo
		 * modificado corretamente
		 */
		facade.cadastrarCenario("Bilionário antes dos 30");
		facade.fecharAposta(2, true);
		assertEquals("Finalizado (ocorreu)", facade.controller.getCenario(2).getEstado());
	}

	@Test
	public void getCaixaCenarioTest() {
		/*
		 * Verifica se o valor destinado ao caixa ao fim de um cenario de
		 * apostas esta correto
		 */
		facade.cadastrarCenario("Sera que um dia eu termino esse lab?");
		facade.cadastrarAposta(1, "Eu", 1000, "vai acontecer");
		facade.cadastrarAposta(1, "Snoop Dogg", 10000, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(100, facade.getCaixaCenario(1), 0.0);
	}

	@Test
	public void getTotalRateioCenario() {
		/*
		 * Verifica se o valor destinado ao rateio entre os vencedores está
		 * correto
		 */
		facade.cadastrarCenario("O fim está próximo");
		facade.cadastrarAposta(1, "Eminem", 2000, "vai acontecer");
		facade.cadastrarAposta(1, "Pessimista", 2000, "n vai acontecer");
		facade.fecharAposta(1, true);
		assertEquals(1980, facade.getTotalRateioCenario(1));
	}
}