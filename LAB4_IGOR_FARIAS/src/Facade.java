import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

public class Facade {
	Controller controller;
	int indexCenarios = 0;
	
	public void inicializa(int caixa, double taxa) {
		controller = new Controller(caixa, taxa);
	}
	
	public int cadastrarCenario(String descricao) {
		int numCenario = indexCenarios + 1;
		Cenario cenario = new Cenario(numCenario, descricao);
		controller.getListaCenarios().add(indexCenarios, cenario);
		indexCenarios++;
		return numCenario;
	}
	
	public String exibirCenario(int cenario) {
		Cenario meuCenario = controller.getListaCenarios().get(cenario-1);
		String retorno = "";
		retorno += meuCenario.getNumeracao() + " - " + meuCenario.getDescricao() + " - " + meuCenario.getEstado() + Utilidades.LN;
		return retorno;
	}
	
	public String exibirCenarios() {
		ArrayList<Cenario> listaCenarios = controller.getListaCenarios();
		String retorno = "";
		for (Cenario cenario : listaCenarios) {
			retorno += exibirCenario(cenario.getNumeracao());
		}
		return retorno;
	}
	
	public void cadastrarAposta(int cenario, String apostador, int valor, String previsao) {
		Aposta aposta = new Aposta(apostador, valor, previsao);
		controller.getMapaApostas().put(cenario, aposta);
	}
	
	public int valorTotalDasApostas(int cenario) {
		Set<Entry<Integer, Aposta>> setApostas = controller.getMapaApostas().entrySet();
		int valor = 0;
		for (Entry<Integer, Aposta> aposta : setApostas) {
			valor += aposta.getValue().getValor();
		}
		return valor;
	}
	
	public String exibeApostas(int cenario) {
		Collection<Aposta> listaApostas = controller.getMapaApostas().values();
		String retorno = "";
		for (Aposta aposta : listaApostas) {
			retorno += aposta.getApostador() + " - " + aposta.getValor()/100 + " - " + aposta.getPrevisao() + Utilidades.LN;
		}
		return retorno;
	}
	
	public void fecharAposta(int cenario, boolean ocorreu) {
		Cenario meuCenario = controller.getListaCenarios().get(cenario-1);
		if (ocorreu) {
			meuCenario.setEstado("Finalizado (ocorreu)");
		}
		else {
			meuCenario.setEstado("Finalizado (n ocorreu)");
		}
		
	}
	
	public int getCaixaCenario(int cenario) {
		// Lembrar de acrescentar Set de Apostas em Cenario e excluir o mapa de apostas do sistema
		
	}

	public int getCaixa() {
		return controller.getCaixa();
	}

	public double getTaxa() {
		return controller.getTaxa();
	}
	
	
}
