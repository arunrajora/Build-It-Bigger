package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements DataFetcher.ResultCallBack {

	CountDownLatch signal=null;
	String joke=null;

	public ApplicationTest() {
		super(Application.class);
	}


	@Override
	protected void setUp() throws Exception {
	//	super.setUp();
		signal=new CountDownLatch(1);
	}

	@Override
	protected void tearDown() throws Exception {
		signal.countDown();
	//	super.tearDown();
	}
	public void testAsyncTaskFetchJoke() throws InterruptedException {
		new DataFetcher(this).execute();
		signal.await();
		assertTrue(joke!=null && joke.length()>0);
	}
	@Override
	public void getResultFromAsyncTask(String result) {
		joke=result;
		signal.countDown();
	}
}