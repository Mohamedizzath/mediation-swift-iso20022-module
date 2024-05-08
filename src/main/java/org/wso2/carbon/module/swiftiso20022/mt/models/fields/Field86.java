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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.MT940ParserConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 86.
 * <p>
 *     Option - No letter <br/>
 *     format: (Narrative)<br/>
 *     example: :86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003
 *     @see <a href="https://www2.swift.com/knowledgecentre/
 *     publications/usgf_20230720/2.0?topic=idx_fld_tag_86.htm">Tag 86</a>
 * </p>
 */
public class Field86 {
    public static final String TAG = "86";

    // Example - A, B, D
    private char option;

    // Example - EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003
    private List<List<Map<String, String>>> value;

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public List<List<Map<String, String>>> getValue() {
        return value;
    }

    public void setValue(List<List<Map<String, String>>> value) {
        this.value = value;
    }

    /**
     * Default constructor for Field86.
     */
    public Field86() { }

    /**
     * Constructor for parse and get Field86 object.
     * @param option              Option of the Field86
     * @param value       String which contains value of Field86
     */
    public Field86(char option, List<List<Map<String, String>>> value) {
        this.value = value;
        this.option = option;
    }

    /**
     * Method for parse and get Field86 object.
     * @param option              Option of the Field86
     * @param field86String       String which contains value of Field86
     * @return                    Created instance of Field86
     * @throws MTMessageParsingException
     */
    public static Field86 parse(char option, String field86String) throws MTMessageParsingException {
        if (option == MTParserConstants.FIELD_OPTION_NO_LETTER) {
            Matcher field86Matcher = MT940ParserConstants.FIELD_86_REGEX_PATTERN.matcher(field86String);

            if (field86Matcher.matches()) {
                List<List<Map<String, String>>> field86values = new ArrayList<>();
                String[] lines = field86Matcher.group(1).split("\\R"); // Splitting field86 by lines

                // Supported Field86 Codes
                List<String> supportedCodes = MTParserUtils.getMT940SupportedField86Codes();

                for (String line : lines) {
                    List<Map<String, String>> field86Line = new ArrayList<>();

                    String[] elements = line.split("/"); // Separate the values using /

                    for (int i = 0; i < elements.length; i++) {
                        if (i + 1 == elements.length) {
                            field86Line.add(Map.of(MT940ParserConstants.FIELD_86_NO_CODE, elements[i]));
                        } else if (supportedCodes.contains(elements[i])) {
                            field86Line.add(Map.of(elements[i], elements[i++]));
                        } else {
                            field86Line.add(Map.of(elements[i], elements[i++]));
                        }
                    }

                    field86values.add(field86Line);
                }

                return new Field86(option, field86values);
            } else {
                throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                        Field86.TAG));
            }
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_OPTION,
                    option, Field86.TAG));
        }
    }
}
