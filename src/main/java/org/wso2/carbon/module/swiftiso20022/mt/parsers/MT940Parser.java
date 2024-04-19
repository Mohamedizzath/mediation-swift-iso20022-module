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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.text.MT940TextBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field20;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field21;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field25;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class for parsing MT940 messages.
 */
public class MT940Parser {
    /**
     * Parser method for parsing MT940 message.
     * @param mtMessage       MT940 message as String
     * @return                MT940 message object
     */
    public static MT940Message parse(String mtMessage) throws MTMessageParsingException {
        Map<String, String> blocks = MTParserUtils.getMessageBlocks(mtMessage);

        MT940Message mt940Message = new MT940Message();
        MTParser.parse(blocks, mt940Message);

        if (!blocks.containsKey(ConnectorConstants.TEXT_BLOCK_KEY)) {
            // Text block is mandatory for any MT message
            throw new MTMessageParsingException(MTParserConstants.INVALID_TEXT_BLOCK);
        }

        mt940Message.setTextBlock(MT940TextBlock.parse(blocks.get(ConnectorConstants.TEXT_BLOCK_KEY)));

        return mt940Message;
    }
}
