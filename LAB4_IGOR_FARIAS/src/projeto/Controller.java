package projeto;

import java.util.ArrayList;
import java.util.Set;

/**
 * Classe controladora que representa o sistema que ir� administrar o
 * cadastramento de cen�rios e apostas e suas resolu��es
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
	 * Cadastra um cen�rio para receber apostas
	 * 
	 * @param descricao
	 *            � a situa��o do cen�rio que poder� acontecer ou n�o
	 * @return o n�mero de identifica��o do cen�rio
	 */
	public int cadastrarCenario(String descricao) {
		int numCenario = indexCenarios + 1;
		Cenario cenario = new Cenario(numCenario, descricao);
		listaCenarios.add(indexCenarios, cenario);
		indexCenarios++;
		return numCenario;
	}

	/**
	 * Exibe informacoes de um cenario
	 * 
	 * @param cenario
	 *            o numero de identificacao do cenario
	 * @return representacao textual do cenario
	 */
	public String exibirCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta de cenario: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.toString().trim();
	}

	/**
	 * Exibe informacoes de todos os cenarios cadastrados
	 * 
	 * @return representacao textual de todos os cenarios cadastrados
	 */
	public String exibirCenarios() {
		String retorno = "";
		for (Cenario cenario : listaCenarios) {
			retorno += cenario.toString();
		}
		return retorno;
	}

	/**
	 * Cadastra uma aposta no cenario especificado
	 * 
	 * @param cenario
	 *            o numero de identificacao do cenario
	 * @param apostador
	 *            o nome do apostador
	 * @param valor
	 *            O valor da aposta em centavos
	 * @param previsao
	 *            Previsao da aposta sobre o cen�rio
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor, String previsao) {
		cenarioInvalidosExcecoes("Erro no cadastro de aposta: ", cenario);
		apostaExcecoes("Erro no cadastro de aposta: ", apostador, valor, previsao);
		Cenario meuCenario = getCenario(cenario);
		Aposta aposta = new Aposta(apostador, valor, previsao);
		meuCenario.getApostas().add(aposta);
	}

	/**
	 * Retorna o valor total das apostas de um cen�rio
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return � o valor total das apostas do cen�rio em centavos
	 */
	public int valorTotalDeApostas(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do valor total de apostas: ", cenario);
		int valor = 0;
		Cenario meuCenario = getCenario(cenario);
		Set<Aposta> apostasCenario = meuCenario.getApostas();
		for (Aposta aposta : apostasCenario) {
			valor += aposta.getValor();
		}
		return valor;
	}

	public int totalDeApostas(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do total de apostas: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		int qtdApostas = meuCenario.getApostas().size();
		return qtdApostas;
	}

	/**
	 * Exibe todas as apostas de um cen�rio
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return representa��o textual de todas as apostas do cen�rio
	 */
	public String exibeApostas(int cenario) {
		Cenario meuCenario = getCenario(cenario);
		Set<Aposta> apostasCenario = meuCenario.getApostas();
		String retorno = "";
		for (Aposta aposta : apostasCenario) {
			retorno += aposta.toString();
		}
		return retorno;
	}

	/**
	 * Finaliza um cen�rio de apostas
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @param ocorreu
	 *            especifica se o cen�rio ocorreu ou n�o
	 */
	public void fecharAposta(int cenario, boolean ocorreu) {
		cenarioInvalidosExcecoes("Erro ao fechar aposta: ", cenario);
		cenarioFechadoExcecao("Erro ao fechar aposta: ", cenario);
		Cenario meuCenario = getCenario(cenario);
		if (ocorreu) {
			meuCenario.setEstado(2);
		} else {
			meuCenario.setEstado(1);
		}
		this.caixa += getCaixaCenario(cenario);

	}

	/**
	 * Retorna o valor total das apostas perdedoras
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return o valor total das apostas perdedoras
	 */
	public int valorTotalApostasPerdedoras(int cenario) {
		Cenario meuCenario = getCenario(cenario);
		Set<Aposta> apostasCenario = meuCenario.getApostas();
		int totalApostasPerdedoras = 0;
		for (Aposta aposta : apostasCenario) {
			if (meuCenario.getEstado().equals("Finalizado (ocorreu)")) {
				if (aposta.getPrevisao().equalsIgnoreCase("N VAI ACONTECER")) {
					totalApostasPerdedoras += aposta.getValor();
				}
			} else if (meuCenario.getEstado().equals("Finalizado (n ocorreu)")) {
				if (aposta.getPrevisao().equalsIgnoreCase("VAI ACONTECER")) {
					totalApostasPerdedoras += aposta.getValor();
				}
			}
		}
		return totalApostasPerdedoras;
	}

	/**
	 * Usa o metodo valorTotalApostasPerdedoras para calcular o valor de um
	 * cenario que sea� destinado ao caixa
	 * 
	 * @param cenario
	 * @return retorna o valor que er� destinado ao caixa em centavos
	 */
	public int getCaixaCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do caixa do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do caixa do cenario: ", cenario);
		int caixaCenario = (int) (valorTotalApostasPerdedoras(cenario) * taxa);
		return caixaCenario;
	}

	/**
	 * Usa os m�todos valorTotalApostasPerdedoras e getCaixaCenario para
	 * retornar o valor total que ser� rateado entre os vencedores
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return valor total a ser rateado entre os vencedores, em centavos
	 */
	public int getTotalRateioCenario(int cenario) {
		cenarioInvalidosExcecoes("Erro na consulta do total de rateio do cenario: ", cenario);
		cenarioAbertoExcecao("Erro na consulta do total de rateio do cenario: ", cenario);
		int totalRateioCenario = valorTotalApostasPerdedoras(cenario) - getCaixaCenario(cenario);
		return totalRateioCenario;
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

	public void cenarioInvalidosExcecoes(String representacao, int cenario) {
		if (cenario <= 0) {
			throw new IllegalArgumentException(representacao + "Cenario invalido");
		}
		if (cenario > listaCenarios.size()) {
			throw new IllegalArgumentException(representacao + "Cenario nao cadastrado");
		}
	}

	

	public void cenarioFechadoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (!meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ja esta fechado");
		}
	}
	
	public void cenarioAbertoExcecao(String representacao, int cenario) {
		Cenario meuCenario = getCenario(cenario);
		if (meuCenario.getEstado().equals("Nao finalizado")) {
			throw new RuntimeException(representacao + "Cenario ainda esta aberto");
		}
	}

	
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
		if (! (previsao.equalsIgnoreCase("VAI ACONTECER") || previsao.equalsIgnoreCase("N VAI ACONTECER"))) {
			throw new IllegalArgumentException (representacao + "Previsao invalida");
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
