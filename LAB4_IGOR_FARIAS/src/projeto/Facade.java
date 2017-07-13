package projeto;
/**
 * Classe de fachada, possui m�todos m�todos de uso geral e utiliza o sistema
 * para realizar as a��es
 */
public class Facade {

	protected Controller controller;

	/**
	 * Inicializa o sistema, instanciando o controlador
	 * 
	 * @param caixa
	 *            � o valor inicial do caixa do sistema, em centavos
	 * @param taxa
	 *            � a taxa que ser� cobrada das apostas perdedoras pelo sistema
	 *            para adicionar certo valor ao caixa
	 */
	public void inicializa(int caixa, double taxa) {
		controller = new Controller(caixa, taxa);
	}

	/**
	 * Chama o m�todo cadastrarCenario do controlador para cadastrar um cen�rio
	 * 
	 * @param descricao
	 *            � a poss�vel situa��o futura que o cen�rio descreve
	 * @return retorna o n�mero de identifica��o do cen�rio
	 */
	public int cadastrarCenario(String descricao) {
		return controller.cadastrarCenario(descricao);
	}

	/**
	 * Chama o m�todo exibirCenario do controlador para exibir informa��es de um
	 * cen�rio de apostas a partir de sua numera��o
	 * 
	 * @param cenario
	 *            � a numera��o que identifica o cen�rio
	 * @return retorna uma representa��o textual do cen�rio
	 */
	public String exibirCenario(int cenario) {
		return controller.exibirCenario(cenario);
	}

	/**
	 * Chama o m�todo exibirCenarios do controlador para exibir informa��es de
	 * todos os cen�rios cadastrados
	 * 
	 * @return representa��o textual de todos os cen�rios
	 */
	public String exibirCenarios() {
		return controller.exibirCenarios();
	}

	/**
	 * Chama o m�todo cadastrarAposta do controlador para cadastrar uma aposta
	 * em um determinado cen�rio
	 * 
	 * @param cenario
	 *            � a numera��o que identifica o cen�rio que ir� receber a
	 *            aposta
	 * @param apostador
	 *            � o nome do apostador
	 * @param valor
	 *            � o valor da aposta, em centavos
	 * @param previsao
	 *            � o palpite da aposta para a situa��o futura
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor,
			String previsao) {
		controller.cadastrarAposta(cenario, apostador, valor, previsao);
	}

	/**
	 * Chama o m�todo valorTotalDasApostas do controlador para retornar o valor
	 * total das apostas de um cen�rio, identificado por sua numera��o
	 * 
	 * @param cenario
	 *            � a numera��o que identifica o cen�rio
	 * @return o valor total das apostas do cen�rio em centavos
	 */
	public int valorTotalDeApostas(int cenario) {
		return controller.valorTotalDeApostas(cenario);
	}

	/**
	 * Chama o m�todo exibirApostas do controlador para exibir informa��es das
	 * apostas de um cen�rio
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @return representa��o textual das apostas do cen�rio
	 */
	
	public int totalDeApostas(int cenario) {
		return controller.totalDeApostas(cenario);
	}
	public String exibeApostas(int cenario) {
		return controller.exibeApostas(cenario);
	}

	/**
	 * Finaliza um cen�rio de apostas
	 * 
	 * @param cenario
	 *            � o n�mero de identifica��o do cen�rio
	 * @param ocorreu
	 *            determina se o cen�rio ocorreu ou n�o
	 */
	public void fecharAposta(int cenario, boolean ocorreu) {
		controller.fecharAposta(cenario, ocorreu);
	}

	public int getCaixaCenario(int cenario) {
		return controller.getCaixaCenario(cenario);
	}

	public int getTotalRateioCenario(int cenario) {
		return controller.getTotalRateioCenario(cenario);
	}

	public int getCaixa() {
		return controller.getCaixa();
	}

	public double getTaxa() {
		return controller.getTaxa();
	}

}
