package com.mahmoudhossam.sayit;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	ForvoApi api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		api = new ForvoApi(getAPIKey());
	}

	/**
	 * Don't forget to provide your own API key!
	 */
	public String getAPIKey() {
		return getResources().getString(R.string.api_key);
	}

	public void onOK(View view) throws InterruptedException, ExecutionException {
		EditText textBox = (EditText) findViewById(R.id.editText1);
		String text = textBox.getText().toString();
		String result = new MakeRequest().execute(text).get();
		
	}

	class MakeRequest extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			return api.get_std_pronouncation(arg0[0]);
		}

	}

}
