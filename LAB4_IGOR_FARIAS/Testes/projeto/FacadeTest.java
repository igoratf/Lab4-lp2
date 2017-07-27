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
		 * Verifica se um sistema com caixa e taxa válidos está inicializando
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
			assertEquals("Erro no cadastro de cenario: Descricao nao pode ser nula", e.getMessage());
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
		assertEquals(2, facade.cadastrarCenario("Todo mundo paga P2"));
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
		facade.cadastrarCenario("Pagar tudo com 10");
		String actual = facade.exibirCenarios();
		String expected = facade.exibirCenario(1) + Utilidades.LN + facade.exibirCenario(2) + Utilidades.LN;
		assertEquals(expected, actual);
	}

	@Test
	public void cadastrarApostaTest() {
		/*
		 * Verifica se a aposta está sendo cadastrada no cenário
		 */
		facade.cadastrarCenario("Pagar P2");
		facade.cadastrarAposta(1, "Ash Ketchun", 1000, "VAI ACONTECER");
		assertEquals(1, facade.totalDeApostas(1));
		facade.cadastrarAposta(1, "Equipe Rocket", 2000, "N VAI ACONTECER");
		assertEquals(2, facade.totalDeApostas(1));
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
		facade.cadastrarAposta(1, "Eu", 2000, "VAI ACONTECER");
		facade.fecharAposta(1, false);
		assertEquals(20, facade.getCaixaCenario(1));
		/*
		 * Verifica se o estado de um cenário de apostas que ocorreu está sendo
		 * modificado corretamente
		 */
		facade.cadastrarCenario("Bilionário antes dos 30");
		facade.cadastrarAposta(2, "Caio Sanches", 20000, "N VAI ACONTECER");
		facade.fecharAposta(2, true);
		assertEquals(200, facade.getCaixaCenario(2));
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

	@Test
	public void cadastraCenarioBonusTest() {
		/*
		 * Verifica se o cadastro de um cenário bônus com bônus inválido retorna
		 * exceção
		 */
		try {
			facade.cadastrarCenario("Cadastrando cenário bônus", -2200);
		} catch (Exception e) {
			assertEquals("Erro no cadastro de cenario: Bonus invalido", e.getMessage());
		}

		/*
		 * Verifica se o cadastro de um cenário bônus válido está ocorrendo
		 * corretamente
		 */
		assertEquals(1, facade.cadastrarCenario("Quem quer dinheiro", 9000));
		assertEquals(2, facade.cadastrarCenario("Casino Royale", 300000));
	}

	@Test

	public void cadastrarApostaSeguraValorTest() {
		/*
		 * Verifica se o cadastro de uma aposta assegurada por valor, inválida,
		 * está retornando exceção
		 */
		try {
			facade.cadastrarCenario("Shao Khan dies", 2000);
			facade.cadastrarApostaSeguraValor(1, "Shang Tsung", 333, "N VAI ACONTECER", 300, 200);
		} catch (Exception e) {
			assertEquals("Erro ao cadastrar aposta assegurada: valor do seguro invalido", e.getMessage());
		}
		/*
		 * Verifica se o cadastro de uma aposta assegurada por valor válida está
		 * ocorrendo corretamente
		 */
		assertEquals(2, facade.cadastrarApostaSeguraValor(1, "Liu Kang", 900, "VAI ACONTECER", 40000, 2000));
	}

	public void cadastrarApostaSeguraTaxaTest() {
		/*
		 * Verifica se o cadastro de uma aposta assegurada por taxa, invalida,
		 * está retornando exceção
		 */
		try {
			facade.cadastrarCenario("Shao Khan dies", 20000);
			facade.cadastrarApostaSeguraTaxa(1, "Raiden", 20000, "VAI ACONTECER", -20, 200);
		} catch (Exception e) {
			assertEquals("Erro ao cadastrar aposta assegurada: taxa de seguro nao pode ser menor ou igual a zero",
					e.getMessage());
		}
		/*
		 * Verifica se o cadastro de uma aposta assegurada por taxa válida está
		 * ocorrendo corretamente
		 * 
		 */
		assertEquals(2, facade.cadastrarApostaSeguraTaxa(1, "Sonya Blade", 20000, "VAI ACONTECER", 0.25, 200));
	}
	
	@Test
	public void alterarSeguroValorTest() {
		/*
		 * Verifica se a alteração de seguro de uma aposta para assegurada por valor está ocorrendo corretamente
		 */
		facade.cadastrarCenario("It ends");
		facade.cadastrarApostaSeguraTaxa(1, "Eu", 2000, "VAI ACONTECER", 0.10, 200);
		facade.alterarSeguroValor(1, 1, 300);
		assertEquals(300, facade.getAposta(1, 1).getValorSeguro());
	}
	
	@Test
	public void alterarSeguroTaxaTest() {
		/*
		 * Verifica se a alteração de seguro de uma aposta para assegurada por taxa está ocorrendo corretamente
		 * 
		 */
		facade.cadastrarCenario("Zerar GTA V");
		facade.cadastrarApostaSeguraValor(1, "Eu", 2000, "VAI ACONTECER", 3000, 200);
		facade.alterarSeguroTaxa(1, 1, 0.20);
		assertEquals(0.20, facade.getAposta(1, 1).getTaxaSeguro(), 0.1);
	}
	
	@Test
	public void getCaixaCenarioBonusTest() {
		/*
		 * Verifica se o cenário bônus está retornando o valor correto para o caixa
		 */
		facade.cadastrarCenario("Fim do lab", 30000);
		facade.cadastrarAposta(1, "Qualquer um", 9000, "N VAI ACONTECER");
		facade.fecharAposta(1, true);
		assertEquals(90, facade.getCaixaCenario(1));
	}
	
	@Test
	public void getTotalRateioCenarioBonusTest() {
		/*
		 * Verifica se o cenário bônus está retornando o valor correto para rateio
		 */
		facade.cadastrarCenario("Fim do lab", 30000);
		facade.cadastrarAposta(1, "Jeremias", 20000, "N VAI ACONTECER");
		facade.fecharAposta(1, true);
		assertEquals(49800, facade.getTotalRateioCenario(1));
	}
	
	@Test
	public void getCaixaTest2() {
		/*
		 * Verifica se o caixa do sistema está retornando o valor correto ao possuir cenário bônus e apostas asseguradas
		 */
		facade.cadastrarCenario("Fim do lab", 50);
		facade.cadastrarAposta(1, "Mario", 200, "VAI ACONTECER");
		facade.cadastrarApostaSeguraValor(1, "MG", 200, "VAI ACONTECER", 200, 200);
		facade.cadastrarApostaSeguraTaxa(1, "Anyone", 200, "N VAI ACONTECER", 0.50, 200);
		facade.fecharAposta(1, true);
		assertEquals(1252, facade.getCaixa());
	}
	
	@Test
	public void exibirCenarioOrdenadoTest() {
		/*
		 * Verifica se a exibição dos cenários ordenados está ocorrendo corretamente
		 */
		
		facade.cadastrarCenario("Oi");
		assertEquals("1 - Oi - Nao finalizado", facade.exibirCenarioOrdenado(1));
	}

}