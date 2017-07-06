import java.util.ArrayList;
import java.util.HashMap;


public class Controller {
	private ArrayList<Cenario> listaCenarios = new ArrayList<Cenario>();
	private HashMap<Integer, Aposta> listaApostas = new HashMap<Integer, Aposta>();
	private int caixa;
	private double taxa;
	
	
	public Controller (int caixa, double taxa) {
		this.caixa = caixa;
		this.taxa = taxa;
	}

	public int getCaixa() {
		return caixa;
	}


	public double getTaxa() {
		return taxa;
	}

	public ArrayList<Cenario> getListaCenarios() {
		return listaCenarios;
	}

	public HashMap<Integer, Aposta> getListaApostas() {
		return listaApostas;
	}

	
	
	
	
	
	
}
