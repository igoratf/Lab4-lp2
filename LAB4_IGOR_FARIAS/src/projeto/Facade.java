package projeto;
/**
 * Classe de fachada, possui mÃ©todos de uso geral e utiliza o sistema
 * para realizar as aÃ§Ãµes
 */
public class Facade {

	protected Controller controller;

	/**
	 * Inicializa o sistema, instanciando o controlador
	 * 
	 * @param caixa
	 *            Ã© o valor inicial do caixa do sistema, em centavos
	 * @param taxa
	 *            Ã© a taxa que serÃ¡ cobrada das apostas perdedoras pelo sistema
	 *            para adicionar certo valor ao caixa
	 */
	public void inicializa(int caixa, double taxa) {
		controller = new Controller(caixa, taxa);
	}

	/**
	 * Chama o mÃ©todo cadastrarCenario do controlador para cadastrar um cenÃ¡rio
	 * 
	 * @param descricao
	 *            Ã© a possÃ­vel situaÃ§Ã£o futura que o cenÃ¡rio descreve
	 * @return retorna o nÃºmero de identificaÃ§Ã£o do cenÃ¡rio
	 */
	public int cadastrarCenario(String descricao) {
		return controller.cadastrarCenario(descricao);
	}

	/**
	 * Chama o mÃ©todo exibirCenario do controlador para exibir informaÃ§Ãµes de um
	 * cenÃ¡rio de apostas a partir de sua numeraÃ§Ã£o
	 * 
	 * @param cenario
	 *            Ã© a numeraÃ§Ã£o que identifica o cenÃ¡rio
	 * @return retorna uma representaÃ§Ã£o textual do cenÃ¡rio
	 */
	public String exibirCenario(int cenario) {
		return controller.exibirCenario(cenario);
	}

	/**
	 * Chama o mÃ©todo exibirCenarios do controlador para exibir informaÃ§Ãµes de
	 * todos os cenÃ¡rios cadastrados
	 * 
	 * @return representaÃ§Ã£o textual de todos os cenÃ¡rios
	 */
	public String exibirCenarios() {
		return controller.exibirCenarios();
	}

	/**
	 * Chama o mÃ©todo cadastrarAposta do controlador para cadastrar uma aposta
	 * em um determinado cenÃ¡rio
	 * 
	 * @param cenario
	 *            Ã© a numeraÃ§Ã£o que identifica o cenÃ¡rio que irÃ¡ receber a
	 *            aposta
	 * @param apostador
	 *            Ã© o nome do apostador
	 * @param valor
	 *            Ã© o valor da aposta, em centavos
	 * @param previsao
	 *            Ã© o palpite da aposta para a situaï¿½ï¿½o futura
	 */
	public void cadastrarAposta(int cenario, String apostador, int valor,
			String previsao) {
		controller.cadastrarAposta(cenario, apostador, valor, previsao);
	}

	/**
	 * Chama o mÃ©todo valorTotalDasApostas do controlador para retornar o valor
	 * total das apostas de um cenÃ¡rio, identificado por sua numeraÃ§Ã£o
	 * 
	 * @param cenario
	 *            Ã© a numeraÃ§Ã£o que identifica o cenÃ¡rio
	 * @return o valor total das apostas do cenÃ¡rio em centavos
	 */
	public int valorTotalDeApostas(int cenario) {
		return controller.valorTotalDeApostas(cenario);
	}

	/**
	 * Chama o mÃ©todo exibirApostas do controlador para exibir informaÃ§Ãµes das
	 * apostas de um cenÃ¡rio
	 * 
	 * @param cenario
	 *            Ã© o nÃºmero de identificaÃ§Ã£o do cenÃ¡rio
	 * @return representaÃ§Ã£o textual das apostas do cenÃ¡rio
	 */
	
	public int totalDeApostas(int cenario) {
		return controller.totalDeApostas(cenario);
	}
	public String exibeApostas(int cenario) {
		return controller.exibeApostas(cenario);
	}

	/**
	 * Finaliza um cenÃ¡rio de apostas
	 * 
	 * @param cenario
	 *            Ã© o nÃºmero de identificaÃ§Ã£o do cenÃ¡rio
	 * @param ocorreu
	 *            determina se o cenÃ¡rio ocorreu ou nÃ£o
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
