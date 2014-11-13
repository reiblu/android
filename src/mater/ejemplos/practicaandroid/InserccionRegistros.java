package mater.ejemplos.practicaandroid;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
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
		
		new InserccionBD().execute(resultado);
		
		//String s = "Name: %s";
		//String name = "Bob";
		//Log.e("practica", "Resulto " + String.format(s, name));
		// Name: Bob
		
		
	}
	
	public void onBackPressed(){
	    setResult(RESULT_CANCELED);
	    super.onBackPressed();
	    finish();
	}
	
	private class InserccionBD extends AsyncTask<String, Void, String>{

		private ProgressDialog pDialog;
		private String url = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw16/fichas";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(InserccionRegistros.this);
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
			HttpPost httppost = new HttpPost(url);
			try {
				httppost.setEntity(new StringEntity(arg0[0]));
				HttpResponse response = httpcliente.execute(httppost);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
