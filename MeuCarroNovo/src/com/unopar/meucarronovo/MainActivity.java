package com.unopar.meucarronovo;

import com.unopar.meucarronovo.modelo.Veiculo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity 
	implements ListaVeiculosFragment.ActivityContainer {
	
	private boolean _inLargeLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_inLargeLayout = findViewById(R.id.fragment_single) == null;
		
		if(savedInstanceState != null)
		{
			return;
		}
		
		if(!_inLargeLayout) {
			ListaVeiculosFragment listaFragment = new ListaVeiculosFragment();
			
			getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.fragment_single, listaFragment)
				.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void notifyItemSelected(int position) {
		DetalheVeiculoFragment detalheFragment;
		
		if(_inLargeLayout) {
			detalheFragment = (DetalheVeiculoFragment)
					getSupportFragmentManager()
					.findFragmentById(R.id.fragment_detail);
			detalheFragment.configUI(position);			
		} else {
			Bundle params = new Bundle();
			params.putInt(
					DetalheVeiculoFragment.VEICULO_SELECIONADO, 
					position);
			
			detalheFragment = new DetalheVeiculoFragment();
			detalheFragment.setArguments(params);
			
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			
			transaction.replace(R.id.fragment_single, detalheFragment);
			transaction.addToBackStack(null);
			
			transaction.commit();	
		}
	}

	@Override
	public boolean isLargeLayout() {		
		return _inLargeLayout;
	}

}
