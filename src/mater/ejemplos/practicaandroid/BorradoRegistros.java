package mater.ejemplos.practicaandroid;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import android.view.View;
import android.widget.EditText;


public class BorradoRegistros extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_borrado_registros1);
		
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
	
	public void borrar(View v){
		EditText texto = (EditText) findViewById(R.id.editText1);
		String dni = texto.getText().toString();
		new BorradoBD().execute(dni);
	}
	
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
	private class BorradoBD extends AsyncTask<String, Void, String>{

		private ProgressDialog pDialog;
		private String url = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw16/fichas";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BorradoRegistros.this);
			pDialog.setMessage(getString(R.string.progess_title));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			String respuesta = "Sin respuesta";
			String URL_final = url;
			if (!arg0[0].equals("")) {
				URL_final = url + "/" + arg0[0];
			}
			AndroidHttpClient httpcliente = AndroidHttpClient
					.newInstance("AndroidHttpClient");
			HttpDelete httpdelete = new HttpDelete(URL_final);
			try {
				HttpResponse response = httpcliente.execute(httpdelete);
				respuesta = EntityUtils.toString(response.getEntity());
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
