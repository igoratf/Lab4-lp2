package projeto;

public class ApostaSeguraValor extends ApostaSegura {
	private int valorSeguro;
	
	
	public ApostaSeguraValor(String apostador, int valor, String previsao, int valorSeguro) {
		super(apostador, valor, previsao);
		this.setTipo("VALOR");
		this.valorSeguro = valorSeguro;
	}
	
	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor()/100.0) + " - " + this.getPrevisao() + "ASSEGURADA (" + this.getTipo() + ")" + " - " + "R$ " + String.format("%.2f", this.valorSeguro);
		return retorno;
	}
	
	public int getValorSeguro() {
		return this.valorSeguro;
	}
	
}
