package projeto;

import java.util.Comparator;

public class OrdenaCenarioCadastro implements Comparator<Cenario> {

	/**
	 * Comparador de cenários que compara a ordem de criação dos cenários pelo
	 * seu ID
	 */
	@Override
	public int compare(Cenario arg0, Cenario arg1) {
		return arg0.getIdCenario() - arg1.getIdCenario();
	}

}
