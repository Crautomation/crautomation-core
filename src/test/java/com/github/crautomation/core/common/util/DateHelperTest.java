package com.github.crautomation.core.common.util;

import com.github.crautomation.core.common.test.BaseTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

public class DateHelperTest extends BaseTest {
    @Test
    public void shouldValidateCurrentTimeReturnsFormattedCorrectly() {
        final String formattedTime = DateHelper.getCurrentTime("HH:mm:SS");

        assertThat(formattedTime,
                matchesPattern("([0-9][0-9]):([0-9][0-9]):([0-9][0-9])"));
    }
}