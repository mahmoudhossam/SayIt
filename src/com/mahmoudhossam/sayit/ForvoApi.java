package com.mahmoudhossam.sayit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForvoApi {
	public static final String STANDARD_PRONOUNCIATION = "standard-pronunciation";
	public static final String WORD_PRONOUNCIATION = "word-pronunciations";
	public static final String LANGUAGE_LIST = "language-list";
	public static final String LANGUAGE_POPULAR = "language-popular";

	private String apiKey;

	public ForvoApi(String key) {
		apiKey = key;
	}

	private String buildRequestURL(String action, String word) {
		String format = "http://apifree.forvo.com/key/%s/format/json/action/%s/word/%s";
		return String.format(format, apiKey, action, word);
	}

	public String get_std_pronouncation(String word) {
		String url = buildRequestURL(STANDARD_PRONOUNCIATION, word);
		return executeRequest(url);
	}

	private String executeRequest(String requestURL) {
		URL url = null;
		try {
			url = new URL(requestURL);
		} catch (MalformedURLException e1) {
			System.err.println(e1.getMessage());
		}
		HttpURLConnection conn = null;
		StringBuilder builder = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			builder = new StringBuilder();

			InputStream in = new BufferedInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			while (reader.ready()) {
				builder.append(reader.readLine() + "\n");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			conn.disconnect();
		}
		return builder.toString();
	}
}
