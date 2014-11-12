package mater.ejemplos.practicaandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ConsultaRegistros extends Activity {
	
	public int codigo = 0;
	JSONArray json;
	JSONObject numero;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_registros1);
		Intent i = getIntent();
        String respuesta = i.getStringExtra("respuesta");
        
        try {
			json = new JSONArray(respuesta);
			numero = json.getJSONObject(codigo);
			codigo = codigo + 1;
			id = numero.getInt("NUMREG");
			if (id==1) {
				TextView cambiar = (TextView)findViewById(R.id.textView2);
				cambiar.setText("Registro 1 de 1");
				JSONObject persona = json.getJSONObject(codigo);
				calcular(persona);
			}else{
				TextView cambiar = (TextView)findViewById(R.id.textView2);
				cambiar.setText("Registro 1 de " + id);
				JSONObject persona = json.getJSONObject(codigo);
				calcular(persona);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
	public void finalIzquierda(View v) throws JSONException{
		TextView cambiar = (TextView)findViewById(R.id.textView2);
		codigo = 1;
		cambiar.setText("Registro 1 de " + id);
		numero = json.getJSONObject(1);
		calcular(numero);
	}
	
	public void izquierda(View v) throws JSONException{
		if (codigo>1) {
			TextView cambiar = (TextView)findViewById(R.id.textView2);
			codigo = codigo - 1;
			cambiar.setText("Registro " + codigo + " de " + id);
			numero = json.getJSONObject(codigo);
			calcular(numero);
		}
		
	}
	
	public void derecha(View v) throws JSONException{
		if (codigo<id) {
			TextView cambiar = (TextView)findViewById(R.id.textView2);
			codigo = codigo + 1;
			cambiar.setText("Registro " + codigo + " de " + id);
			numero = json.getJSONObject(codigo);
			calcular(numero);
		}
		
	}
	
	public void finalDerecha(View v) throws JSONException{
		TextView cambiar = (TextView)findViewById(R.id.textView2);
		codigo = id;
		cambiar.setText("Registro " + codigo + " de " + id);
		numero = json.getJSONObject(id);
		calcular(numero);
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
