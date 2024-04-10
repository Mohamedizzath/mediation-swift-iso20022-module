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

import org.testng.annotations.DataProvider;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Test constants for MTParserTests.
 */
public class MTParserTestConstants {

    public static final String VALID_USER_HEADER_BLOCK = "{103:EBA}{113:xxxx}{108:REF0140862562/15}" +
            "{115:121413121413DEBANKDECDA123}{119:STP}{423:18071715301204}{106:120811BANKBEBBAXXX2222123456}" +
            "{424:PQAB1234}{121:180f1e65-90e0-44d5-a49a-92b55eb3025f}{165:/123/abcdefghi-abcdefghi-abcdefghi-abcd}" +
            "{433:/AOK}{434:/FPO/Some information}{111:255}";

    public static final String VALID_TRAILER_BLOCK = "{CHK:123456789ABC}{TNG:}{PDE:1348120811BANKFRPPAXXX2222123456}" +
            "{DLM:}{MRF:1806271539180626BANKFRPPAXXX2222123456}{PDM:1213120811BANKFRPPAXXX2222123456}" +
            "{SYS:1454120811BANKFRPPAXXX2222123456}";

    @DataProvider(name = "validMTMessageMapDataProvider")
    Object[][] getValidMTMessageMapDataProvider() {
        return new Object[][]{
                {new HashMap<String, String>()},
                {Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, VALID_USER_HEADER_BLOCK)},
                {Map.of(ConnectorConstants.TRAILER_BLOCK_KEY, VALID_TRAILER_BLOCK)},
                {Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, VALID_USER_HEADER_BLOCK,
                        ConnectorConstants.TRAILER_BLOCK_KEY, VALID_TRAILER_BLOCK)}
        };
    }

    @DataProvider(name = "invalidUserHeaderBlockDataProvider")
    Object[][] getInvalidUserHeaderBlockDataProvider() {
        return new Object[][]{
                {"  "},
                {"{"},
                {"}"},
                {"{  { }"},
                {" some string "},
                {"{:245689393}"},
                {"{567:245689393}"},
                {"{567:245689393}}"},
                {"{423:1234}"},
                {"{433:/AOK/}"},
                {"{433:/AOKadditional info"},
                {"{103:}"},
                {"{103:123}"},
                {"{103:AB}"},
                {"{103:ABCD}"},
                {"{113:}"},
                {"{113:ABCDE}"},
                {"{113:<<>>}"},
                {"{113:123}"},
                {"{108:}"},
                {"{108:REF}"},
                {"{108:<<<<<<<<>>>>>>>>}"},
                {"{108:REF0140862562/015}"},
                {"{119:}"},
                {"{119:ABCDEFGHIJK}"},
                {"{423:}"},
                {"{423:123456}"},
                {"{423:ABCDEFABCDEFGH}"},
                {"{106:}"},
                {"{106:120811}"},
                {"{106:120811BANKBEBBAXXX222212345634}"},
                {"{424:}"},
                {"{424:<<<<<<<<>>>>>>>>}"},
                {"{424:PQAB1234PQAB1234PQAB1234}"},
                {"{111:}"},
                {"{111:ABC}"},
                {"{111:1234}"},
                {"{121:}"},
                {"{121:180f1e65-90e0-44d5-a49a-92b55eb3025f-abc}"},
                {"{121:180F1E65-90E0-44D5-A49A-92B55EB3025F}"},
                {"{115:}"},
                {"{115:121413}"},
                {"{115:12141312141325BANKDECDA123}"},
                {"{115:121413ABCDEFDEBANKDECDA123}"},
                {"{165:}"},
                {"{165:FIN}"},
                {"{165:/FIN}"},
                {"{165:/FIN/}"},
                {"{165:/FIN/This sentence is longer than 34 characters}"},
                {"{165:/FINThis sentence is longer than 34 characters}"},
                {"{433:}"},
                {"{433:AOK}"},
                {"{433:/AOK/}"},
                {"{433:/AOK/This sentence is longer than 20 characters}"},
                {"{434:}"},
                {"{434:FPO}"},
                {"{434:/FPO/}"},
                {"{434:/FPO/This sentence is longer than 20 characters}"},
        };
    }

    @DataProvider(name = "invalidTrailerBlockDataProvider")
    Object[][] getTrailerBlockDataProvider() {
        return new Object[][]{
                {"  "},
                {"{"},
                {"}"},
                {"{  { }"},
                {" some string "},
                {"TNG:some value"},
                {"FFF:"},
                {"{:245689393}"},
                {"{567:245689393}"},
                {"{567:245689393}}"},
                {"{567:245689393:adadaf}"},
                {"{CHK:}"},
                {"{CHK:123456789abc}"},
                {"{CHK:123456789ABCD}"},
                {"{PDE:}"},
                {"{PDE:1348120811}"},
                {"{PDE:ABCD120811BANKFRPPAXXX2222123456}"},
                {"{PDE:1348120811BANKFRPPAXXX2222123456AB}"},
                {"{MRF:}"},
                {"{MRF:1806271539180626}"},
                {"{MRF:ABCDEF1539180626BANKFRPPAXXX2222123456}"},
                {"{MRF:1806271539180626BANKFRPPAXXX2222123456AB}"},
                {"{PDM:}"},
                {"{PDM:1348120811}"},
                {"{PDM:ABCD120811BANKFRPPAXXX2222123456}"},
                {"{PDM:1348120811BANKFRPPAXXX2222123456AB}"},
                {"{SYS:}"},
                {"{SYS:1348120811}"},
                {"{SYS:ABCD120811BANKFRPPAXXX2222123456}"},
                {"{SYS:1348120811BANKFRPPAXXX2222123456AB}"},
        };
    }
}
