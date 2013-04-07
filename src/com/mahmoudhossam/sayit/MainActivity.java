package com.mahmoudhossam.sayit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	ForvoApi api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		api = new ForvoApi(getAPIKey());
	}

	/**
	 * Don't forget to provide your own API key!
	 */
	public String getAPIKey() {
		return getResources().getString(R.string.api_key);
	}

	public void onOK(View view) {
		EditText textBox = (EditText) findViewById(R.id.editText1);
		String text = textBox.getText().toString();
		new GetPronunciation().execute(text);
	}

	class GetPronunciation extends AsyncTask<String, Integer, String> {
		
		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected String doInBackground(String... arg0) {
			return api.get_std_pronouncation(arg0[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			setProgressBarIndeterminateVisibility(false);
			try {
				String url = parse(result);
				Uri uri = Uri.parse(url);
				play(uri);
			} catch (WordNotFoundException e) {
				String text = "Error: Word not found";
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			}

		}
		
		private void play(Uri uri) {
			MediaPlayer player = MediaPlayer.create(getApplicationContext(), uri);
			if (player.isPlaying()){
				player.stop();
			}
			player.start();
		}
		
		private String parse(String text) throws WordNotFoundException {
			try {
				JSONObject root = (JSONObject) new JSONTokener(text).nextValue();
				JSONArray items = root.getJSONArray("items");
				if(items.length() == 0){
					throw new WordNotFoundException();
				}
				return items.getJSONObject(0).getString("pathogg");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
