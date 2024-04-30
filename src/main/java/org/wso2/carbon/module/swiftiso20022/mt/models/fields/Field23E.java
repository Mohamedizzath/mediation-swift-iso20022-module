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
 * Model for instruction code in Text Block (Block 04).
 * <p>
 * format: (Instruction Code)/[Additional Information]
 * <p>
 * example: TELI/3226553478
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_23E.htm">
 * Field 23E</a>
 */
public class Field23E {

    public static final String TAG = "23E";

    // example: TELI
    private String instructionCode;

    // example: 3226553478
    private String additionalInformation;

    public String getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(String instructionCode) {
        this.instructionCode = instructionCode;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * Method to set instruction code of the field and return the instance.
     *
     * @param instructionCode Instruction Code to be set.
     * @return object itself
     */
    public Field23E withInstructionCode(String instructionCode) {
        setInstructionCode(instructionCode);
        return this;
    }

    /**
     * Method to set additional information of the field and return the instance.
     *
     * @param additionalInformation Additional Information to be set.
     * @return object itself
     */
    public Field23E withAdditionalInformation(String additionalInformation) {
        setAdditionalInformation(additionalInformation);
        return this;
    }

    /**
     * Method to parse and get Field23E object.
     *
     * @param field23EString String containing value of 23E field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field23E parse(String field23EString)  throws MTMessageParsingException {

        // Get matcher to the regex matching -> (Instruction Code)[/(Additional Information)]
        Matcher field23EMatcher = MTParserConstants.FIELD_23E_REGEX_PATTERN.matcher(field23EString);

        if (field23EMatcher.matches()) {

            // group 1 -> Instruction Code
            // group 2 -> /Additional Information
            // group 3 -> Additional Information
            return new Field23E()
                    .withInstructionCode(field23EMatcher.group(1))
                    .withAdditionalInformation(field23EMatcher.group(3));

        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    MT103Constants.INSTRUCTION_CODE, ConnectorConstants.TEXT_BLOCK));
        }
    }
}
