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
	 * Exibe informa��es de um cen�rio
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return representa��o textual do cen�rio
	 */
	public String exibirCenario(int cenario) {
		Cenario meuCenario = getCenario(cenario);
		return meuCenario.toString().trim();
	}

	/**
	 * Exibe informa��es de todos os cen�rios cadastrados
	 * 
	 * @return representa��o textual de todos os cen�rios cadastrados
	 */
	public String exibirCenarios() {
		String retorno = "";
		for (Cenario cenario : listaCenarios) {
			retorno += cenario.toString();
		}
		return retorno;
	}

	/**
	 * Cadastra uma aposta no cen�rio especificado
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @param apostador
	 *            � o nome do apostador
	 * @param valor
	 *            � o valor da aposta em centavos
	 * @param previsao
	 *            � a previs�o da aposta sobre o cen�rio
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor, String previsao) {
		if (cenario > listaCenarios.size() || listaCenarios.size() == 0) {
			throw new IndexOutOfBoundsException ("Erro na consulta de cenario: Cenario nao cadastrado");
		}
		if (cenario <= 0) {
			throw new IllegalArgumentException ("Erro no cadastro de aposta: Cenario invalido");
		}
		if (apostador == null || apostador.trim().equals("")) {
			throw new IllegalArgumentException ("Apostador nao pode ser vazio ou nulo");
		}
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
	public int valorTotalDasApostas(int cenario) {
		int valor = 0;
		Cenario meuCenario = getCenario(cenario);
		Set<Aposta> apostasCenario = meuCenario.getApostas();
		for (Aposta aposta : apostasCenario) {
			valor += aposta.getValor();
		}
		return valor;
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
		Cenario meuCenario = getCenario(cenario);
		if (ocorreu) {
			meuCenario.setEstado(2);
		} else {
			meuCenario.setEstado(1);
		}

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
	 * Usa o m�todo valorTotalApostasPerdedoras para calcular o valor de um
	 * cen�rio que ser� destinado ao caixa
	 * 
	 * @param cenario
	 * @return retorna o valor que er� destinado ao caixa em centavos
	 */
	public int getCaixaCenario(int cenario) {
		int caixaCenario = (int) (valorTotalApostasPerdedoras(cenario) * taxa);
		return caixaCenario;
	}

	/**
	 * Usa os m�todos valorTotalApostasPerdedoras e getCaixaCenario para
	 * retornar o valor total que ser� rateado entre os vencedores
	 * 
	 * @param cenario � o n�mero de identifica��o do cen�rio
	 * @return valor total a ser rateado entre os vencedores, em centavos
	 */
	public int getTotalRateioCenario(int cenario) {
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
		if (numCenario <= 0) {
			throw new IllegalArgumentException("Erro na consulta de cenario: Cenario invalido");
		}
		if (numCenario > listaCenarios.size() || listaCenarios.size() == 0) {
			throw new IndexOutOfBoundsException ("Erro na consulta de cenario: Cenario nao cadastrado");
		}
		Cenario cenario = listaCenarios.get(numCenario - 1);
		return cenario;
	}

}
