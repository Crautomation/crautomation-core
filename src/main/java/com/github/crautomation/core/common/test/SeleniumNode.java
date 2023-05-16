package com.github.crautomation.core.common.test;

import io.restassured.http.ContentType;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import static io.restassured.RestAssured.get;


public final class SeleniumNode {
    /**
     * Returns the IP Address and Port number for the Node on which the driver
     * object has initiated a test script execution. Value is returned as a
     * String in the formation IP Address:Port
     */
    public static String getConnectionDetails(final RemoteWebDriver remoteDriver) {
        final HttpCommandExecutor executor = (HttpCommandExecutor) remoteDriver.getCommandExecutor();
        final String hostName = executor.getAddressOfRemoteServer().getHost();
        final int port = executor.getAddressOfRemoteServer().getPort();


        return get(String.format("http://%s:%s/grid/api/testsession?session=%s", hostName, port, remoteDriver.getSessionId()))
                .then().contentType(ContentType.JSON).extract().response().path("proxyId");
    }
}