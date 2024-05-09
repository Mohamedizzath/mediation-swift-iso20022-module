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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for MT message parsing.
 */
public class MTParserUtils {

    /**
     * Util methods for parsing blocks in MT messages.<br/>
     * Regex explanation - ^(\\{1:([^\\W_]+)\\})(\\{2:([^\\W_]+)\\})?(\\{3:(\\{\\d{3}:[^\\{\\}]*\\})+\\})?
     *                     (\\{4:[^\\{\\}]+\\R-\\})(\\{5:(\\{[A-Z]{3}:[^\\{\\}]*\\})+\\})?$
     *<ol>
     *      <li>(\\{1:([^\\W_]+)\\})-Regex for basic header block. Starting ( and ending ) marks group to match, {1:
     *      exact match of starting characters of basic header block, [^\\W_]+ matches one or more characters 0-9A-Za-z
     *      without _.
     *         <br/>Ex - {1:F01GSCRUS30XXXX0000000000}<br/><br/>
     *      </li>
     *
     *      <li>(\\{2:([^\\W_]+)\\})?- Regex for application header block. Since group ends with ? application header
     *      can be empty.
     *         <br/>Ex - {2:O9400400190425GSCRUS30XXX0000000002403141137N}<br/><br/>
     *      </li>
     *
     *      <li>(\\{3:(\\{\\d{3}:[^\\{\\}]*\\})+\\})?- Regex for user header block. Inner contents of user header block,
     *      next () represents inner groups to match tag:value. \\{ matches exact match of starting curly bracket of
     *      tag:value, \\d{3} matches tag which is exact three digits following by exact match of : and for the value
     *      [^\\{\\}]* matches zero or more occurrence of any character except {}. Note that there should be at least
     *       one tag.  <br/>Ex - {3:{113:URGT}}<br/><br/>
     *      </li>
     *
     *      <li>(\\{4:[^\\{\\}]+\\R-\\})-Regex for text block. [^\\{\\}]+ after {4: matches the contents of
     *      text block where there should be at least one character from any characters except {}.
     *        <br/>Ex - {4:<br/>
     *              :20:258158850<br/>
     *              :21:258158850<br/>
     *              :25:DD01100056869<br/>
     *              :28C:1/1<br/>
     *              :60F:D230930USD843686,20<br/>
     *              :61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8<br/>
     *              :86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003<br/>
     *              :62F:D230930USD846665,15<br/>
     *              :64:C231002USD334432401,27<br/>
     *              -}<br/><br/>
     *      </li>
     *      <li>(\\{5:(\\{[A-Z]{3}:[^\\{\\}]*\\})+\\})? - Regex for trailer block . \\{ after {5: matches exact match of
     *      starting curly bracket of tag:value, \\[A-Z]{3} matches tag which is exact three uppercase letters following
     *      by exact match of : and for the value [^\\{\\}]* matches zero or more occurrence of any character except {}.
     *      Note that there should be at least one tag.
     *         <br/>Ex - {5:{CHK:123456789ABC}}<br/><br/>
     *      </li>
     * </ol>
     *
     * @param mtMessage     Complete MT messages as string
     * @return              Blocks stored in Map with key as block name(basic-header-block, application-header-block...)
     */
    public static Map<String, String> getMessageBlocks(String mtMessage) throws MTMessageParsingException {
        Map<String, String> blocksMap = new HashMap<>();
        Matcher matcher = MTParserConstants.MT_MESSAGE_BLOCKS_REGEX.matcher(mtMessage);

        if (!matcher.matches()) {
            // Handle parsing exception
            throw new MTMessageParsingException(MTParserConstants.INVALID_MT_MESSAGE_BLOCKS);
        }

        // Extract the basic header block which in {1:......} format
        // Removing the {1: and } from the block and set to map with the key '1'
        if (!StringUtils.isBlank(matcher.group(1))) {
            blocksMap.put(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                    matcher.group(1).substring(3, matcher.group(1).length() - 1));
        }

        // Extract the application header block which in {2:......} format
        // Removing the {2: and } from the block and set to map with the key '2'
        if (!StringUtils.isBlank(matcher.group(3))) {
            blocksMap.put(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                    matcher.group(3).substring(3, matcher.group(3).length() - 1));
        }

        // Extract the user header block which in {3:......} format
        // Removing the {3: and } from the block and set to map with the key '3'
        if (!StringUtils.isBlank(matcher.group(5))) {
            blocksMap.put(ConnectorConstants.USER_HEADER_BLOCK_KEY,
                    matcher.group(5).substring(3, matcher.group(5).length() - 1));
        }

        // Extract the text block which in {4:......} format
        // Removing the {4: and -} from the block and set to map with the key '4'
        if (!StringUtils.isBlank(matcher.group(7))) {
            blocksMap.put(ConnectorConstants.TEXT_BLOCK_KEY,
                    matcher.group(7).substring(3, matcher.group(7).length() - 2));
        }

        // Extract the trailer block which in {5:......} format
        // Removing the {5: and } from the block and set to map with the key '5'
        if (!StringUtils.isBlank(matcher.group(8))) {
            blocksMap.put(ConnectorConstants.TRAILER_BLOCK_KEY,
                    matcher.group(8).substring(3, matcher.group(8).length() - 1));
        }

        return blocksMap;
    }

    /**
     * Method to extract fields from block with fields enclosed in curly brackets.
     * example: "{103:EBA}{119:STP}" -> ["103" -> "EBA", "119" -> "STP"]
     *
     * @param fieldsString Block containing fields enclosed in curly brackets
     * @return Map of fields where tag as the key with the value
     */
    public static Map<String, String> extractFieldsWithinCurlyBrackets(String fieldsString) {

        Map<String, String> fields = new HashMap<>();

        // regex pattern initialization for matching curly brackets with fields
        // {tag:value}, regex pattern matches tag and value in separate groups in each match
        // Get matcher to the regex matching -> {(Tag):(Field Value)}
        Matcher matcher = MTParserConstants.CURLY_BRACKETS_FIELDS_MATCHING_PATTERN.matcher(fieldsString);

        while (matcher.find()) {

            // group 1 -> Tag
            // group 2 -> Field Value
            fields.put(matcher.group(1), matcher.group(2));
        }

        return fields;
    }

  /**
     * Method for parsing fields in text block.
     * @param textBlock                     Text block as string to break into fields
     * @return                              Parsed field as a list of String
     * @throws MTMessageParsingException
     */
    public static List<String> getTextBlockFields(String textBlock) throws MTMessageParsingException {
        // Match whether text block starting with newline and : (Should have at least one tag)
        Pattern textStartPattern = Pattern.compile("^\\R:(.+)", Pattern.DOTALL);
        Matcher textStartMatcher = textStartPattern.matcher(textBlock);

        if (textStartMatcher.matches()) {
            // Remove the starting new line character and : from the text block
            textBlock = textStartMatcher.group(1);
        } else {
            throw new MTMessageParsingException("Text block not in the correct format");
        }

        // Remove the final new line character
        return List.of(textBlock.trim().split("\\R:"));
    }

    /**
     * Method for converting amount text from MT format to ISO format.
     * @param amountText        MT amount as text
     * @return                  Converted ISO amount string
     */
    public static Double convertMTAmountToISOAmount(String amountText) {
        amountText = amountText.replaceAll(",", ".");
        amountText = amountText.endsWith(".") ? amountText + "00" : amountText;

        return Double.parseDouble(amountText);
    }
}
