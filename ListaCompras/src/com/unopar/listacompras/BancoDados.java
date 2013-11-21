package com.unopar.listacompras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class BancoDados {
	private final String NOME_BD = "banco.db";
	private final int VERSAO_BD = 1;
	
	private final SQLiteDatabase _db;
	
	private SQLiteStatement _deleteStatement;
	
	public BancoDados(Context context) {
		AbreBancoDados helper = new AbreBancoDados(context);
		
		_db = helper.getWritableDatabase();
		
		_deleteStatement = _db.compileStatement(
				"DELETE FROM COMPRAS WHERE NOME = ?");
	}
	
	public void inserirNovaCompra(String compra) {
		ContentValues dados = new ContentValues();
		dados.put("NOME", compra);
		dados.put("DATA", (new Date()).getTime());
		
		_db.insert("COMPRAS", null, dados);
	}
	
	public List<String> recuperaCompras() {
		ArrayList<String> compras = new ArrayList<String>();
		
		Cursor cursor = _db.query("COMPRAS", 
				new String[] { "NOME" }, null, 
				null, null, null, "DATA ASC");
		
		while(cursor.moveToNext()) {
			compras.add(cursor.getString(0));
		}
		
		cursor.close();
		
		return compras;
	}
	
	public boolean existeCompra(String novaCompra) {

		Cursor cursor = _db.query(
				"COMPRAS", 
				new String[] { "NOME" }, 
				"NOME = ?", 
				new String[] { novaCompra }, 
				null, null, null);
		
		boolean existe = cursor.moveToNext();
		cursor.close();
		
		return existe;
		
	}
	
	public boolean adicionaSeNaoExiste(String novaCompra) {
		if(!existeCompra(novaCompra)) {
			inserirNovaCompra(novaCompra);
			
			return true;
		}
		
		
		return false;
	}
	
	public boolean excluirCompra(String compra) {
		_deleteStatement.bindString(1, compra);
		
		return _deleteStatement.executeUpdateDelete() == 1;
	}
	
	public Cursor executaSql(String sql, String...params) {
		return _db.rawQuery(sql, params);
	}
	
	private class AbreBancoDados extends SQLiteOpenHelper {
		public AbreBancoDados(Context contexto) {
			super(contexto, NOME_BD, null, VERSAO_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE COMPRAS ( " + 
					   "  ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "" +
					   "  NOME TEXT, " + 
					   "  DATA INTEGER);" );
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS COMPRAS;");
			onCreate(db);
		}
	}

}
