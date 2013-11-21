package com.unopar.projetoteste;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        if(MinhaAplicacao.get().emSessao())
        {
        	
        }
        
        
        Log.i("Info", "Atividade Criada");
    }
    
    public void abrirSegundaAtividadeClick(View view) {
    	Intent abrirAtividade = new Intent(this, SegundaActivity.class);
    	startActivity(abrirAtividade);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {    	
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	String valorArmazenado = savedInstanceState
    			.getString("VALOR_INTERNO");
    	
    	Log.i("Info", "Estado da atividade restaurado");
    }
    
    @Override
    protected void onStart() {    	
    	super.onStart();
    	
    	Log.i("Info", "Atividade iniciada");
    }
    
    @Override
    protected void onResume() {    	
    	super.onResume();
    	
    	Log.i("Info","onResume chamado");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {    	
    	//super.onSaveInstanceState(outState);
    	
    	outState.putString("VALOR_INTERNO", "ALGUM VALOR");
    	
    	Log.i("Info", "Ao salvar o estado da atividade");
    }
    
    @Override
    protected void onPause() {    
    	super.onPause();
    	
    	Log.i("Info", "Atividade foi pausada");
    }
    
    @Override
    protected void onStop() {    	
    	super.onStop();
    	
    	Log.i("Info", "Atividade foi parada");
    }
    
    @Override
    protected void onDestroy() {    	
    	super.onDestroy();
    	
    	Log.i("Info", "Atividade foi destruida");
    }
    
    @Override
    protected void onRestart() {    	
    	super.onRestart();
    	
    	Log.i("Info", "Atividade foi reiniciada");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}
