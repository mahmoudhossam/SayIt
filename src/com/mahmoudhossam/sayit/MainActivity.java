package com.mahmoudhossam.sayit;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
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
		String url = new ParseJSON().execute(result).get();
		Uri uri = Uri.parse(url);
		MediaPlayer player = MediaPlayer.create(getApplicationContext(), uri);
		if (player.isPlaying()){
			player.stop();
		}
		player.start();
	}

	class MakeRequest extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			return api.get_std_pronouncation(arg0[0]);
		}

	}

	class ParseJSON extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			JSONTokener tokener = new JSONTokener(params[0]);
			try {
				JSONObject root = (JSONObject) tokener.nextValue();
				JSONObject contents = (JSONObject) root.getJSONArray("items").get(0);
				return contents.getString("pathogg");
			} catch (JSONException e) {
				System.err.println(e.getMessage());
			}
			return null;
		}

	}

}
