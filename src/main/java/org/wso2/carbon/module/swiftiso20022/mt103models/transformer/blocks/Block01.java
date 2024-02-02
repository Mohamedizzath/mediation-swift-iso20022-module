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

package org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks;

import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

/**
 * Class that models request payload block01.
 */
public class Block01 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block01";
    String applicationIdentifier;
    String serviceIdentifier;
    String logicalTerminalAddress;
    String sessionNumber;
    String sequenceNumber;
    @Override
    public ErrorModel validate() {
        if (applicationIdentifier == null) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Application Identifier should be present");
        } else if (!applicationIdentifier.equals("F")) {
            return new ErrorModel("H02", "value should be F");
        }
        if (serviceIdentifier == null) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Service Identifier should be present");
        } else if (!serviceIdentifier.equals("01")) {
            return new ErrorModel("H03", "Service Identifier should be a valid value");
        }
        if (logicalTerminalAddress == null) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Logical Terminal Address should be present");
        } else if (logicalTerminalAddress.length() != 12) {
            // TODO: replace with constants
            return new ErrorModel("H10", "Logical Terminal Address length is invalid");
        }
        if (sessionNumber == null) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Session Number should be present");
        } else if (sessionNumber.length() != 4) {
            // TODO: replace with constants
            return new ErrorModel("H15", "Session Number length is invalid");
        }
        if (sequenceNumber == null) {
            // TODO: replace with constants
            return new ErrorModel("H98", "Sequence Number should be present");
        } else if (sequenceNumber.length() != 6) {
            // TODO: replace with constants
            return new ErrorModel("H20", "Sequence Number length is invalid");
        }
        return new ErrorModel();
    }

    public void setApplicationIdentifier(String applicationIdentifier) {
        this.applicationIdentifier = applicationIdentifier;
    }

    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setLogicalTerminalAddress(String logicalTerminalAddress) {
        this.logicalTerminalAddress = logicalTerminalAddress;
    }

    public String getLogicalTerminalAddress() {
        return logicalTerminalAddress;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }
}
