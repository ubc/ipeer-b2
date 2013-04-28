package ca.ubc.ctlt.ipeerb2.integration;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ScreenshotOnFailureListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult result) {

		Object currentClass = result.getInstance();
		((BaseTest) currentClass).takeScreenshot(result.getMethod().getMethodName());

		super.onTestFailure(result);
	}

}
