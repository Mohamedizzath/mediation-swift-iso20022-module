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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 77B -> Model for regulatory reporting in Text Block (Block 04).
 *
 * <dl>
 *      <dt>format:</dt>
 *      <dd>4*35x</dd>
 *
 *      <dt>example:</dt>
 *      <dd>:77B:/ORDERRES/BE//MEILAAN 1, 9000 GENT</dd>
 * </dl>
 * <p>
 * 77T -> Model for envelope contents in Text Block (Block 04).
 *
 * <dl>
 *      <dt>format:</dt>
 *      <dd>9000z</dd>
 *
 *      <dt>example:</dt>
 *      <dd>:77T:/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+...</dd>
 * </dl>
 *
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_77B.htm">
 * Field 77B</a>
 * @see <a href="https://www2.swift.com/knowledgecentre/publications/usgf_20230720/2.0?topic=idx_fld_tag_77T.htm">
 * Field 77T</a>
 */
public class Field77 {

    public static final String TAG = "77";
    private static final List<Character> OPTIONS = Arrays.asList(
            ConnectorConstants.OPTION_B, ConnectorConstants.OPTION_T);
    private char option;
    private List<String> lines;
    private String value;

    /**
     * Constructor to initialize all attributes.
     *
     * @param option single character which identify the option.
     * @param lines String array with each value is a separate line
     * @param value String with character set z
     */
    public Field77(char option, List<String> lines, String value) {
        this.option = option;
        this.lines = lines;
        this.value = value;
    }

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Method to parse and get Field77 object.
     * Current implementations -> Option B and T
     *
     * @param option single character option of the field77String
     * @param field77String String containing value of 77 field in Text Block
     * @return An instance of this model.
     * @throws MTMessageParsingException if the value is invalid
     */
    public static Field77 parse(char option, String field77String) throws MTMessageParsingException {

        if (!OPTIONS.contains(option)) {
            throw new MTMessageParsingException(String.format(
                    MTParserConstants.INVALID_OPTION_FOR_FIELD, option, ConnectorConstants.FIELD_77));
        }

        Matcher field77Matcher = MTParserConstants.FIELD_77_REGEX_PATTERN.matcher(field77String);

        if (field77Matcher.matches()) {
            // group 1 -> lines
            // group 3 -> value
            List<String> lines = field77Matcher.group(1) != null ?
                    Arrays.asList(field77Matcher.group(1).split(MTParserConstants.LINE_BREAK_REGEX_PATTERN)) : null;
            return new Field77(option, lines, field77Matcher.group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_IN_BLOCK_MESSAGE,
                    ConnectorConstants.FIELD_77 + (option == ConnectorConstants.NO_LETTER_OPTION ? "" : option),
                    ConnectorConstants.TEXT_BLOCK));
        }
    }
}
