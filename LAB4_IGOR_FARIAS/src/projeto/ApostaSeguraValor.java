package projeto;

public class ApostaSeguraValor extends Aposta {
	private int idApostaSegura;
	private int valorSeguro;
	private String tipo;
	public ApostaSeguraValor(String apostador, int valor, String previsao, int valorSeguro) {
		super(apostador, valor, previsao);
		this.tipo = "VALOR";
		this.valorSeguro = valorSeguro;
	}
	
	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor()/100.0) + " - " + this.getPrevisao() + "ASSEGURADA (" + this.tipo + ")" + " - " + "R$ " + String.format("%.2f", this.valorSeguro);
		return retorno;
	}
	
	public int getValorSeguro() {
		return this.valorSeguro;
	}
	public String getTipo() {
		return this.tipo;
	}
	
	public int getIdApostaSegura() {
		return this.idApostaSegura;
	}
	
	public void setIdApostaSegura(int id) {
		this.idApostaSegura = id;
	}
	
	
}
