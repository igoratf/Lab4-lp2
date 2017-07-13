package projeto;
/**
 * Classe correspondente a uma aposta, que possui o nome do apostador, um valor
 * associado em centavos e a previs�o que ir� determinar seu resultado no
 * cen�rio
 * 
 * @author Igor Farias
 *
 */
public class Aposta {
	private String apostador;
	private int valor;
	private String previsao;

	public Aposta(String apostador, int valor, String previsao) {
		if (apostador == null || previsao == null) {
			throw new NullPointerException("apostador ou previsao nulos");
		}
		if (apostador.trim().equals("") || previsao.trim().equals("")) {
			throw new IllegalArgumentException("apostador ou previsao vazios");
		}
		if (valor <= 0) {
			throw new IllegalArgumentException("valor invalido");
		}
		if (! (previsao.trim().equalsIgnoreCase("vai acontecer") || previsao.trim().equalsIgnoreCase("n vai acontecer"))) {
			throw new IllegalArgumentException("previsao invalida");
		}
		this.apostador = apostador;
		this.valor = valor;
		this.previsao = previsao;
	}

	public String getApostador() {
		return apostador;
	}

	public int getValor() {
		return valor;
	}

	public String getPrevisao() {
		return previsao;
	}
	
	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor()/100.0) + " - " + this.getPrevisao()
		+ Utilidades.LN;
		return retorno;
	}

}