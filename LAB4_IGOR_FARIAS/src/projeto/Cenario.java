package projeto;
import java.util.ArrayList;

/**
 * Classe correspondente ao cenário, que possuirá uma numeração, uma descrição,
 * um estado e uma lista de apostas que serão resolvidas
 * 
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */
public class Cenario {
	private ArrayList<Aposta> apostas = new ArrayList<>();
	private int numeracao;
	private String descricao;
	private int estado;
	private int caixaCenario;
	private int totalRateioCenario;

	public Cenario(int numeracao, String descricao) {
		if (descricao == null) {
			throw new NullPointerException("descricao nula");
		}
		if (descricao.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro de cenario: Descricao nao pode ser vazia");
		}
		if (numeracao <= 0) {
			throw new IllegalArgumentException("numeracao invalida");
		}
		this.numeracao = numeracao;
		this.descricao = descricao;
		this.estado = 0;
	}

	public int getNumeracao() {
		return numeracao;
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
		retorno += getNumeracao() + " - " + getDescricao() + " - " + getEstado();
		return retorno;
	}
	
	/**
	 * Cadastra uma aposta no cenário
	 * @param apostador é o nome do apostador
	 * @param valor é o valor da aposta em centavos
	 * @param previsao é a previsão da aposta
	 */
	public void cadastrarAposta(String apostador, int valor, String previsao) {
		Aposta aposta = new Aposta(apostador, valor, previsao);
		this.apostas.add(aposta);
	}
	
	/**
	 * Calcula o valor total das apostas do cenário
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
	 * @return a quantidade de apostas do cenário
	 */
	public int totalDeApostas() {
		return apostas.size();
	}
	
	/**
	 * Exibe as apostas do cenário
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
	 * @param ocorreu especifica se o cenário ocorrreu ou não
	 * @param taxa é a taxa referente ao sistema
	 */
	public void fecharAposta(boolean ocorreu, double taxa) {
		if (ocorreu) {
			this.estado = 2;
		} else {
			this.estado = 1;
		}
		this.caixaCenario = (int) (this.valorTotalApostasPerdedoras() * taxa);
		this.totalRateioCenario = this.valorTotalApostasPerdedoras() - this.caixaCenario;
	}
	
	/**
	 * Calcula o valor das apostas perdedoras
	 * @return valor total das apostas perdedoras em centavos
	 */
	public int valorTotalApostasPerdedoras() {
		int totalApostasPerdedoras = 0;
		for (Aposta aposta : apostas) {
			if (this.estado == 2) {
				if (aposta.getPrevisao().equalsIgnoreCase("N VAI ACONTECER")) {
					totalApostasPerdedoras += aposta.getValor();
				}
			} else if (this.estado == 1) {
				if (aposta.getPrevisao().equalsIgnoreCase("VAI ACONTECER")) {
					totalApostasPerdedoras += aposta.getValor();
				}
			}
		}
		return totalApostasPerdedoras;
	}
	
	public int getCaixaCenario() {
		return this.caixaCenario;
	}
	
	public int getTotalRateioCenario() {
		return this.totalRateioCenario;
	}

}
