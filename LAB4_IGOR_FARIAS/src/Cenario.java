
public class Cenario {
	private int numeracao;
	private String descricao;
	private String estado;
	
	
	public Cenario(int numeracao, String descricao) {
		this.numeracao = numeracao;
		this.descricao = descricao;
		this.estado = "Não finalizado";
	}
	

	public int getNumeracao() {
		return numeracao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getEstado() {
		String retorno = "";
		switch (estado) {
		case "Finalizado (ocorreu)":
			retorno = "Finalizado (ocorreu)";
			break;
		case "Finalizado (n ocorreu)":
			retorno = "Finalizado (n ocorreu)";
			break;
		case "Não finalizado":
			retorno = "Não finalizado";
			break;
		default:
			retorno = "Estado inválido";
			break;
		}
		return retorno;
		
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
}
