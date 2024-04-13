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

package org.wso2.carbon.module.swiftiso20022.constants;

/**
 * Constants for the MT parsers.
 */
public class MTParserConstants {

    private MTParserConstants() {
        // Private constructor to prevent instantiation.
    }

    public static final String MT_MESSAGE_BLOCKS_REGEX =
                    "^(\\{1:([^\\W_\\}]+)\\})(\\{2:([^\\W_\\}]+)\\})?(\\{3:(\\{\\d{3}:[^\\{\\}]*\\})+\\})?" +
                    "(\\{4:[^\\{\\}]+\\})(\\{5:(\\{[A-Z]{3}:[^\\{\\}]*\\})+\\})?$";
    public static final String BASIC_HEADER_REGEX = "^(F|A|L)(\\d{2})([A-Z0-9]{12})(\\d{4})(\\d{6})$";
    public static final String INPUT_OUTPUT_IDENTIFIER_REGEX = "^(I|O).*$";
    public static final String INPUT_APPLICATION_HEADER_REGEX = "^I(\\d{3})([A-Z0-9]{12})(S|U|N|)(\\d?)(\\d{3}|)$";
    public static final String OUTPUT_APPLICATION_HEADER_REGEX =
            "^O(\\d{3})(\\d{4})(\\d{6}[A-Z0-9]{12}[0-9]{4}[0-9]{6})(\\d{6}|)(\\d{4}|)(N|)$";

    public static final String INVALID_MT_MESSAGE_BLOCKS = "MT message blocks are not in the correct format";
    public static final String INVALID_BASIC_HEADER = "Basic header block is not in the correct format";
    public static final String INVALID_APPLICATION_HEADER = "Application header block is not in the correct format";
}
