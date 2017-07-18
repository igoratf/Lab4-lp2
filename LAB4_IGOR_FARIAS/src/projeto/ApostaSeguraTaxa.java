package projeto;

public class ApostaSeguraTaxa extends Aposta {

	public ApostaSeguraTaxa(String apostador, int valor, String previsao, double taxaSeguro, int custo) {
		super(apostador, valor, previsao);
		this.setTipo("TAXA");
		this.setTaxaSeguro(taxaSeguro);
	}

	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor() / 100.0) + " - "
				+ this.getPrevisao() + " - " + "ASSEGURADA(" + this.getTipo() + ") - " + this.getTaxaSeguro() + "%";
		return retorno;
	}


}
