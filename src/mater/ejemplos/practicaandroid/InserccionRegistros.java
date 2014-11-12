package mater.ejemplos.practicaandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class InserccionRegistros extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserccion_registros1);
		
		Intent i = getIntent();
        String dni = i.getStringExtra("dni");
        EditText clasedni = (EditText)findViewById(R.id.editText1);
		clasedni.setText(dni);
	}
	
	public void subir(View v){
		String resultado = "";
		EditText clasedni = (EditText)findViewById(R.id.editText1);
		String dni = "\"DNI\":\""+clasedni.getText().toString()+"\",";
		EditText clasenombre = (EditText)findViewById(R.id.editText2);
		String nombre = "\"Nombre\":\""+clasenombre.getText().toString()+"\",";
		EditText claseapellidos = (EditText)findViewById(R.id.EditText3);
		String apellidos = "\"Apellidos\":\""+claseapellidos.getText().toString()+"\",";
		EditText clasedireccion = (EditText)findViewById(R.id.EditText4);
		String direccion = "\"Direccion\":\""+clasedireccion.getText().toString()+"\",";
		EditText clasetelefono = (EditText)findViewById(R.id.EditText5);
		String telefono = "\"Telefono\":\""+clasetelefono.getText().toString()+"\",";
		EditText claseequipo = (EditText)findViewById(R.id.EditText6);
		String equipo = "\"Equipo\":\""+claseequipo.getText().toString()+"\"";
		resultado = "{" + dni + nombre + apellidos + direccion + telefono + equipo + "}";
		Log.d("practica", resultado);
		
		Intent intencion = new Intent();
        setResult(RESULT_OK,intencion);
        finish();
	}
	
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
}
