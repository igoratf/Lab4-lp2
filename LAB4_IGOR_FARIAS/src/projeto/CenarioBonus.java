package projeto;
/**
 * Classe que estende Cenário e acrescenta o atributo bônus
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */
public class CenarioBonus extends Cenario {
	private int bonus;
	public CenarioBonus(String descricao, int bonus) {
		super(descricao);
		if (bonus <= 0) {
			throw new IllegalArgumentException("Erro no cadastro de cenario: Bonus invalido");
		}
		this.bonus = bonus;
	}
	
	@Override
	public String toString() {
		String retorno = this.getDescricao() + " - " + this.getEstado() + " - " + "R$ " + String.format("%.2f", this.bonus/100.0);
		return retorno;
	}
	
	
	public int getCaixaCenario() {
		return super.getCaixaCenario();
	}
	
	public int getTotalRateioCenario() {
		return super.getTotalRateioCenario() + this.bonus;
	}
	
	public void cadastrarAposta(Aposta aposta) {
		super.cadastrarAposta(aposta);
	}
	
	public int valorTotalDeApostas() {
		return super.valorTotalDeApostas();
	}
	
	public int totalDeApostas() {
		return super.totalDeApostas();
	}
	
	public String exibeApostas() {
		return super.exibeApostas();
	}
	
	public void fecharAposta(boolean ocorreu, double taxa) {
		super.fecharAposta(ocorreu, taxa);
	}
	
	public String getEstado() {
		return super.getEstado();
	}
	
	public String getDescricao() {
		return super.getDescricao();
	}

}
