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

	public String get_std_pronouncation(String word)
			throws MalformedURLException, IOException {
		String url = buildRequestURL(STANDARD_PRONOUNCIATION, word);
		return executeRequest(url);
	}

	private String executeRequest(String requestURL)
			throws MalformedURLException, IOException {
		URL url = new URL(requestURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = new BufferedInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			while (reader.ready()) {
				builder.append(reader.readLine() + "\n");
			}
		} finally {
			conn.disconnect();
		}
		return builder.toString();
	}
}
