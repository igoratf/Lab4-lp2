package projeto;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Classe controladora que representa o sistema que irá administrar o
 * cadastramento de cenários e apostas e suas resoluções
 * 
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */

public class Controller {
	private ArrayList<Cenario> listaCenarios = new ArrayList<Cenario>();
	private ArrayList<Cenario> cenariosOrdenados = new ArrayList<Cenario>();
	private int caixa;
	private double taxa;

	public Controller(int caixa, double taxa) {
		if (caixa < 0) {
			throw new IllegalArgumentException("Erro na inicializacao: Caixa nao pode ser inferior a 0");
		}
		if (taxa < 0) {
			throw new IllegalArgumentException("Erro na inicializacao: Taxa nao pode ser inferior a 0");
		}
		this.caixa = caixa;
		this.taxa = taxa;
	}

	/**
	 * Cadastra um cenário para receber apostas
	 * 
	 * @param descricao
	 *            é a situação do cenário que poderá acontecer ou não
	 * @return o número de identificação do cenário
	 */
	public int cadastrarCenario(String descricao) {
		Cenario cenario = new Cenario(descricao);
		listaCenarios.add(cenario);
		cenariosOrdenados.add(cenario);
		cenario.setIdCenario(listaCenarios.size());
		return listaCenarios.size();
	}

