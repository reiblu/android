package mater.ejemplos.practicaandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class ModificacionRegistros extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificacion_registros1);
		
		Intent i = getIntent();
        String respuesta = i.getStringExtra("respuesta");
        
        JSONArray json;
		try {
			json = new JSONArray(respuesta);
			JSONObject numero = json.getJSONObject(1);
			calcular(numero);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void modificar(View v){
		Intent intencion = new Intent();
        setResult(RESULT_OK,intencion);
        finish();
	}
	
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
	public void calcular(JSONObject persona) throws JSONException{
		EditText dni = (EditText)findViewById(R.id.editText1);
		dni.setText(persona.getString("DNI"));
		EditText nombre = (EditText)findViewById(R.id.editText2);
		nombre.setText(persona.getString("Nombre"));
		EditText apellido = (EditText)findViewById(R.id.EditText3);
		apellido.setText(persona.getString("Apellidos"));
		EditText direccion = (EditText)findViewById(R.id.EditText4);
		direccion.setText(persona.getString("Direccion"));
		EditText telefono = (EditText)findViewById(R.id.EditText5);
		telefono.setText(persona.getString("Telefono"));
		EditText equipo = (EditText)findViewById(R.id.EditText6);
		equipo.setText(persona.getString("Equipo"));
    	
    }
	
}
