package projeto;

import java.util.Comparator;

public class OrdenaCenarioCadastro implements Comparator<Cenario> {

	@Override
	public int compare(Cenario arg0, Cenario arg1) {
		return arg0.getIdCenario() - arg1.getIdCenario();
	}
	
}
