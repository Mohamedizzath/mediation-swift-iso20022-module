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

/**
 * Test constants for MT103ToJsonTransformer.
 */
public class MT103ToJsonTransformerTestConstants {

    public static final String VALID_PAYLOAD = "{1:F01BANKFRPPAXXX4321123456}" +
            "{2:O1030930180711BANKFRPPAXXX76351234560912300912N}" +
            "{3:{103:EBA}{119:STP}{121:180f1e65-90e0-44d5-a49a-92b55eb3025f}}{4:\n" +
            ":20:Ref254\n" +
            ":13C:/CLSTIME/0800+0100\n" +
            ":13C:/RNCTIME/0800+0100\n" +
            ":23B:SPAY\n" +
            ":23E:CHQB/BENEFICIARY\n" +
            ":23E:CORT\n" +
            ":23E:PHOI/BENEFICIARY\n" +
            ":32A:981209USD1000,00\n" +
            ":33B:USD1000,00\n" +
            ":36:0,978\n" +
            ":50K:/12345678\n" +
            "JOHN DOE\n" +
            "123, FAKE STREET\n" +
            "FAKETOWN\n" +
            ":51A:ABNANL2A\n" +
            ":54A:IRVTUS3N\n" +
            ":59:/BE62510007547061\n" +
            "JOHANN WILLEMS\n" +
            "RUE JOSEPH II, 19\n" +
            "1040 BRUSSELS:71A:SHA\n" +
            ":71F:USD10,00\n" +
            ":71F:USD5,00\n" +
            ":71F:USD5,00\n" +
            ":71G:USD10,00\n" +
            ":72:/INS/ABNANL2A\n" +
            "/INT/123456789012345678\n" +
            ":77B:/ORDERRES/BE//MEILAAN 1, 9000 GENT\n" +
            ":77T:/UEDI/UNH+123A5+FINPAY:D:98A:UN'DOC+\n" +
            "-}{5:{CHK:123456789ABC}{TNG:}{PDE:1348120811BANKFRPPAXXX2222123456}" +
            "{MRF:1806271539180626BANKFRPPAXXX2222123456}{DLM:}{PDM:1213120811BANKFRPPAXXX2222123456}" +
            "{SYS:1454120811BANKFRPPAXXX2222123456}}";

    @DataProvider(name = "invalidMT103MessageDataProvider")
    Object[][] getInvalidMT103MessageDataProvider() {

        return new Object[][] {
                {"{1:F01BANKFRPPAXXX4321123456}"},
                {"{2:O1030930180711BANKFRPPAXXX76351234560912300912N}"},
                {"{1:F01BANKFRPPAXXX4321123456}{5:{CHK:123456789ABC}{TNG:}}"}
        };
    }
}
