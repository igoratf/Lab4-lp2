package projeto;

import java.util.Comparator;

public class OrdenaCenarioApostas implements Comparator<Cenario> {

	@Override
	public int compare(Cenario o1, Cenario o2) {
		int numApostas1 = o1.totalDeApostas();
		int numApostas2 = o1.totalDeApostas();
		return numApostas1 - numApostas2;
	}
	
}
