package mater.ejemplos.practicaandroid;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import android.widget.Toast;

public class Principal extends Activity {
	private final int resultado = 001;
	private final int resultado1 = 002;
	private final int resultado2 = 003;
	private final int resultado3 = 004;
	private int tipo = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);

	}

	public void conectar(View v) {
		tipo = 0;
		EditText texto = (EditText) findViewById(R.id.editText1);
		String dni = texto.getText().toString();
		new ConsultaBD().execute(dni);

	}

	public void insertar(View v) {
		tipo = 1;
		EditText texto = (EditText) findViewById(R.id.editText1);
		String dni = texto.getText().toString();
		if (dni.equals("")) {

			Toast.makeText(Principal.this, "Debe introducir un DNI",
					Toast.LENGTH_LONG).show();
		} else {
			new ConsultaBD().execute(dni);
		}
	}
	
	public void modificar(View v) {
		tipo = 2;
		EditText texto = (EditText) findViewById(R.id.editText1);
		String dni = texto.getText().toString();
		if (dni.equals("")) {

			Toast.makeText(Principal.this, "Debe introducir un DNI",
					Toast.LENGTH_LONG).show();
		} else {
			new ConsultaBD().execute(dni);
		}
	}
	
	public void borrar(View v) {
		tipo = 3;
		EditText texto = (EditText) findViewById(R.id.editText1);
		String dni = texto.getText().toString();
		if (dni.equals("")) {

			Toast.makeText(Principal.this, "Debe introducir un DNI",
					Toast.LENGTH_LONG).show();
		} else {
			new ConsultaBD().execute(dni);
		}
	}

	private class ConsultaBD extends AsyncTask<String, Void, String> {

		private ProgressDialog pDialog;
		private String url = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw16/fichas";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Principal.this);
			pDialog.setMessage(getString(R.string.progess_title));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String respuesta = "Sin respuesta";
			String URL_final = url;
			if (!params[0].equals("")) {
				URL_final = url + "/" + params[0];
			}
			try {
				AndroidHttpClient httpclient = AndroidHttpClient
						.newInstance("AndroidHttpClient");
				HttpGet httpget = new HttpGet(URL_final);
				HttpResponse response = httpclient.execute(httpget);
				respuesta = EntityUtils.toString(response.getEntity());
				httpclient.close();
			} catch (Exception e) {
				Log.e("practica", e.toString());
			}
			return respuesta;

		}

		@Override
		protected void onPostExecute(String respuesta) {
			pDialog.dismiss();
			try {
				JSONArray json = new JSONArray(respuesta);
				JSONObject numero = json.getJSONObject(0);
				int id = numero.getInt("NUMREG");
				if (id == 0 && tipo != 1) {
					respuesta = "Registro no existe";
					Toast.makeText(getBaseContext(), respuesta,
							Toast.LENGTH_LONG).show();

				} else if (id == 0 && tipo == 1) {
					EditText texto = (EditText) findViewById(R.id.editText1);
					String dni = texto.getText().toString();
					Intent nextScreen = new Intent(Principal.this,
								InserccionRegistros.class);
					nextScreen.putExtra("dni", dni);
					startActivityForResult(nextScreen, resultado1);

					

				} else if (id == 1 && tipo == 1) {

					Toast.makeText(Principal.this, "Registro existente",
							Toast.LENGTH_LONG).show();

				} else {

					if (tipo == 0) {
						Intent nextScreen = new Intent(Principal.this,
								ConsultaRegistros.class);
						nextScreen.putExtra("respuesta", respuesta);
						startActivityForResult(nextScreen, resultado);
						
					} else if (tipo == 2) {

						Intent nextScreen = new Intent(Principal.this,
								ModificacionRegistros.class);
						nextScreen.putExtra("respuesta", respuesta);
						startActivityForResult(nextScreen, resultado2);
						
					} else if (tipo == 3) {

						Intent nextScreen = new Intent(Principal.this,
								BorradoRegistros.class);
						nextScreen.putExtra("respuesta", respuesta);
						startActivityForResult(nextScreen, resultado3);
						
					}
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}

		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == resultado) {
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(Principal.this, "Consulta finalizada",
						Toast.LENGTH_LONG).show();
			}
		}

		if (requestCode == resultado1) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(Principal.this, "Inserción realizada",
						Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(Principal.this, "Inserción cancelada",
						Toast.LENGTH_LONG).show();
			}
		}
		
		if (requestCode == resultado2) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(Principal.this, "Modificacion realizada",
						Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(Principal.this, "Modificacion cancelada",
						Toast.LENGTH_LONG).show();
			}
		}
		
		if (requestCode == resultado3) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(Principal.this, "Borrado realizado",
						Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(Principal.this, "Borrado cancelado",
						Toast.LENGTH_LONG).show();
			}
		}

	}

}
