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

package org.wso2.carbon.module.swiftiso20022;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mtmessageparsers.MTParser;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MTMessage;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MTParserTestConstants;

import java.util.Map;

/**
 * Test class for MTParser.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class})
public class MTParserTests {

    @Test(dataProvider = "validMTMessageMapDataProvider", dataProviderClass = MTParserTestConstants.class)
    public void testMTParserParseMethod(Map<String, String> blocks) throws MTMessageParsingException {

        MTParser.parse(blocks, new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class, dataProvider = "invalidUserHeaderBlockDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testParseUserHeaderBlock(String userHeaderBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, userHeaderBlockString), new MTMessage());
    }

    @Test(expectedExceptions = MTMessageParsingException.class, dataProvider = "invalidTrailerBlockDataProvider",
            dataProviderClass = MTParserTestConstants.class)
    public void testParseTrailerBlock(String trailerBlockString) throws MTMessageParsingException {

        MTParser.parse(Map.of(ConnectorConstants.TRAILER_BLOCK_KEY, trailerBlockString), new MTMessage());
    }
}
