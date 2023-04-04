package io.camunda.connector.salesforce.createobjects;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectorConfig;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.salesforce.common.ConnectionSalesForce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OutboundConnector(name = "createObject", inputVariables = { CreateObjectInput.INPUT_CONNECTION,
    CreateObjectInput.INPUT_MODE, CreateObjectInput.INPUT_OBJECTTYPE, CreateObjectInput.INPUT_LISTOFATTRIBUTES,
    CreateObjectInput.INPUT_OPERATIONS }, type = CreateObjectFunction.TYPE_CREATEOBJECT)
public class CreateObjectFunction implements OutboundConnectorFunction {

  Logger logger = LoggerFactory.getLogger(CreateObjectFunction.class.getName());

  public static final String TYPE_CREATEOBJECT = "createObject";

  public static final String BPMERROR_ERROR = "Error";
  public static final String BPMERROR_BADCONFIGURATION = "Bad configuration";

  @Override
  public CreateObjectOutput execute(OutboundConnectorContext context) throws ConnectorException {
    CreateObjectInput input = context.getVariablesAsType(CreateObjectInput.class);

    try {
      ConnectionSalesForce connectionSalesForce = new ConnectionSalesForce();

      ConnectorConfig connectorConfiguration = connectionSalesForce.getConnectorConfiguration(input.connection);
      PartnerConnection connection = connectionSalesForce.getConnection(connectorConfiguration);

      if (CreateObjectInput.MODE_OPERATION.SINGLE.equals(input.mode))
        return createObject(input, connection, List.of(
            Map.of(CreateObjectInput.INPUT_OBJECTTYPE, input.objectType, // type
                CreateObjectInput.INPUT_LISTOFATTRIBUTES, input.listOfAttributes)));
      else if (CreateObjectInput.MODE_OPERATION.MULTIPLE.equals(input.mode))
        return createObject(input, connection, input.operations);
      else
        throw new ConnectorException(BPMERROR_BADCONFIGURATION, "[" + CreateObjectInput.INPUT_MODE + "] must contains ["
            + CreateObjectInput.MODE_OPERATION.SINGLE + "] or ["
            + CreateObjectInput.MODE_OPERATION.MULTIPLE + "]  (contains |" + input.mode + "]");

    } catch (Exception e) {
      logger.error("createObject: Error: " + e.getMessage());

      throw new ConnectorException(BPMERROR_ERROR, e.getMessage());
    }
  }

  private CreateObjectOutput createObject(CreateObjectInput input,
                                          PartnerConnection connection,
                                          List<Map<String, Object>> listOperations) throws Exception {
    List<SObject> listSObject = new ArrayList<>();

    for (Map<String, Object> operation : listOperations) {
      SObject sObject = new SObject();
      sObject.setType((String) operation.get(CreateObjectInput.INPUT_OBJECTTYPE));

      Map<String, Object> listOfAttributes = (Map<String, Object>) operation.get(CreateObjectInput.INPUT_LISTOFATTRIBUTES);

      for (Map.Entry<String, Object> entry : listOfAttributes.entrySet()) {
        sObject.setField(entry.getKey(), entry.getValue());
      }
      listSObject.add(sObject);
    }

    CreateObjectOutput output = new CreateObjectOutput(input.mode);

    SaveResult[] listResults = connection.create(listSObject.toArray(new SObject[0]));

    if (listResults != null) {
      for (SaveResult saveResult : listResults) {

        String errors = Arrays.stream(saveResult.getErrors())
            .map(t -> t.getMessage())
            .collect(Collectors.joining(", "));

        output.addResult(saveResult.getId(), saveResult.isSuccess(), errors);
      }
    } else {
      output.addResult(null, false, "No results");
    }
    return output;

  }

}
