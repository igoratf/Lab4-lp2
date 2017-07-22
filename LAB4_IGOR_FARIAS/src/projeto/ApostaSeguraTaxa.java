package projeto;

public class ApostaSeguraTaxa extends Aposta {

	public ApostaSeguraTaxa(String apostador, int valor, String previsao, double taxaSeguro, int custo) {
		super(apostador, valor, previsao);
		if (taxaSeguro <= 0) {
			throw new IllegalArgumentException("Erro ao cadastrar aposta assegurada: taxa de seguro nao pode ser menor ou igual a zero");
		}
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
