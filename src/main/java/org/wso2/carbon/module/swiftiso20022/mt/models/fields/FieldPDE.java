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
 * Model for possible duplicate emission in Trailer Block (Block 05).
 * <p>
 * format: (Time)(Date)(LT Identifier)(Session Number)(Sequence Number)
 * example: {PDE:1348120811BANKFRPPAXXX2222123456}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-5-trailers-description/">
 * Trailer Block Fields</a>
 */
public class FieldPDE {

    public static final String TAG = "PDE";

    // format: HHMM
    // example: 1348
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

    public FieldPDE setTime(String time) {
        this.time = time;
        return this;
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
    public FieldPDE withTime(String time) {
        setTime(time);
        return this;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date value to be set.
     * @return object itself
     */
    public FieldPDE withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set logical terminal identifier of the field and return the instance.
     *
     * @param ltIdentifier Logical terminal identifier value to be set.
     * @return object itself
     */
    public FieldPDE withLtIdentifier(String ltIdentifier) {
        setLtIdentifier(ltIdentifier);
        return this;
    }

    /**
     * Method to set session number of the field and return the instance.
     *
     * @param sessionNumber Session number value to be set.
     * @return object itself
     */
    public FieldPDE withSessionNumber(String sessionNumber) {
        setSessionNumber(sessionNumber);
        return this;
    }

    /**
     * Method to set sequence number of the field and return the instance.
     *
     * @param sequenceNumber Sequence number value to be set.
     * @return object itself
     */
    public FieldPDE withSequenceNumber(String sequenceNumber) {
        setSequenceNumber(sequenceNumber);
        return this;
    }

    /**
     * Method to parse and get FieldPDE object.
     *
     * @param fieldPDEString String containing value of PDE field in Trailer Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static FieldPDE parse(String fieldPDEString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Time)(Date)(LT Address)(Session No)(Sequence No)
        Matcher fieldPDEMatcher = MTParserConstants.FIELD_PDE_REGEX_PATTERN.matcher(fieldPDEString);

        if (fieldPDEMatcher.matches()) {

            // group 1 -> Time
            // group 2 -> Date
            // group 3 -> LT Identifier
            // group 4 -> Session Number
            // group 5 -> Sequence Number
            return new FieldPDE()
                    .withTime(fieldPDEMatcher.group(1))
                    .withDate(fieldPDEMatcher.group(2))
                    .withLtIdentifier(fieldPDEMatcher.group(3))
                    .withSessionNumber(fieldPDEMatcher.group(4))
                    .withSequenceNumber(fieldPDEMatcher.group(5));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.POSSIBLE_DUPLICATE_EMISSION, ConnectorConstants.TRAILER_BLOCK));
        }
    }

}
