package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arun.rajora.sample.jokedisplay.DisplayJoke;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jokes.rajora.myapplication.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by rajor on 02-Sep-16.
 */
	class DataFetcher extends AsyncTask<Context, Void, String> {
	private static MyApi myApiService = null;
	private ResultCallBack callBack;

	public interface ResultCallBack {
		public void getResultFromAsyncTask(String result);
	}

	DataFetcher(ResultCallBack cb) {
		callBack = cb;
	}

	@Override
	protected String doInBackground(Context... params) {
		if (myApiService == null) {  // Only do this once
			MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
					new AndroidJsonFactory(), null)
					// options for running against local devappserver
					// - 10.0.2.2 is localhost's IP address in Android emulator
					// - turn off compression when running against local devappserver
					.setRootUrl("http://192.168.43.69:8080/_ah/api/")
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
						@Override
						public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
							abstractGoogleClientRequest.setDisableGZipContent(true);
						}
					});
			// end options for devappserver

			myApiService = builder.build();
		}
		try {
			return myApiService.tellJoke().execute().getData();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		if (callBack != null)
			callBack.getResultFromAsyncTask(result);
	}
}
