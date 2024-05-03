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
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.util.PayloadHelper;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;

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

            MT940Message mt940Message = MT940Parser.parse(message);
            String json = gson.toJson(mt940Message);

            ConnectorUtils.appendJsonResponseToMessageContext(messageContext, json);
        } catch (AxisFault | MTMessageParsingException e) {
            throw new ConnectException(e);
        }
    }
}
