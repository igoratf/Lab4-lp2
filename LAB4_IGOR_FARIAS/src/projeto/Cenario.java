﻿package projeto;

import java.util.ArrayList;

/**
 * Classe correspondente ao cenário, que possuirá uma numeração, uma descrição,
 * um estado e uma lista de apostas que serão resolvidas
 * 
 * @author Igor Farias - igor.farias@ccc.ufcg.edu.br
 *
 */
public class Cenario {
	protected ArrayList<Aposta> apostas = new ArrayList<>();
	protected String descricao;
	protected int estado;
	protected int caixaCenario;
	protected int totalRateioCenario;
	protected int valorTotalSeguros;

	public Cenario(String descricao) {
		if (descricao == null) {
			throw new NullPointerException("Erro no cadastro de cenario: Descricao nao pode ser nula");
		}
		if (descricao.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro de cenario: Descricao nao pode ser vazia");
		}
		this.descricao = descricao;
		this.estado = 0;
		this.caixaCenario = 0;
	}


	/**
	 * Cadastra uma aposta no cenário, se a aposta for assegurada, atribui um
	 * número de identificação
	 * 
	 * @param apostador
	 *            é o nome do apostador
	 * @param valor
	 *            é o valor da aposta em centavos
	 * @param previsao
	 *            é a previsão da aposta
	 */
	public void cadastrarAposta(Aposta aposta) {
		this.apostas.add(aposta);
		}

	/**
	 * Calcula o valor total das apostas do cenário
	 * 
	 * @return valor das apostas em centavos
	 */
	public int valorTotalDeApostas() {
		int valorTotal = 0;
		for (Aposta aposta : apostas) {
			valorTotal += aposta.getValor();
		}
		return valorTotal;
	}

	/**
	 * Verifica a quantidade de apostas do cenário
	 * 
	 * @return a quantidade de apostas do cenário
	 */
	public int totalDeApostas() {
		return apostas.size();
	}

	/**
	 * Exibe as apostas do cenário
	 * 
	 * @return representação textual das apostas do cenário
	 */
	public String exibeApostas() {
		String retorno = "";
		for (Aposta aposta : apostas) {
			retorno += aposta.toString();
		}
		return retorno;
	}

	/**
	 * Fecha o cenário de apostas
	 * 
	 * @param ocorreu
	 *            especifica se o cenário ocorreu ou não
	 * @param taxa
	 *            é a taxa referente ao sistema
	 */
	public void fecharAposta(boolean ocorreu, double taxa) {
		this.valorTotalSeguros = 0;
		int valorApostasPerdidas = 0;
		if (ocorreu) {
			this.estado = 2;
		} else {
			this.estado = 1;
		}

		for (Aposta aposta : apostas) {
			if (this.estado == 2) {
				if (aposta.getPrevisao().equalsIgnoreCase("N VAI ACONTECER")) {
					valorApostasPerdidas += aposta.getValor();
					caixaCenario += aposta.getValor() * taxa;
					if (aposta instanceof ApostaSeguraValor) {
						valorTotalSeguros += ((ApostaSeguraValor) aposta).getValorSeguro();
					}
					else if (aposta instanceof ApostaSeguraTaxa) {
						valorTotalSeguros += aposta.getValor() * ((ApostaSeguraTaxa) aposta).getTaxaSeguro();
					}
				}
			}

			else if (this.estado == 1) {
				if (aposta.getPrevisao().equalsIgnoreCase("VAI ACONTECER")) {
					valorApostasPerdidas += aposta.getValor();
					caixaCenario += aposta.getValor() * taxa;
					if (aposta instanceof ApostaSeguraValor) {
						valorTotalSeguros += ((ApostaSeguraValor) aposta).getValorSeguro();
					}
					else if (aposta instanceof ApostaSeguraTaxa) {
						valorTotalSeguros += ((ApostaSeguraTaxa) aposta).getValor() * ((ApostaSeguraTaxa) aposta).getTaxaSeguro();
					}
				}
			}
		}
		totalRateioCenario = valorApostasPerdidas - caixaCenario;

	}
	
	/**
	 * Cadastra no cenário uma aposta assegurada por valor
	 * @param aposta é a aposta assegurada por valor
	 * @return número de identificação da aposta
	 */
	public int cadastrarApostaSeguraValor(ApostaSeguraValor aposta) {
		this.apostas.add(aposta);
		aposta.setIdApostaSegura(apostas.indexOf(aposta));
		return aposta.getIdApostaSegura();
	}
	
	public int cadastrarApostaSeguraTaxa(ApostaSeguraTaxa aposta) {
		this.apostas.add(aposta);
		aposta.setIdApostaSegura(apostas.indexOf(aposta));
		return aposta.getIdApostaSegura();
	}
	public String getDescricao() {
		return descricao;
	}
	
	public String getEstado() {
		String retorno = "";
		switch (estado) {
		case 2:
			retorno = "Finalizado (ocorreu)";
			break;
		case 1:
			retorno = "Finalizado (n ocorreu)";
			break;
		case 0:
			retorno = "Nao finalizado";
			break;
		default:
			retorno = "Estado invalido";
			break;
		}
		return retorno;
		
	}
	
	@Override
	public String toString() {
		String retorno = "";
		retorno += getDescricao() + " - " + getEstado();
		return retorno;
	}
	
	
	public int getCaixaCenario() {
		return this.caixaCenario;
	}

	public int getTotalRateioCenario() {
		return this.totalRateioCenario;
	}
	
	public ApostaSegura getApostaSegura(int id) {
		return ((ApostaSegura) apostas.get(id));
	}
	
	public int getValorTotalSeguros() {
		return this.valorTotalSeguros;
	}
}
