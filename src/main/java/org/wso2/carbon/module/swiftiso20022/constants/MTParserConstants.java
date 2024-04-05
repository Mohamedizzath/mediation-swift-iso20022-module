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

    public static final String FIELD_DLM_TAG = "DLM";
    public static final String FIELD_TNG_TAG = "TNG";
    public static final String CURLY_BRACKETS_FIELDS_MATCHING_PATTERN = "\\{([^}:]+):([^}:]*)}";

    // Error message related constants
    public static final String EMPTY_BLOCK = "Empty %s cannot be parsed";

    // Constants related to value extraction from fields (starting and ending indexes)

    // User Header Block Field indexes
    public static final int FIELD_106_DATE_START = 0;
    public static final int FIELD_106_DATE_END = 4;
    public static final int FIELD_106_LT_ADDRESS_START = 4;
    public static final int FIELD_106_LT_ADDRESS_END = 16;
    public static final int FIELD_106_SESSION_NO_START = 16;
    public static final int FIELD_106_SESSION_NO_END = 20;
    public static final int FIELD_106_SEQUENCE_NO_START = 20;
    public static final int FIELD_115_CREDITING_TIME_START = 0;
    public static final int FIELD_115_CREDITING_TIME_END = 4;
    public static final int FIELD_115_DEBITING_TIME_START = 4;
    public static final int FIELD_115_DEBITING_TIME_END = 8;
    public static final int FIELD_115_COUNTRY_CODE_START = 8;
    public static final int FIELD_115_COUNTRY_CODE_END = 10;
    public static final int FIELD_115_REFERENCE_START = 10;
    public static final int FIELD_423_DATE_START = 0;
    public static final int FIELD_423_DATE_END = 4;
    public static final int FIELD_423_TIME_START = 4;

    // Trailer Block Field indexes
    public static final int FIELD_MRF_SENT_DATE_START = 0;
    public static final int FIELD_MRF_SENT_DATE_END = 6;
    public static final int FIELD_MRF_TIME_START = 6;
    public static final int FIELD_MRF_TIME_END = 10;
    public static final int FIELD_MRF_DATE_START = 10;
    public static final int FIELD_MRF_DATE_END = 16;
    public static final int FIELD_MRF_LT_ADDRESS_START = 16;
    public static final int FIELD_MRF_LT_ADDRESS_END = 28;
    public static final int FIELD_MRF_SESSION_NO_START = 28;
    public static final int FIELD_MRF_SESSION_NO_END = 32;
    public static final int FIELD_MRF_SEQUENCE_NO_START = 32;
    public static final int FIELD_PDE_TIME_START = 0;
    public static final int FIELD_PDE_TIME_END = 4;
    public static final int FIELD_PDE_DATE_START = 4;
    public static final int FIELD_PDE_DATE_END = 10;
    public static final int FIELD_PDE_LT_ADDRESS_START = 10;
    public static final int FIELD_PDE_LT_ADDRESS_END = 22;
    public static final int FIELD_PDE_SESSION_NO_START = 22;
    public static final int FIELD_PDE_SESSION_NO_END = 26;
    public static final int FIELD_PDE_SEQUENCE_NO_START = 26;
    public static final int FIELD_PDM_TIME_START = 0;
    public static final int FIELD_PDM_TIME_END = 4;
    public static final int FIELD_PDM_DATE_START = 4;
    public static final int FIELD_PDM_DATE_END = 10;
    public static final int FIELD_PDM_LT_ADDRESS_START = 10;
    public static final int FIELD_PDM_LT_ADDRESS_END = 22;
    public static final int FIELD_PDM_SESSION_NO_START = 22;
    public static final int FIELD_PDM_SESSION_NO_END = 26;
    public static final int FIELD_PDM_SEQUENCE_NO_START = 26;
    public static final int FIELD_SYS_TIME_START = 0;
    public static final int FIELD_SYS_TIME_END = 4;
    public static final int FIELD_SYS_DATE_START = 4;
    public static final int FIELD_SYS_DATE_END = 10;
    public static final int FIELD_SYS_LT_ADDRESS_START = 10;
    public static final int FIELD_SYS_LT_ADDRESS_END = 22;
    public static final int FIELD_SYS_SESSION_NO_START = 22;
    public static final int FIELD_SYS_SESSION_NO_END = 26;
    public static final int FIELD_SYS_SEQUENCE_NO_START = 26;

}
