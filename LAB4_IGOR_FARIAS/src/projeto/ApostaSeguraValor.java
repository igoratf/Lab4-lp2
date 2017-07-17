package projeto;

public class ApostaSeguraValor extends Aposta {
	public ApostaSeguraValor(String apostador, int valor, String previsao, int valorSeguro, int custo) {
		super(apostador, valor, previsao);
		seguro.setTipo("VALOR");
		seguro.setCusto(custo);
		seguro.setValorSeguro(valorSeguro);
	}

	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor() / 100.0) + " - "
				+ this.getPrevisao() + "ASSEGURADA (" + this.getTipo() + ")" + " - " + "R$ "
				+ String.format("%.2f", seguro.getValorSeguro());
		return retorno;
	}

}
