package com.github.crautomation.core.common.testplatform.constants;

/**
 * Platforms which tests can run against.
 */
public enum TestPlatforms {
    GRID("grid", ""),
    LOCAL("local", "");

    private String name;
    private String url;

    TestPlatforms(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
