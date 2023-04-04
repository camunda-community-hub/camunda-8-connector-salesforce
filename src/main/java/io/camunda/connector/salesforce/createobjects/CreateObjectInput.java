package io.camunda.connector.salesforce.createobjects;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

public class CreateObjectInput {

  public static final String INPUT_CONNECTION = "connection";
  @NotEmpty String connection;

  public static final String INPUT_MODE = "mode";

  public enum MODE_OPERATION {SINGLE, MULTIPLE}
  @NotEmpty MODE_OPERATION mode;

  public static final String INPUT_OBJECTTYPE = "objectType";
  public String objectType;

  public static final String INPUT_LISTOFATTRIBUTES = "listOfAttributes";
  Map<String, Object> listOfAttributes;

  public static final String INPUT_OPERATIONS = "operations";
  List<Map<String,Object>> operations;

}