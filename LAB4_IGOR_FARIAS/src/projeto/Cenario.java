package projeto;

import java.util.ArrayList;

/**
 * Classe correspondente ao cenário, que possuirá uma numeração, uma descrição,
 * um estado e uma lista de apostas que serão resolvidas
 * 
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */
public class Cenario implements Comparable<Cenario> {
	private int idCenario;
	private ArrayList<Aposta> apostas = new ArrayList<>();
	private String descricao;
	private int estado;
	private int caixaCenario;
	private int totalRateioCenario;
	private int valorTotalSeguros;

	public Cenario(String descricao) {
		if (descricao == null) {
			throw new NullPointerException("Erro no cadastro de cenario: Descricao nao pode ser nula");
		}
		if (descricao.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro de cenario: Descricao nao pode ser vazia");
		}
		this.descricao = descricao;
		this.estado = 0;
		this.caixaCenario = 0;
	}

	/**
	 * Cadastra uma aposta no cenário, se a aposta for assegurada, atribui um
	 * número de identificação
	 * 
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta em centavos
	 * @param previsao
	 *            é a previsão da aposta
	 */
	public void cadastrarAposta(String apostador, int valor, String previsao) {
		Aposta aposta = new Aposta(apostador, valor, previsao);
		this.apostas.add(aposta);
	}

	/**
	 * Calcula o valor total das apostas do cenário
	 * 
	 * @return valor das apostas em centavos
	 */
	public int valorTotalDeApostas() {
		int valorTotal = 0;
		for (Aposta aposta : apostas) {
			valorTotal += aposta.getValor();
		}
		return valorTotal;
	}

	/**
	 * Verifica a quantidade de apostas do cenário
	 * 
	 * @return a quantidade de apostas do cenário
	 */
	public int totalDeApostas() {
		return apostas.size();
	}

	/**
	 * Exibe as apostas do cenário
	 * 
	 * @return representação textual das apostas do cenário
	 */
	public String exibeApostas() {
		String retorno = "";
		for (Aposta aposta : apostas) {
			retorno += aposta.toString();
		}
		return retorno;
	}

	/**
	 * Fecha o cenário de apostas
	 * 
	 * @param ocorreu
	 *            especifica se o cenário ocorreu ou não
	 * @param taxa
	 *            é a taxa referente ao sistema
	 */
	public void fecharAposta(boolean ocorreu, double taxa) {
		this.valorTotalSeguros = 0;
		int valorApostasPerdidas = 0;
		if (ocorreu) {
			this.estado = 2;
		} else {
			this.estado = 1;
		}

		for (Aposta aposta : apostas) {
			if ((this.estado == 2 && aposta.getPrevisao().equalsIgnoreCase("N VAI ACONTECER"))
					|| (this.estado == 1 && aposta.getPrevisao().equalsIgnoreCase("VAI ACONTECER"))) {
				valorApostasPerdidas += aposta.getValor();
				if (aposta.getValorSeguro() > 0) {
					valorTotalSeguros += aposta.getValorSeguro();
				} else if (aposta.getTaxaSeguro() > 0) {
					valorTotalSeguros += aposta.getValor() * aposta.getTaxaSeguro();
				}
			}
		}
		/*
		 * Calcula o valor destinado ao caixa de cenário a partir da taxa
		 * recebida do sistema e do valor das apostas perdidas
		 */
		caixaCenario = (int) (valorApostasPerdidas * taxa);
		/*
		 * Calcula o valor destinado ao rateio entre os vencedores a partir do
		 * valor das apostas perdidas e do valor destinado ao caixa
		 */
		totalRateioCenario = valorApostasPerdidas - caixaCenario;
	}

	/**
	 * Cadastra no cenário uma aposta assegurada por valor
	 * 
	 * @param aposta
	 *            é a aposta assegurada por valor
	 * @return número de identificação da aposta
	 */
	public int cadastrarApostaSeguraValor(String apostador, int valor, String previsao, int valorSeguro, int custo) {
		ApostaSeguraValor apostaSeguraVal = new ApostaSeguraValor(apostador, valor, previsao, valorSeguro, custo);
		this.apostas.add(apostaSeguraVal);
		return this.apostas.size();
	}

	/**
	 * Cadastra no cenário uma aposta assegurada por taxa
	 * 
	 * @param aposta
	 *            é a aposta assegurada por taxa
	 * @return número de identificação da aposta
	 */
	public int cadastrarApostaSeguraTaxa(String apostador, int valor, String previsao, double taxa, int custo) {
		ApostaSeguraTaxa apostaSeguraTaxa = new ApostaSeguraTaxa(apostador, valor, previsao, taxa, custo);
		this.apostas.add(apostaSeguraTaxa);
		return this.apostas.size();
	}

	/**
	 * Altera o tipo de seguro de uma aposta para assegurada por valor
	 * 
	 * @param apostaAssegurada
	 *            é a identificação da aposta assegurada
	 * @param valor
	 *            é o valor do seguro
	 * @return identificação da aposta
	 */
	public int alterarSeguroValor(int apostaAssegurada, int valor) {
		Aposta aposta = getApostaSegura(apostaAssegurada);
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
	 * @param apostaAssegurada
	 *            é a identificação da aposta assegurada
	 * @param taxa
	 *            é a taxa de seguro da aposta
	 * @return identificação da aposta
	 */
	public int alterarSeguroTaxa(int apostaAssegurada, double taxa) {
		Aposta aposta = getApostaSegura(apostaAssegurada);
		if (aposta.getTipo().equalsIgnoreCase("VALOR")) {
			aposta.setTipo("TAXA");
			aposta.setValorSeguro(0);
			aposta.setTaxaSeguro(taxa);
		}
		return apostaAssegurada;
	}

	/**
	 * Compara os cenários pela descrição em ordem lexicográfica
	 */
	@Override
	public int compareTo(Cenario o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

	public String getDescricao() {
		return descricao;
	}

	public String getEstado() {
		String retorno = "";
		switch (estado) {
		case 2:
			retorno = "Finalizado (ocorreu)";
			break;
		case 1:
			retorno = "Finalizado (n ocorreu)";
			break;
		case 0:
			retorno = "Nao finalizado";
			break;
		default:
			retorno = "Estado invalido";
			break;
		}
		return retorno;

	}

	@Override
	public String toString() {
		String retorno = "";
		retorno += getDescricao() + " - " + getEstado();
		return retorno;
	}

	public int getCaixaCenario() {
		return this.caixaCenario;
	}

	public int getTotalRateioCenario() {
		return this.totalRateioCenario;
	}

	public Aposta getApostaSegura(int id) {
		return apostas.get(id - 1);
	}

	public int getValorTotalSeguros() {
		return this.valorTotalSeguros;
	}

	public int getIdCenario() {
		return idCenario;
	}

	public void setIdCenario(int idCenario) {
		this.idCenario = idCenario;
	}

}
