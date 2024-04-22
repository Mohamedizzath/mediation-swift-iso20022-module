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

package org.wso2.carbon.module.swiftiso20022.mt.parsers;

import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.messages.MT103Message;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserUtils;

import java.util.Map;

/**
 * Parser class for MT103 Message.
 */
public class MT103Parser {

    public static MT103Message parse(String mt103Message) throws MTMessageParsingException {

        Map<String, String> blocks = MTParserUtils.getMessageBlocks(mt103Message);
        // TODO: Do the format validations
        MT103Message mt103MessageModel = new MT103Message();
        MTParser.parse(blocks, mt103MessageModel);
        // TODO: implement parsing logic for the text block and set the text block
        return null;
    }
}
