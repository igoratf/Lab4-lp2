package projeto;
import java.util.ArrayList;

/**
 * Classe correspondente ao cenário, que possuirá uma numeração, uma descrição,
 * um estado e um set de apostas que serão resolvidas
 * 
 * @author Igor Farias
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
	
	public void cadastrarAposta(String apostador, int valor, String previsao) {
		Aposta aposta = new Aposta(apostador, valor, previsao);
		this.apostas.add(aposta);
	}
	
	public int valorTotalDeApostas() {
		int valorTotal = 0;
		for (Aposta aposta : apostas) {
			valorTotal += aposta.getValor();
		}
		return valorTotal;
	}
	
	public int totalDeApostas() {
		return apostas.size();
	}
	
	public String exibeApostas() {
		String retorno = "";
		for (Aposta aposta : apostas) {
			retorno += aposta.toString();
		}
		return retorno;
	}
	
	public void fecharAposta(boolean ocorreu, double taxa) {
		if (ocorreu) {
			this.estado = 2;
		} else {
			this.estado = 1;
		}
		this.caixaCenario = (int) (this.valorTotalApostasPerdedoras() * taxa);
		this.totalRateioCenario = this.valorTotalApostasPerdedoras() - this.caixaCenario;
	}
	
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
