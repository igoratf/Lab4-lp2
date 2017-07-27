package projeto;

import java.util.Comparator;

public class OrdenaCenarioApostas implements Comparator<Cenario> {

	@Override
	public int compare(Cenario o1, Cenario o2) {
		if (o1.totalDeApostas() == o2.totalDeApostas()) {
			return o1.getIdCenario() - o2.getIdCenario();
		}
		return o2.totalDeApostas() - o1.totalDeApostas();
	}

}
