package com.example.enviodatos;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void listadoOnClick(View view){
		startActivity(new Intent(this, ListadoActivity.class));
		
		
	}

	public void EnviarOnClik(View view) {
		Thread nt = new Thread() {
			@Override
			public void run() {
				EditText usuario = (EditText) findViewById(R.id.et_nombre);
				EditText clave = (EditText) findViewById(R.id.et_apellido);
				//EditText edad = (EditText) findViewById(R.id.et_edad);
				//CheckBox modo = (CheckBox) findViewById(R.id.ck_modo);

				try {
					final String res;
					//if (modo.isChecked()) {
						//res = enviarGet(usuario.getText().toString(), clave
							//	.getText().toString());

					//} else {
						res = enviarPost(usuario.getText().toString(), clave
								.getText().toString());
					//}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MainActivity.this, res,
									Toast.LENGTH_LONG).show();
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		nt.start();
	}

	public String enviarPost(String usuario, String clave) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(
				"http://172.16.216.184:84/api/usuario/login/");
		HttpResponse response = null;
        try {
			List<NameValuePair> params = new ArrayList<NameValuePair>(3);
			params.add(new BasicNameValuePair("usuario", usuario));
			params.add(new BasicNameValuePair("clave", clave));
			//params.add(new BasicNameValuePair("edad", edad));
			//params.add(new BasicNameValuePair("modo", "POST"));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = httpClient.execute(httpPost, localContext);
        } catch (Exception e) {
			// TODO: handle exception
		}
        return response.toString();
	}

	public String enviarGet(String usuario, String clave) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpResponse response = null;
		//String parametros = "?usuario=" + usuario + "&clave=" + clave
			//	+ "&edad=" + edad + "&modo=GET";
		
		HttpGet httpget = new HttpGet(
				"http://192.168.0.117:89/usuario/login/" + usuario + "/" + clave + "");
		try {
			response = httpClient.execute(httpget, localContext);

		} catch (Exception e) {
		
		}
		return response.toString();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
