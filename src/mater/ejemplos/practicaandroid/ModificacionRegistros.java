package mater.ejemplos.practicaandroid;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
		
		new ModificacionBD().execute(resultado);
	}
	
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
private class ModificacionBD extends AsyncTask<String, Void, String>{

		private ProgressDialog pDialog;
		private String url = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw16/fichas";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ModificacionRegistros.this);
			pDialog.setMessage(getString(R.string.progess_title));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			String respuesta = "Sin respuesta";
			AndroidHttpClient httpcliente = AndroidHttpClient
					.newInstance("AndroidHttpClient");
			HttpPut httpput = new HttpPut(url);
			Log.d("practica", url);
			try {
				httpput.setEntity(new StringEntity(arg0[0]));
				Log.d("practica", arg0[0]);
				HttpResponse response = httpcliente.execute(httpput);
				respuesta = EntityUtils.toString(response.getEntity());
				Log.d("practica", respuesta);
				httpcliente.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return respuesta;
		}
		
		protected void onPostExecute(String respuesta){
			pDialog.dismiss();
			JSONArray json;
			try {
				json = new JSONArray(respuesta);
				JSONObject numero = json.getJSONObject(0);
				int id = numero.getInt("NUMREG");
				if(id==1 || id==0){
					Intent intencion = new Intent();
			        setResult(RESULT_OK,intencion);
			        finish();
				} else {
					Intent intencion = new Intent();
			        setResult(RESULT_CANCELED,intencion);
			        finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
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
