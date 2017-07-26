package projeto;

import java.util.Comparator;

public class OrdenaCenarioApostas implements Comparator<Cenario> {

	@Override
	public int compare(Cenario o1, Cenario o2) {
		int numApostas1 = o1.totalDeApostas();
		int numApostas2 = o1.totalDeApostas();
		if (numApostas1 - numApostas2 == 0) {
			return o1.getIdCenario() - o2.getIdCenario();
		}
		return numApostas1 - numApostas2;
	}
	
}
