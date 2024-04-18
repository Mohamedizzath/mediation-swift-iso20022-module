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
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for time indication in Text Block (Block 04).
 * <p>
 * format: /(Code)/(Time)(Sign)(Offset)
 * <p>
 * example: /CLSTIME/0915+0100
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_13C.htm">
 * Field 13C</a>
 */
public class Field13C {

    public static final String TAG = "13C";

    // example: CLSTIME
    private String code;

    // format: HHMM
    // example: 0915
    private String time;

    // example: +
    private String sign;

    // format: HHMM
    // example: 0100
    private String offset;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Method to set code of the field and return the instance.
     *
     * @param code Code to be set.
     * @return object itself
     */
    public Field13C withCode(String code) {
        setCode(code);
        return this;
    }

    /**
     * Method to set time of the field and return the instance.
     *
     * @param time Time to be set.
     * @return object itself
     */
    public Field13C withTime(String time) {
        setTime(time);
        return this;
    }

    /**
     * Method to set sign of the field and return the instance.
     *
     * @param sign Sign to be set.
     * @return object itself
     */
    public Field13C withSign(String sign) {
        setSign(sign);
        return this;
    }

    /**
     * Method to set offset of the field and return the instance.
     *
     * @param offset Offset to be set.
     * @return object itself
     */
    public Field13C withOffset(String offset) {
        setOffset(offset);
        return this;
    }

    /**
     * Method to parse and get Field13C object.
     *
     * @param field13CString String containing value of 13C field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field13C parse(String field13CString) throws MTMessageParsingException {

        // Get matcher to the regex matching -> /(Code)/(Time)(Sign)(Offset)
        Matcher field13CMatcher = MTParserConstants.FIELD_13C_REGEX_PATTERN.matcher(field13CString);

        if (field13CMatcher.matches()) {

            // group 1 -> Code
            // group 2 -> Time
            // group 3 -> Sign
            // group 4 -> Offset
            return  new Field13C()
                    .withCode(field13CMatcher.group(1))
                    .withTime(field13CMatcher.group(2))
                    .withSign(field13CMatcher.group(3))
                    .withOffset(field13CMatcher.group(4));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.TIME_INDICATION, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
