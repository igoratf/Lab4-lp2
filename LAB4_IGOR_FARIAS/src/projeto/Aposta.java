package projeto;
/**
 * Classe correspondente a uma aposta, que possui o nome do apostador, um valor
 * associado em centavos e a previsão que irá determinar seu resultado no
 * cenário
 * 
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */
public class Aposta {
	private String apostador;
	private int valor;
	private String previsao;

	public Aposta(String apostador, int valor, String previsao) {
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
