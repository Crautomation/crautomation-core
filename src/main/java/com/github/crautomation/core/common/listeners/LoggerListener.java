package com.github.crautomation.core.common.listeners;

import com.github.crautomation.core.common.properties.constants.CustomResources;
import com.github.crautomation.core.common.test.BaseTest;
import com.github.crautomation.core.common.util.ProminentStep;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * LoggerListener
 *
 * Handles attaching the test specific log files to the Tests for reporting.
 */
public class LoggerListener extends TestListenerAdapter
{
    @Override
    public synchronized void onTestSkipped(final ITestResult testResult)
    {
        attachLogFile(testResult);
    }

    @Override
    public synchronized void onTestFailure(final ITestResult testResult)
    {
        attachLogFile(testResult);
    }

    @Override
    public synchronized void onTestSuccess(final ITestResult testResult)
    {
        attachLogFile(testResult);
    }

    @Attachment(value = "Log File")
    private String attachLogFile(final ITestResult tr)
    {
        ProminentStep.create("Attaching Log File to report.");

        final Object currentClass = tr.getInstance();
        final String logFileName = ((BaseTest) currentClass).getLoggerFileName();

        try
        {
            return new String(Files.readAllBytes(new File(CustomResources.LOG_LOCATION.concat(logFileName).concat(".log")).toPath()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return "There was an error attaching the log file to this report.";
    }
}
