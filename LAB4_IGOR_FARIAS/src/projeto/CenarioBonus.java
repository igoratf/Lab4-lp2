package projeto;

public class CenarioBonus extends Cenario {
	private int bonus;
	public CenarioBonus(int numeracao, String descricao, int bonus) {
		super(numeracao, descricao);
		this.bonus = bonus;
	}
}
