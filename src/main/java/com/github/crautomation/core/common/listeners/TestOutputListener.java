package com.github.crautomation.core.common.listeners;

import com.github.crautomation.core.common.util.DateHelper;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * TestOutputListener
 * <p>
 * Formats test result output in the console.
 * <p>
 * [HH:mm:SS] [RESULT] test.class.path.Test
 */
public class TestOutputListener extends TestListenerAdapter {
    private void formatTestResultForOutput(final String resultType, final ITestResult tr) {
        final String croppedTestName = tr.getInstanceName().replace("com.github.crautomation.", "");
        System.out.println(String.format("%s %s", DateHelper.getCurrentTime("HH:mm:ss"), resultType.concat(croppedTestName)));
    }

    @Override
    public synchronized void onTestSuccess(final ITestResult tr) {
        formatTestResultForOutput("[PASSED] ", tr);
        super.onTestSuccess(tr);
    }

    @Override
    public synchronized void onTestFailure(final ITestResult tr) {
        formatTestResultForOutput("[FAILED] ", tr);
        super.onTestFailure(tr);
    }

    @Override
    public synchronized void onTestSkipped(final ITestResult tr) {
        formatTestResultForOutput("[SKIPPED] ", tr);
        super.onTestSkipped(tr);
    }

}