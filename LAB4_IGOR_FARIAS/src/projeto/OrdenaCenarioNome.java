package projeto;

import java.util.Comparator;

public class OrdenaCenarioNome implements Comparator<Cenario> {

	@Override
	public int compare(Cenario o1, Cenario o2) {
		String cenario1 = o1.getDescricao();
		String cenario2 = o2.getDescricao();
		return cenario1.compareTo(cenario2);
	}
	
}
