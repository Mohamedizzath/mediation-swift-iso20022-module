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

import java.util.List;

/**
 * Class that models request payload block04.
 */
public class Block04 implements RequestPayloadBlock {
    static final String BLOCK_NAME = "block04";
    String sendersReference;
    List<String> timeIndication;
    String bankOperationCode;
    List<String> instructionCodes;
    String transactionTypeCode;
    String value;
    String instructedAmount;
    String exchangeRate;
    // TODO: include entity fields
    List<String> remittanceInformation;
    String detailsOfCharges;
    List<String> sendersCharges;
    String receiversCharges;
    List<String> senderToReceiverInformation;
    List<String> regulatoryReporting;
    @Override
    public ErrorModel validate() {
        // TODO: Implement validation logic
        return null;
    }
}
