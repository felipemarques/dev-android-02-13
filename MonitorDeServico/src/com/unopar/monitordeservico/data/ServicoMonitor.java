package com.unopar.monitordeservico.data;

import java.util.Random;

public class ServicoMonitor {
	private static Random _random = new Random();
	
	private String _nome;
	private boolean _status;
	
	public ServicoMonitor(String nome) {
		_nome = nome;
	}
	
	public String getNome() {
		return _nome;
	}
	
	public boolean estaOn() {
		return _status;
	}
	
	public void verificar() {
		_status = _random.nextBoolean();
	}
}
