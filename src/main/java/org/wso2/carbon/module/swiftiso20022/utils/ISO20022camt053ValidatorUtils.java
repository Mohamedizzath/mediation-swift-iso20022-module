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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.MessageContext;
import org.jaxen.JaxenException;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;

/**
 * Utils methods for ISO20022.camt.053 validations.
 */
public class ISO20022camt053ValidatorUtils {
    /**
     * Check whether ISO20022.camt.053 message contains valid electronic sequence number.
     * @param isBusinessMsg     Boolean value to indicate ISO20022 message is business message or not
     * @param mc                MessageContext which contains the XML 20022 input
     * @throws ConnectException
     */
    public static void validateElectronicSequenceNumber(boolean isBusinessMsg, MessageContext mc)
            throws ConnectException {
        try {
            String xPathToSeqNumber = ConnectorConstants.XPATH_ELECTSEQ_NUMBER_WITHOUT_BUSINESS_HDR;

            if (isBusinessMsg) {
                xPathToSeqNumber = ConnectorConstants.XPATH_ELECTSEQ_NUMBER_WITH_BUSINESS_HDR;
            }

            OMElement seqNumberElement = ISOMessageParser.getXMLElementByXPath(xPathToSeqNumber, mc);

            if (seqNumberElement == null) {
                // Sequence number not present in the ISO20022 message
                throw new ConnectException(ConnectorConstants.ERROR_EMPTY_ELECTRONIC_SEQUENCE_NUMBER);
            }
        } catch (JaxenException e) {
            throw new ConnectException(ConnectorConstants.ERROR_INVALID_ELECTRONIC_SEQUENCE_NUMBER);
        }
    }

    /**
     * Check whether ISO20022.camt.053 message contains valid logic sequence number.
     * @param isBusinessMsg     Boolean value to indicate ISO20022 message is business message or not
     * @param mc                MessageContext which contains the XML 20022 input
     * @throws ConnectException
     */
    public static void validateLegalSequenceNumber(boolean isBusinessMsg, MessageContext mc) throws ConnectException {
        try {
            String xPathToLogicNumber = ConnectorConstants.XPATH_LEGALSEQ_NUMBER_WITHOUT_BUSINESS_HDR;

            if (isBusinessMsg) {
                xPathToLogicNumber = ConnectorConstants.XPATH_LEGALSEQ_NUMBER_WITH_BUSINESS_HDR;
            }

            OMElement logicNumberElement = ISOMessageParser.getXMLElementByXPath(xPathToLogicNumber, mc);

            if (logicNumberElement == null) {
                // Logic number not present in the ISO20022 message
                throw new ConnectException(ConnectorConstants.ERROR_EMPTY_LEGAL_SEQUENCE_NUMBER);
            }
        } catch (JaxenException e) {
            throw new ConnectException(ConnectorConstants.ERROR_INVALID_LEGAL_SEQUENCE_NUMBER);
        }
    }
}
