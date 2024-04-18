/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for message input reference in User Header Block (Block 03).
 * <p>
 * format: (Date)(LT Address)(Session Number)(Sequence Number)
 * example: {106:120811BANKBEBBAXXX2222123456}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field106 {

    public static final String TAG = "106";

    // format: YYMMDD
    // example: 120811
    private String date;

    // example: BANKBEBBAXXX
    private String logicalTerminalAddress;

    // example: 2222
    private String sessionNumber;

    // example: 123456
    private String sequenceNumber;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogicalTerminalAddress() {
        return logicalTerminalAddress;
    }

    public void setLogicalTerminalAddress(String logicalTerminalAddress) {
        this.logicalTerminalAddress = logicalTerminalAddress;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date value to be set.
     * @return object itself
     */
    public Field106 withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set logical terminal address of the field and return the instance.
     *
     * @param logicalTerminalAddress Logical terminal address value to be set.
     * @return object itself
     */
    public Field106 withLogicalTerminalAddress(String logicalTerminalAddress) {
        setLogicalTerminalAddress(logicalTerminalAddress);
        return this;
    }

    /**
     * Method to set session number of the field and return the instance.
     *
     * @param sessionNumber Session number Value to be set.
     * @return object itself
     */
    public Field106 withSessionNumber(String sessionNumber) {
        setSessionNumber(sessionNumber);
        return this;
    }

    /**
     * Method to set sequence number value of the field and return the instance.
     *
     * @param sequenceNumber Sequence number value to be set.
     * @return object itself
     */
    public Field106 withSequenceNumber(String sequenceNumber) {
        setSequenceNumber(sequenceNumber);
        return this;
    }

    /**
     * Method to parse and get Field103 object.
     *
     * @param field106String String containing value of 106 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the string cannot be parsed.
     */
    public static Field106 parse(String field106String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Date)(LT Address)(Session No)(Sequence No)
        Matcher field106Matcher = MTParserConstants.FIELD_106_REGEX_PATTERN.matcher(field106String);

        if (field106Matcher.matches()) {

            // group 1 -> Date
            // group 2 -> LT Address
            // group 1 -> Session Number
            // group 2 -> Sequence Number
            return new Field106()
                    .withDate(field106Matcher.group(1))
                    .withLogicalTerminalAddress(field106Matcher.group(2))
                    .withSessionNumber(field106Matcher.group(3))
                    .withSequenceNumber(field106Matcher.group(4));

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.MESSAGE_INPUT_REFERENCE, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }
}