	/**
	 * Exibe informações de um cenario
	 * 
	 * @param cenario
	 *            o número de identificação do cenário
	 * @return representação textual do cenário
	 */
	public String exibirCenario(int cenario) {
		cenarioInvalidoExcecoes("Erro na consulta de cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return Integer.toString(getNumCenario(meuCenario)) + " - " + meuCenario.toString();
	}

	/**
	 * Exibe informações de todos os cenarios cadastrados
	 * 
	 * @return representação textual de todos os cenários cadastrados
	 */
	public String exibirCenarios() {
		String retorno = "";
		for (Cenario cenario : listaCenarios) {
			retorno += Integer.toString(getNumCenario(cenario)) + " - " + cenario.toString() + Utilidades.LN;
		}
		return retorno;
	}

	/**
	 * Cadastra uma aposta no cenário especificado
	 * 
	 * @param cenario
	 *            é o número de identificação cenário
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta em centavos
	 * @param previsao
	 *            é a previsão da aposta sobre o cenário
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor, String previsao) {
		cenarioInvalidoExcecoes("Erro no cadastro de aposta: ", cenario);
		apostaExcecoes("Erro no cadastro de aposta: ", apostador, valor, previsao);
		Aposta aposta = new Aposta(apostador, valor, previsao);
		Cenario meuCenario = getCenario(cenario);
		meuCenario.cadastrarAposta(aposta);
	}

	/**
	 * Chama o método valorTotalDeApostas da classe Cenario para retornar o
	 * valor total das apostas de um cenário
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return é o valor total das apostas do cenário em centavos
	 */
	public int valorTotalDeApostas(int cenario) {
		cenarioInvalidoExcecoes("Erro na consulta do valor total de apostas: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.valorTotalDeApostas();
	}

	/**
	 * Chama o método totalDeApostas de Cenario para retornar a quantidade de
	 * apostas do cenário
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return quantidade de apostas do cenário em inteiro
	 */
	public int totalDeApostas(int cenario) {
		cenarioInvalidoExcecoes("Erro na consulta do total de apostas: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.totalDeApostas();
	}

	/**
	 * Chama o método exibeApostas de Cenario para exibir todas as apostas de um
	 * cenário
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return representação textual de todas as apostas de um cenário
	 */
	public String exibeApostas(int cenario) {
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.exibeApostas();
	}

	/**
	 * Chama o método fecharAposta de Cenario para finalizar o cenário de
	 * apostas
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @param ocorreu
	 *            especifica se o cenário ocorreu ou não
	 */
	public void fecharAposta(int cenario, boolean ocorreu) {
		cenarioInvalidoExcecoes("Erro ao fechar aposta: ", cenario);
		cenarioFechadoExcecao("Erro ao fechar aposta: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		meuCenario.fecharAposta(ocorreu, this.taxa);	
		this.caixa += meuCenario.getCaixaCenario();
		this.caixa -= meuCenario.getValorTotalSeguros();
	}

	/**
	 * Retorna o valor em caixa do cenário que será destinado ao caixa do
	 * sistema
	 * 
	 * 
	 * @param cenario
	 * @return retorna o valor que será destinado ao caixa em centavos
	 */
	public int getCaixaCenario(int cenario) {
		cenarioInvalidoExcecoes("Erro na consulta do caixa do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do caixa do cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.getCaixaCenario();
	}

	/**
	 * Retorna o valor que será destinado ao rateio dos vencedores do cenário de
	 * apostas
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return valor total a ser rateado entre os vencedores, em centavos
	 */
	public int getTotalRateioCenario(int cenario) {
		cenarioInvalidoExcecoes("Erro na consulta do total de rateio do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do total de rateio do cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.getTotalRateioCenario();
	}

	/**
	 * Cadastra um Cenário Bônus
	 * 
	 * @param descricao
	 *            é a situação possível do cenário
	 * @param bonus
	 *            é o bônus do cenário
	 * @return a numeração do cenário
	 */
	public int cadastrarCenario(String descricao, int bonus) {
		Cenario cenario = new CenarioBonus(descricao, bonus);
		listaCenarios.add(cenario);
		cenariosOrdenados.add(cenario);
		this.caixa -= bonus;
		return getNumCenario(cenario);
	}

	/**
	 * Cadastra uma aposta assegurada pelo valor
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta
	 * @param previsao
	 *            é a previsão da aposta para o possível cenário
	 * @param valorSeguro
	 *            é o valor do seguro da aposta
	 * @param custo
	 *            é o custo para o seguro da aposta
	 * @return número de identificação da aposta assegurada
	 */
	public int cadastrarApostaSeguraValor(int cenario, String apostador, int valor, String previsao, int valorSeguro,
			int custo) {
		apostaExcecoes("Erro no cadastro de aposta assegurada por valor: ", apostador, valor, previsao);
		cenarioInvalidoExcecoes("Erro no cadastro de aposta assegurada por valor: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		ApostaSeguraValor apostaSeguraVal = new ApostaSeguraValor(apostador, valor, previsao, valorSeguro, custo);
		this.caixa += custo;
		return meuCenario.cadastrarApostaSeguraValor(apostaSeguraVal);
	}

	/**
	 * Cadastra uma aposta assegurada por taxa
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta
	 * @param previsao
	 *            é a previsão do possível cenário
	 * @param taxa
	 *            é a taxa de seguro da aposta
	 * @param custo
	 *            é o custo do seguro da aposta
	 * @return número de identificação da aposta assegurada
	 */

	public int cadastrarApostaSeguraTaxa(int cenario, String apostador, int valor, String previsao, double taxa,
			int custo) {
		cenarioInvalidoExcecoes("Erro no cadastro de aposta assegurada por taxa: ", cenario);
		apostaExcecoes("Erro no cadastro de aposta assegurada por taxa: ", apostador, valor, previsao);
		ApostaSeguraTaxa apostaSeguraTaxa = new ApostaSeguraTaxa(apostador, valor, previsao, taxa, custo);
		Cenario meuCenario = getCenario(cenario);
		this.caixa += custo;
		return meuCenario.cadastrarApostaSeguraTaxa(apostaSeguraTaxa);
	}

	/**
	 * Altera o tipo de seguro de uma aposta assegurada para assegurada por
	 * valor
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @param apostaAssegurada
	 *            é o número de identificação da aposta assegurada
	 * @param valor
	 *            é o valor do seguro
	 */
	public int alterarSeguroValor(int cenario, int apostaAssegurada, int valor) {
		Cenario meuCenario = getCenario(cenario);
		Aposta aposta = meuCenario.getApostaSegura(apostaAssegurada);
		if (aposta.getTipo().equalsIgnoreCase("TAXA")) {
			aposta.setTipo("VALOR");
			aposta.setValorSeguro(valor);
			aposta.setTaxaSeguro(0);
		}
		return apostaAssegurada;
	}

	/**
	 * Altera o tipo de seguro de uma aposta para assegurada por taxa
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @param apostaAssegurada
	 *            é a identificação da aposta assegurada
	 * @param taxa
	 *            é a taxa assegurada pela aposta segura
	 */
	public int alterarSeguroTaxa(int cenario, int apostaAssegurada, double taxa) {
		Cenario meuCenario = getCenario(cenario);
		Aposta aposta = meuCenario.getApostaSegura(apostaAssegurada);
		if (aposta.getTipo().equalsIgnoreCase("VALOR")) {
			aposta.setTipo("TAXA");
			aposta.setValorSeguro(0);
			aposta.setTaxaSeguro(taxa);
		}
		return apostaAssegurada;
	}

	/**
	 * Altera a ordenação dos cenários
	 * 
	 * @param ordem
	 *            é a parâmetro de ordenação
	 */
	public void alterarOrdem(String ordem) {
		ordemExcecoes("Erro ao alterar ordem: ", ordem);
		if (ordem.equalsIgnoreCase("Apostas")) {
			ordenaCenariosApostas();
		} else if (ordem.equalsIgnoreCase("Nome")) {
			ordenaCenariosNome();
		} else if (ordem.equalsIgnoreCase("Cadastro")) {
			ordenaCenariosCadastro();
		}

	}

	public String exibirCenarioOrdenado(int numCenario) {
		cenarioInvalidoExcecoes("Erro na consulta de cenario ordenado: ", numCenario);
		Cenario meuCenario = getCenarioOrdenado(numCenario);
		return Integer.toString(getNumCenario(meuCenario)) + " - " + meuCenario.toString();
	}

	public void ordenaCenariosNome() {
		Comparator<Cenario> ordenaCenarioNome = new OrdenaCenarioNome();
		cenariosOrdenados.sort(ordenaCenarioNome);
	}

	public void ordenaCenariosApostas() {
		Comparator<Cenario> ordenaCenarioApostas = new OrdenaCenarioApostas();
		cenariosOrdenados.sort(ordenaCenarioApostas);
	}
	
	public void ordenaCenariosCadastro() {
		cenariosOrdenados.clear();
		for (Cenario cenario : listaCenarios) {
			cenariosOrdenados.add(cenario);
		}
	}

	/**
	 * Verifica se o cenário é válido a partir de seu número
	 * 
	 * @param representacao
	 *            é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario
	 *            é o número de identificação do cenário
	 */
	public void cenarioInvalidoExcecoes(String representacao, int cenario) {
		if (cenario <= 0) {
			throw new IllegalArgumentException(representacao + "Cenario invalido");
		}
		if (cenario > listaCenarios.size() || listaCenarios.size() == 0) {
			throw new IllegalArgumentException(representacao + "Cenario nao cadastrado");
		}
	}

	/**
	 * Verifica se um cenário já está fechado e lança exceção
	 * 
	 * @param representacao
	 *            é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario
	 *            é o número de identificação do cenário
	 */
	public void cenarioFechadoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (!meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ja esta fechado");
		}
	}

	/**
	 * Verifica se um cenário ainda está aberto e lança exceção
	 * 
	 * @param representacao
	 *            é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario
	 *            é o número de identificação do cenário
	 */
	public void cenarioAbertoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ainda esta aberto");
		}
	}

	/**
	 * Verifica se a aposta é válida e lança exceção se não for
	 * 
	 * @param representacao
	 *            é o texto que referencia o método em que foi lançado a exceção
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta em centavos
	 * @param previsao
	 *            é a previsão da aposta
	 */
	public void apostaExcecoes(String representacao, String apostador, int valor, String previsao) {
		if (apostador == null || apostador.trim().equals("")) {
			throw new NullPointerException(representacao + "Apostador nao pode ser vazio ou nulo");
		}
		if (valor <= 0) {
			throw new IllegalArgumentException(representacao + "Valor nao pode ser menor ou igual a zero");
		}
		if (previsao == null || previsao.trim().equals("")) {
			throw new IllegalArgumentException(representacao + "Previsao nao pode ser vazia ou nula");
		}
		if (!(previsao.equalsIgnoreCase("VAI ACONTECER") || previsao.equalsIgnoreCase("N VAI ACONTECER"))) {
			throw new IllegalArgumentException(representacao + "Previsao invalida");
		}
	}

	public void ordemExcecoes(String representacao, String ordem) {
		if (ordem == null || ordem.trim().equals("")) {
			throw new NullPointerException(representacao + "Ordem nao pode ser vazia ou nula");
		}
		if (!(ordem.equalsIgnoreCase("Cadastro") || ordem.equalsIgnoreCase("Nome") || ordem.equalsIgnoreCase("Apostas"))) {
			throw new IllegalArgumentException(representacao + "Ordem invalida");
		}
	}

	public int getCaixa() {
		return caixa;
	}

	public double getTaxa() {
		return taxa;
	}

	public Cenario getCenario(int numCenario) {
		Cenario cenario = listaCenarios.get(numCenario - 1);
		return cenario;
	}

	public Cenario getCenarioOrdenado(int numCenario) {
		Cenario cenario = cenariosOrdenados.get(numCenario - 1);
		return cenario;
	}

	public int getNumCenario(Cenario cenario) {
		return listaCenarios.indexOf(cenario) + 1;
	}

	public int getNumCenarioOrdenado(Cenario cenario) {
		return cenariosOrdenados.indexOf(cenario) + 1;
	}

	public Aposta getAposta(int cenario, int aposta) {
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.getApostaSegura(aposta);
	}

}
