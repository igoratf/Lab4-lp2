package projeto;

public abstract class ApostaSegura extends Aposta {
	protected int idApostaSegura;
	protected String tipo;
	protected int custo;
	
	public ApostaSegura(String apostador, int valor, String previsao, int custo) {
		super(apostador, valor, previsao);
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

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public int getCusto() {
		return custo;
	}

	
	
}
