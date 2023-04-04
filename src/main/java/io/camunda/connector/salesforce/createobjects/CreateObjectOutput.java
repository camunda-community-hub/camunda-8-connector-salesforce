package io.camunda.connector.salesforce.createobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateObjectOutput {

  public static final String OUTPUT_OBJECTID = "objectId";
  String objectId;

  public static final String OUTPUT_SUCCESS = "success";
  boolean success;

  public static final String OUTPUT_ERRORS = "errors";
  String errors;

  public static final String OUTPUT_OPERATIONS = "operations";
  List<Map<String, Object>> operations = new ArrayList<>();

  CreateObjectInput.MODE_OPERATION mode;

  public CreateObjectOutput(CreateObjectInput.MODE_OPERATION mode) {
    this.mode = mode;
  }

  public void addResult(String objectId, boolean isSuccess, String errors) {
    switch (mode) {
    case SINGLE -> {
      this.objectId = objectId;
      this.success = isSuccess;
      this.errors = errors;
    }
    case MULTIPLE -> {
      this.operations.add(Map.of(OUTPUT_OBJECTID, objectId, OUTPUT_SUCCESS, isSuccess, OUTPUT_ERRORS, errors));}

    }
  }

}
