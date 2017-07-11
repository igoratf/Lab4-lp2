public class Facade {
	/**
	 * Classe de fachada, possui métodos métodos de uso geral e utiliza o
	 * sistema para realizar as ações
	 */
	protected Controller controller;

	/**
	 * Inicializa o sistema, instanciando o controlador
	 * 
	 * @param caixa
	 *            é o valor inicial do caixa do sistema, em centavos
	 * @param taxa
	 *            é a taxa que será cobrada das apostas perdedoras pelo sistema
	 *            para adicionar certo valor ao caixa
	 */
	public void inicializa(int caixa, double taxa) {
		controller = new Controller(caixa, taxa);
	}

	/**
	 * Chama o método cadastrarCenario do controlador para cadastrar um cenário
	 * 
	 * @param descricao
	 *            é a possível situação futura que o cenário descreve
	 * @return retorna o número de identificação do cenário
	 */
	public int cadastrarCenario(String descricao) {
		return controller.cadastrarCenario(descricao);
	}

	/**
	 * Chama o método exibirCenario do controlador para exibir informações de um
	 * cenário de apostas a partir de sua numeração
	 * 
	 * @param cenario
	 *            é a numeração que identifica o cenário
	 * @return retorna uma representação textual do cenário
	 */
	public String exibirCenario(int cenario) {
		return controller.exibirCenario(cenario);
	}

	/**
	 * Chama o método exibirCenarios do controlador para exibir informações de
	 * todos os cenários cadastrados
	 * 
	 * @return representação textual de todos os cenários
	 */
	public String exibirCenarios() {
		return controller.exibirCenarios();
	}

	/**
	 * Chama o método cadastrarAposta do controlador para cadastrar uma aposta
	 * em um determinado cenário
	 * 
	 * @param cenario
	 *            é a numeração que identifica o cenário que irá receber a
	 *            aposta
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta, em centavos
	 * @param previsao
	 *            é o palpite da aposta para a situação futura
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor, String previsao) {
		controller.cadastrarAposta(cenario, apostador, valor, previsao);
	}

	/**
	 * Chama o método valorTotalDasApostas do controlador para retornar o valor
	 * total das apostas de um cenário, identificado por sua numeração
	 * 
	 * @param cenario
	 *            é a numeração que identifica o cenário
	 * @return o valor total das apostas do cenário em centavos
	 */
	public int valorTotalDasApostas(int cenario) {
		return controller.valorTotalDasApostas(cenario);
	}

	/**
	 * Chama o método exibirApostas do controlador para exibir informações das
	 * apostas de um cenário
	 * 
	 * @param cenario é o número de identificação do cenário
	 * @return representação textual das apostas do cenário
	 */
	public String exibeApostas(int cenario) {
		return controller.exibeApostas(cenario);
	}
	
	/**
	 * Finaliza um cenário de apostas
	 * @param cenario é o número de identificação do cenário
	 * @param ocorreu determina se o cenário ocorreu ou não
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
