package projeto;

import java.util.Comparator;

public class OrdenaCenarioNome implements Comparator<Cenario> {

	/**
	 * Comparador de cenários que utiliza as descrições para comparação
	 * alfabética
	 */
	@Override
	public int compare(Cenario o1, Cenario o2) {
		String cenario1 = o1.getDescricao();
		String cenario2 = o2.getDescricao();
		if (cenario1.compareTo(cenario2) == 0) {
			return o1.getIdCenario() - o2.getIdCenario();
		}
		return cenario1.compareTo(cenario2);
	}

}
