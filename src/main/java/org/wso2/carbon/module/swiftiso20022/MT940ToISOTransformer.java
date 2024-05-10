/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.util.PayloadHelper;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940JSONParserUtils;

/**
 * Transform MT940 to ISO20022.camt.053.
 */
public class MT940ToISOTransformer extends AbstractConnector {
    private static Log log = LogFactory.getLog(MT940ToISOTransformer.class);

    private static final Gson gson = new Gson();

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            String message = PayloadHelper.getTextPayload(messageContext).trim();

            if (StringUtils.isBlank(message)) {
                throw new MTMessageParsingException(ConnectorConstants.ERROR_EMPTY_MT940_MSG);
            }

            MT940Message mt940Message = MT940Parser.parse(message);
            JsonObject mt940JsonObject = (JsonObject) gson.toJsonTree(mt940Message);

            MT940JSONParserUtils.updateJsonObjectToMT940(mt940JsonObject);
            MT940JSONParserUtils.updateDatesFrMT940(mt940JsonObject);
            MT940JSONParserUtils.addBICToMT940Message(mt940JsonObject);

            String mt940Json = mt940JsonObject.toString();

            ConnectorUtils.appendJsonResponseToMessageContext(messageContext, mt940JsonObject.toString());
        } catch (AxisFault | MTMessageParsingException e) {
            log.error(e.getMessage(), e);
            ConnectorUtils.appendErrorToMessageContext(messageContext,
                    String.format(ConnectorConstants.ERROR_PARSING_MT_MESSAGE, "940"), e.getMessage());

            throw new ConnectException(e, String.format(ConnectorConstants.ERROR_PARSING_MT_MESSAGE, "940"));
        }
    }
}
