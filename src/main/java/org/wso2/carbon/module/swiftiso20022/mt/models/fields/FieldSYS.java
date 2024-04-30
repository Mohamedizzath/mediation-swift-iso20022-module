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
 * Model for system originated message in Trailer Block (Block 05).
 * <p>
 * format: (Time)(Date)(LT Identifier)(Session Number)(Sequence Number)
 * example: {SYS:1454120811BANKFRPPAXXX2222123456}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-5-trailers-description/">
 * Trailer Block Fields</a>
 */
public class FieldSYS {

    public static final String TAG = "SYS";

    // format: HHMM
    // example: 1454
    private String time;

    // format: YYMMDD
    // example: 120811
    private String date;

    // example: BANKFRPPAXXX
    private String ltIdentifier;

    // example: 2222
    private String sessionNumber;

    // example: 123456
    private String sequenceNumber;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLtIdentifier() {
        return ltIdentifier;
    }

    public void setLtIdentifier(String ltIdentifier) {
        this.ltIdentifier = ltIdentifier;
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
     * Method to set time of the field and return the instance.
     *
     * @param time Time value to be set.
     * @return object itself
     */
    public FieldSYS withTime(String time) {
        setTime(time);
        return this;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date value to be set.
     * @return object itself
     */
    public FieldSYS withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set logical terminal identifier of the field and return the instance.
     *
     * @param ltIdentifier Logical terminal identifier value to be set.
     * @return object itself
     */
    public FieldSYS withLtIdentifier(String ltIdentifier) {
        setLtIdentifier(ltIdentifier);
        return this;
    }

    /**
     * Method to set session number of the field and return the instance.
     *
     * @param sessionNumber Session number value to be set.
     * @return object itself
     */
    public FieldSYS withSessionNumber(String sessionNumber) {
        setSessionNumber(sessionNumber);
        return this;
    }

    /**
     * Method to set sequence number of the field and return the instance.
     *
     * @param sequenceNumber Sequence number value to be set.
     * @return object itself
     */
    public FieldSYS withSequenceNumber(String sequenceNumber) {
        setSequenceNumber(sequenceNumber);
        return this;
    }

    /**
     * Method to parse and get FieldSYS object.
     *
     * @param fieldSYSString String containing value of SYS field in Trailer Block
     * @return An instance of this model.
     * @throws MTMessageParsingException of the value is invalid
     */
    public static FieldSYS parse(String fieldSYSString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldSYSMatcher = MTParserConstants.FIELD_SYS_REGEX_PATTERN.matcher(fieldSYSString);

        if (fieldSYSMatcher.matches()) {

            // group 1 -> Time
            // group 2 -> Date
            // group 3 -> LT Identifier
            // group 4 -> Session Number
            // group 5 -> Sequence Number
            return new FieldSYS()
                    .withTime(fieldSYSMatcher.group(1))
                    .withDate(fieldSYSMatcher.group(2))
                    .withLtIdentifier(fieldSYSMatcher.group(3))
                    .withSessionNumber(fieldSYSMatcher.group(4))
                    .withSequenceNumber(fieldSYSMatcher.group(5));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.SYSTEM_ORIGINATED_MESSAGE, ConnectorConstants.TRAILER_BLOCK));
        }
    }
}
