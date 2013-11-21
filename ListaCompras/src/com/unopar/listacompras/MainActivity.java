package com.unopar.listacompras;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private BancoDados _bd;
	private List<String> _compras;
	
	private EditText _txtCompra;
	private ListView _lsvItens;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_txtCompra = (EditText)findViewById(R.id.txtCompra);
		_lsvItens = (ListView)findViewById(R.id.lsvItens);
		
		_bd = new BancoDados(this);
		_compras = _bd.recuperaCompras();
		
		criaAdaptador();
		
		_lsvItens.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String item = _compras.get(position);
				
				String mensagem = 
						_bd.excluirCompra(item) 
							? "Compra removida com sucesso."
							: "A compra não foi localizada.";
				Toast.makeText(
						getApplicationContext(), 
						mensagem, 
						Toast.LENGTH_SHORT).show();
				
				_compras.remove(position);
				criaAdaptador();
			}
		});
	}

	private void criaAdaptador() {
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, _compras);
		
		_lsvItens.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onBtnAddClick(View view) {
		String novaCompra = _txtCompra.getText().toString();
		if(_bd.adicionaSeNaoExiste(novaCompra)) {
			_compras.add(novaCompra);
			
			criaAdaptador();
			Toast.makeText(this, "Compra registrada!", Toast.LENGTH_SHORT).show();	
		}
		else
		{
			Toast.makeText(
				this, 
				String.format("A compra %s já foi registrada.", novaCompra), 
				Toast.LENGTH_SHORT).show();
		}
	}
}
