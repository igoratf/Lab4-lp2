package projeto;
import java.util.HashSet;

/**
 * Classe correspondente ao cenário, que possuirá uma numeração, uma descrição,
 * um estado e um set de apostas que serão resolvidas
 * 
 * @author Igor Farias
 *
 */
public class Cenario {
	private HashSet<Aposta> apostas = new HashSet<>();
	private int numeracao;
	private String descricao;
	private int estado;

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
			retorno = "Estado inválido";
			break;
		}
		return retorno;

	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public HashSet<Aposta> getApostas() {
		return apostas;
	}
	
	@Override
	public String toString() {
		String retorno = "";
		retorno += getNumeracao() + " - " + getDescricao() + " - " + getEstado();
		return retorno;
	}
	

}
