package projeto;

public class ApostaSeguraValor extends ApostaSegura {
	protected int valorSeguro;

	public ApostaSeguraValor(String apostador, int valor, String previsao, int valorSeguro, int custo) {
		super(apostador, valor, previsao, custo);
		this.tipo = "VALOR";
		this.valorSeguro = valorSeguro;
	}

	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor() / 100.0) + " - "
				+ this.getPrevisao() + "ASSEGURADA (" + this.getTipo() + ")" + " - " + "R$ "
				+ String.format("%.2f", this.valorSeguro);
		return retorno;
	}

	public int getValorSeguro() {
		return this.valorSeguro;
	}

	public int getCusto() {
		return super.getCusto();
	}

	public int getIdApostaSegura() {
		return super.getIdApostaSegura();
	}

	public int getValor() {
		return super.getValor();
	}

}
