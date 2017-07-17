package projeto;

public class Seguro {
	private int idApostaSegura;
	private String tipo;
	private int custo;
	private int valorSeguro;
	private double taxaSeguro;
	
	public Seguro(String tipo, int custo) {
		this.tipo = tipo;
		this.custo = custo;
	}
	

	public int getIdApostaSegura() {
		return idApostaSegura;
	}

	public void setIdApostaSegura(int idApostaSegura) {
		this.idApostaSegura = idApostaSegura;
	}

	public String getTipo() {
		return tipo;
	}

	public int getCusto() {
		return custo;
	}
	
	public void setValorSeguro(int valor) {
		this.valorSeguro = valor;
	}
	
	public void setCusto(int custo) {
		this.custo = custo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getValorSeguro() {
		return this.valorSeguro;
	}


	public double getTaxaSeguro() {
		return taxaSeguro;
	}


	public void setTaxaSeguro(double taxaSeguro) {
		this.taxaSeguro = taxaSeguro;
	}
	
	

	
	
}
