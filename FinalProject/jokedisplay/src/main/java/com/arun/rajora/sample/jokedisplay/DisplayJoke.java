package com.arun.rajora.sample.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJoke extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_joke);
		String joke = getIntent().getStringExtra("joke");
		if(!joke.isEmpty())
		((TextView) findViewById(R.id.joke_textView)).setText(joke);
	}
}
