package io.camunda.connector.salesforce.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.partner.Connector;

import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.partner.PartnerConnection;


public class ConnectionSalesForce {

  /**
   * get the configuration from a JSON String
   * @param connectionParameterString the string
   * @return the configuration
   */
  public ConnectorConfig getConnectorConfiguration(String connectionParameterString) {
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();

    Gson gson = builder.create();
    Map<String,Object> connectionParameter = gson.fromJson(connectionParameterString, HashMap.class);
    return getConnectorConfiguration(connectionParameter);
  }

  /**
   * Get the configuration from a Map
   * @param connectionParameter map of parameters
   * @return the configuration
   */
  protected ConnectorConfig getConnectorConfiguration(Map<String,Object> connectionParameter) {
    final ConnectorConfig config = new ConnectorConfig();
    config.setUsername( (String) connectionParameter.get("userName"));
    final String password = (String) connectionParameter.get("password");
    final String token = (String) connectionParameter.get("token");

    config.setPassword(password + token);
    final String authEndPoint = (String) connectionParameter.get("authEndPoint");
    if (authEndPoint != null) {
      config.setAuthEndpoint(authEndPoint);
    }
    final String proxyHost = (String) connectionParameter.get("proxyHost");
    final Integer proxyPort = (Integer) connectionParameter.get("proxyPort");
    if (proxyHost != null && proxyHost.length() > 0 && proxyPort != null && proxyPort > 0) {
      config.setProxy(proxyHost, proxyPort);
    }
    final String proxyPassword = (String) connectionParameter.get("proxyPassword");
    if (proxyPassword != null) {
      config.setProxyPassword(proxyPassword);
    }
    final String proxyUserName = (String) connectionParameter.get("proxyUserName");
    if (proxyUserName != null) {
      config.setProxyUsername(proxyUserName);
    }
    final Integer connectionTimeoutInMS = (Integer) connectionParameter.get("connectionTimeoutInMs");
    if (connectionTimeoutInMS != null && connectionTimeoutInMS > 0) {
      config.setConnectionTimeout(connectionTimeoutInMS);
    }
    final Integer readTimeoutInMs = (Integer) connectionParameter.get("readTimeoutInMs");
    if (readTimeoutInMs != null && readTimeoutInMs > 0) {
      config.setReadTimeout(readTimeoutInMs);
    }
    final String restEndPoint = (String) connectionParameter.get("restEndPoint");
    if (restEndPoint != null) {
      config.setRestEndpoint(restEndPoint);
    }
    final String serviceEndPoint = (String) connectionParameter.get("serviceEndPoint");
    if (serviceEndPoint != null) {
      config.setServiceEndpoint(serviceEndPoint);
    }
    config.setPrettyPrintXml(true);

    return config;
  }

  /**
   * Get a new connection
   * @param config configuration to create the object
   * @return the connection
   * @throws Exception if the connection can't be establish
   */
  public PartnerConnection getConnection(ConnectorConfig config) throws Exception {
    return com.sforce.soap.partner.Connector.newConnection(config);
  }
}
