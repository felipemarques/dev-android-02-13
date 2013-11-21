package com.unopar.meucarronovo;

import java.util.List;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unopar.meucarronovo.modelo.Veiculo;

public class ListaVeiculosFragment extends ListFragment {
	public interface ActivityContainer {
		void notifyItemSelected(int position);

		boolean isLargeLayout();
	}
	
	private ActivityContainer _container;
	private int _position;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		List<Veiculo> veiculos = Veiculo.GetAll();
		
		int layoutID = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB 
				? android.R.layout.simple_list_item_activated_1 
				: android.R.layout.simple_list_item_1;
		
		setListAdapter(
			new ArrayAdapter<Veiculo>(
				getActivity(),
				layoutID,
				veiculos));		
		
		if(savedInstanceState != null)
			_position = savedInstanceState.getInt(
					DetalheVeiculoFragment.VEICULO_SELECIONADO, 0);
	}
	
	@Override
	public void onStart() {		
		super.onStart();
		
		if(_container.isLargeLayout()) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			getListView().setItemChecked(_position, true);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {		
		super.onAttach(activity);		

		_container = (ActivityContainer)activity;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(
				DetalheVeiculoFragment.VEICULO_SELECIONADO, _position);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		_position = position;
		
		_container.notifyItemSelected(position);
		
		getListView().setItemChecked(position, true);
	}
}
