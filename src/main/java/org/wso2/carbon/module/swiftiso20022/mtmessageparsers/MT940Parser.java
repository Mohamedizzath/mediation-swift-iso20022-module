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

package org.wso2.carbon.module.swiftiso20022.mtmessageparsers;

import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;

/**
 * Parser class for parsing MT940 messages
 */
public class MT940Parser {
    /**
     * Parser method for parsing MT940 message
     * @param mtMessage       MT940 message as String
     * @return                MT940 message object
     */
    public static MT940Message parse(String mtMessage) throws Exception {
        Map<String, String> blocks = MTParserUtils.getMessageBlocks(mtMessage);

        MT940Message mt940Message = MTParser.parse(blocks, MT940Message.class);

        // mt940Message.setMT940TextBlock(parseMT940TextBlock(blocks.get(ConnectorConstants.TEXT_BLOCK_CODE)));

        return mt940Message;
    }
}
