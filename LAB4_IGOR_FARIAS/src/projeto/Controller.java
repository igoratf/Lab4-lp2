package projeto;

import java.util.ArrayList;

/**
 * Classe controladora que representa o sistema que irá administrar o
 * cadastramento de cenários e apostas e suas resoluções
 * 
 * @author Igor Farias
 *
 */

public class Controller {
	private int indexCenarios = 0;
	private ArrayList<Cenario> listaCenarios = new ArrayList<Cenario>();
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
		int numCenario = indexCenarios + 1;
		Cenario cenario = new Cenario(numCenario, descricao);
		listaCenarios.add(indexCenarios, cenario);
		indexCenarios++;
		return numCenario;
	}

	/**
	 * Exibe informações de um cenario
	 * 
	 * @param cenario
	 *            o número de identificação do cenário
	 * @return representação textual do cenário
	 */
	public String exibirCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta de cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.toString().trim();
	}

	/**
	 * Exibe informações de todos os cenarios cadastrados
	 * 
	 * @return representação textual de todos os cenários cadastrados
	 */
	public String exibirCenarios() {
		String retorno = "";
		for (Cenario cenario : listaCenarios) {
			retorno += cenario.toString();
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
		cenarioInvalidosExcecoes("Erro no cadastro de aposta: ", cenario);
		apostaExcecoes("Erro no cadastro de aposta: ", apostador, valor, previsao);
		Cenario meuCenario = getCenario(cenario);
		meuCenario.cadastrarAposta(apostador, valor, previsao);
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
		cenarioInvalidosExcecoes("Erro na consulta do valor total de apostas: ", cenario);
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
		cenarioInvalidosExcecoes("Erro na consulta do total de apostas: ", cenario);
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
		cenarioInvalidosExcecoes("Erro ao fechar aposta: ", cenario);
		cenarioFechadoExcecao("Erro ao fechar aposta: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		meuCenario.fecharAposta(ocorreu, this.taxa);
		this.caixa += meuCenario.getCaixaCenario();
	}

	/**
	 * Retorna o valor total das apostas perdedoras a partir do método
	 * valorTotalApostasPerdedoras de Cenario
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return o valor total das apostas perdedoras
	 */
	public int valorTotalApostasPerdedoras(int cenario) {
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.valorTotalApostasPerdedoras();
	}

	/**
	 * Usa o metodo valorTotalApostasPerdedoras para calcular o valor de um
	 * cenario que será destinado ao caixa
	 * 
	 * @param cenario
	 * @return retorna o valor que será destinado ao caixa em centavos
	 */
	public int getCaixaCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do caixa do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do caixa do cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.getCaixaCenario();
	}

	/**
	 * Usa os métodos valorTotalApostasPerdedoras e getCaixaCenario para
	 * retornar o valor total que será rateado entre os vencedores
	 * 
	 * @param cenario
	 *            é o número de identificação do cenário
	 * @return valor total a ser rateado entre os vencedores, em centavos
	 */
	public int getTotalRateioCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do total de rateio do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do total de rateio do cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.getTotalRateioCenario();
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
	
	/**
	 * Verifica se o cenário é válido a partir de seu número
	 * @param representacao é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario é o número de identificação do cenário
	 */
	public void cenarioInvalidosExcecoes(String representacao, int cenario) {
		if (cenario <= 0) {
			throw new IllegalArgumentException(representacao + "Cenario invalido");
		}
		if (cenario > listaCenarios.size() || listaCenarios.size() == 0) {
			throw new IllegalArgumentException(representacao + "Cenario nao cadastrado");
		}
	}
	
	/**
	 * Verifica se um cenário já está fechado e lança exceção
	 * @param representacao é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario é o número de identificação do cenário
	 */
	public void cenarioFechadoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (!meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ja esta fechado");
		}
	}
	
	/**
	 * Verifica se um cenário ainda está aberto e lança exceção
	 * @param representacao é o texto que referencia o método em que foi lançado a exceção
	 * @param cenario é o número de identificação do cenário
	 */
	public void cenarioAbertoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ainda esta aberto");
		}
	}
	
	/**
	 * Verifica se a aposta é válida e lança exceção se não for
	 * @param representacao é o texto que referencia o método em que foi lançado a exceção
	 * @param apostador é o nome do apostador
	 * @param valor é o valor da aposta em centavos
	 * @param previsao é a previsão da aposta
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + caixa;
		result = prime * result + indexCenarios;
		result = prime * result + ((listaCenarios == null) ? 0 : listaCenarios.hashCode());
		long temp;
		temp = Double.doubleToLongBits(taxa);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Controller other = (Controller) obj;
		if (caixa != other.caixa)
			return false;
		if (indexCenarios != other.indexCenarios)
			return false;
		if (listaCenarios == null) {
			if (other.listaCenarios != null)
				return false;
		} else if (!listaCenarios.equals(other.listaCenarios))
			return false;
		if (Double.doubleToLongBits(taxa) != Double.doubleToLongBits(other.taxa))
			return false;
		return true;
	}

}
