package org.wso2.carbon.module.swiftiso20022;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;

import java.util.Optional;


/**
 * Class to convert JSON Object to MT103 format.
 */
public class JsonToMT103Transformer extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                    .getAxis2MessageContext();
            Optional<String> payload = ConnectorUtils.buildMessagePayloadFromMessageContext(axis2MessageContext);
            if (payload.isPresent()) {
                // TODO: Implement validation logic
            } else {
                // TODO: replace with constants
                this.log.error("Request payload is missing.");
            }
        } catch (Exception e) {
            // TODO: add exception handling logic
            this.log.error(e.getMessage());
            throw new ConnectException(e);
        }
    }
}
