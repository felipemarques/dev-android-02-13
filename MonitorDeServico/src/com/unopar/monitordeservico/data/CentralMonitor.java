package com.unopar.monitordeservico.data;

import java.util.ArrayList;
import java.util.List;

public class CentralMonitor {
	private List<ServicoMonitor> _itens;
	
	public CentralMonitor() {
		_itens = new ArrayList<ServicoMonitor>();
		
		_itens.add(new ServicoMonitor("Servico 1"));
		_itens.add(new ServicoMonitor("Servico 2"));
		_itens.add(new ServicoMonitor("Servico 3"));
		_itens.add(new ServicoMonitor("Servico 4"));
	}
	
	public List<ServicoMonitor> get() {
		return _itens;
	}
	
	public void refresh() {
		for(ServicoMonitor item : _itens) {
			item.verificar();
		}
	}

	public String getOnlineAsString() {
		StringBuilder builder = new StringBuilder();
		
		for(ServicoMonitor sm : _itens) {
			if(sm.estaOn())
			{
				builder.append(sm.getNome() + ", ");
			}
		}
		
		return builder.toString();
	}

	public String getOfflineAsString() {
		StringBuilder builder = new StringBuilder();
		
		for(ServicoMonitor sm : _itens) {
			if(!sm.estaOn())
			{
				builder.append(sm.getNome() + ", ");
			}
		}
		
		return builder.toString();
	}
	

}
