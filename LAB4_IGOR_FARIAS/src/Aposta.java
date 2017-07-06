
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
	
	
}
