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

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;

import java.util.List;
import java.util.Objects;

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
        if (StringUtils.isBlank(sendersReference)) {
            return new ErrorModel("T13", "Sender's Reference is required");
        }
        if (sendersReference.length() > 16) {
            return new ErrorModel("T33", "Sender's Reference length is invalid");
        }
        if (!Objects.isNull(timeIndication)) {
            // TODO: implement array checking
        }
        if (StringUtils.isBlank(bankOperationCode)) {
            return new ErrorModel("T13", "Bank Operation Code is required");
        }
        if (bankOperationCode.length() != 4) {
            return new ErrorModel("T33", "Bank Operation Code length is invalid");
        }
        if (!Objects.isNull(instructionCodes)) {
            // TODO: implement array checking
        }
        if (!StringUtils.isBlank(transactionTypeCode) && transactionTypeCode.length() != 3) {
            return new ErrorModel("T33", "Transaction Type Code length is invalid");
        }
        if (StringUtils.isBlank(value)) {
            return new ErrorModel("T13", "Value is required");
        }
        if (value.length() > 24) {
            return new ErrorModel("T33", "Value length is invalid");
        }
        if (!StringUtils.isBlank(instructedAmount) && instructedAmount.length() > 18) {
            return new ErrorModel("T33", "Instructed Amount length is invalid");
        }
        if (!StringUtils.isBlank(exchangeRate) && exchangeRate.length() > 12) {
            return new ErrorModel("T33", "Exchange Rate length is invalid");
        }

        // TODO: implement validation logic for entities

        if (!Objects.isNull(remittanceInformation)) {
            // TODO: implement validation for arrays
        }
        if (StringUtils.isBlank(detailsOfCharges)) {
            return new ErrorModel("T13", "Details of Charge is required");
        }
        if (detailsOfCharges.length() != 3) {
            return new ErrorModel("T33", "Details of Charge length is invalid");
        }
        if (!Objects.isNull(sendersCharges)) {
            // TODO: implement validation for arrays
        }
        if (!StringUtils.isBlank(receiversCharges) && receiversCharges.length() > 18) {
            return new ErrorModel("T33", "Receiver's charges length is invalid");
        }
        if (!Objects.isNull(senderToReceiverInformation)) {
            // TODO: implement validation for arrays
        }
        if (!Objects.isNull(regulatoryReporting)) {
            // TODO: implement validation for arrays
        }
        return new ErrorModel();
    }
}
