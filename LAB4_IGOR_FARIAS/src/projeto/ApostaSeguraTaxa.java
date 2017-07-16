package projeto;

public class ApostaSeguraTaxa extends ApostaSegura {
	private double taxaSeguro;
	
	public ApostaSeguraTaxa (String apostador, int valor, String previsao, double taxa, int custo) {
		super(apostador, valor, previsao, custo);
		this.setTipo("TAXA");
		this.taxaSeguro = taxa;
	}
	
	@Override
	public String toString() {
		String retorno = this.getApostador() + " - " + "R$" + String.format("%.2f", this.getValor()/100.0) + " - " + this.getPrevisao() + " - " + "ASSEGURADA(" + this.getTipo() + ") - " + this.taxaSeguro + "%";
		return retorno;
	}


	public double getTaxaSeguro() {
		return taxaSeguro;
	}
	
	public void setTaxaSeguro(double taxa) {
		this.taxaSeguro = taxa;
	}
	
	public int getCusto() {
		return super.getCusto();
	}
	
	public int getIdApostaSegura() {
		return super.getIdApostaSegura();
	}
	
	
}
