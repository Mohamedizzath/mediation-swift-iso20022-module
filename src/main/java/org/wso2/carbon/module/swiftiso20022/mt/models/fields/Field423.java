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
 * Model for balance checkpoint in User Header Block (Block 03).
 * <p>
 * format: (Date)(Time)
 * example: {423:18071715301204}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-3-user-header-description/">
 * User Header Block Fields</a>
 */
public class Field423 {

    public static final String TAG = "423";

    // format: YYMMDD
    // example: 180717
    private String date;

    // format: HHMMSS[ss]
    // ss -> hundredths of seconds
    // example: 15301204
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date value to be set.
     * @return object itself
     */
    public Field423 withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set time of the field and return the instance.
     *
     * @param time Time value to be set.
     * @return object itself
     */
    public Field423 withTime(String time) {
        setTime(time);
        return this;
    }

    /**
     * Method to parse and get Field423 object.
     *
     * @param field423String String containing value of 423 field in User Header Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the string is invalid
     */
    public static Field423 parse(String field423String) throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Date)(Time)
        Matcher field423Matcher = MTParserConstants.FIELD_423_REGEX_PATTERN.matcher(field423String);

        if (field423Matcher.matches()) {

            // group 1 -> Date
            // group 2 -> Time
            return new Field423()
                    .withDate(field423Matcher.group(1))
                    .withTime(field423Matcher.group(2));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.BALANCE_CHECKPOINT, ConnectorConstants.USER_HEADER_BLOCK));
        }
    }
}
