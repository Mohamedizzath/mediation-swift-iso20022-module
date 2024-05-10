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

package org.wso2.carbon.module.swiftiso20022.utils;

import org.testng.annotations.DataProvider;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.mt.parsers.MT940Parser;

import java.util.List;
import java.util.Map;

/**
 * Contains test constants for testing gson transformer methods.
 */
public class MT940ToJSONTransformerTestConstants {
    @DataProvider(name = "removeFieldOption")
    Object[][] removeFieldOption() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD846665,15\n" +
                                ":64:C231002USD334432401,27\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n-}"))))}
        };
    }

    @DataProvider(name = "updateBalanceAmounts")
    Object[][] updateBalanceAmounts() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C231002USD843686,20\n" +
                                ":65:C231002USD843686,20\n-}")))), 843686.20},
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD0,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD0,20\n" +
                                ":64:C231002USD0,20\n" +
                                ":65:C231002USD0,20\n-}")))), 0.20},
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD4569078,\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD4569078,\n" +
                                ":64:C231002USD4569078,\n" +
                                ":65:C231002USD4569078,\n-}")))), 4569078.00}
        };
    }

    @DataProvider(name = "updateBalanceDates")
    Object[][] updateBalanceDates() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                "{4:\n:20:258158850\n" +
                        ":21:258158850\n" +
                        ":25:DD01100056869\n" +
                        ":28C:1/1\n" +
                        ":60F:D230930USD843686,20\n" +
                        ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                        ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                        ":61:2310011001DD10,00ACHPNONREF\n" +
                        ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                        ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                        ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                        ":62F:D230930USD843686,20\n" +
                        ":64:C230930USD843686,20\n" +
                        ":65:C230930USD843686,20\n-}")))), "2023-09-30"},
        };
    }

    @DataProvider(name = "updateStatementAmounts")
    Object[][] updateStatementAmounts() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C230930USD843686,20\n" +
                                ":65:C230930USD843686,20\n-}")))), List.of(10.00, 10.00, 10.00)},
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD789,ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD789,ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD789,ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C230930USD843686,20\n" +
                                ":65:C230930USD843686,20\n-}")))), List.of(789.00, 789.00, 789.00)},
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD0,789ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001DD0,789ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2310011001CD0,789ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C230930USD843686,20\n" +
                                ":65:C230930USD843686,20\n-}")))), List.of(0.789, 0.789, 0.789)}
        };
    }

    @DataProvider(name = "updateStatementValueDate")
    Object[][] updateStatementValueDate() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2212301001DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:1706301001CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C230930USD843686,20\n" +
                                ":65:C230930USD843686,20\n-}")))), List.of("2023-10-01", "2022-12-30", "2017-06-30")},
        };
    }

    @DataProvider(name = "updateStatementEntryDate")
    Object[][] updateStatementEntryDate() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(ConnectorConstants.TEXT_BLOCK_KEY,
                        "{4:\n:20:258158850\n" +
                                ":21:258158850\n" +
                                ":25:DD01100056869\n" +
                                ":28C:1/1\n" +
                                ":60F:D230930USD843686,20\n" +
                                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:2212301230DD10,00ACHPNONREF\n" +
                                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":61:1706300630CD10,00ASHP20230928LTERMID2\n" +
                                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                                ":62F:D230930USD843686,20\n" +
                                ":64:C230930USD843686,20\n" +
                                ":65:C230930USD843686,20\n-}")))), List.of("2024-10-01", "2024-12-30", "2024-06-30")},
        };
    }

    @DataProvider(name = "updateFromBICNumber")
    Object[][] updateFromBICNumber() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(
                        ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                 "{2:O9400400190425GSCRUS30MPXT00000000002403290912N}")))), "GSCRUS30PXT"},
        };
    }

    @DataProvider(name = "updateToBICNumber")
    Object[][] updateToBICNumber() throws Exception {
        return new Object[][] {
                {MT940Parser.parse(MTParserTestConstants.getMTMessageText((Map.of(
                        ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        "{1:F01GSCRUS30MPXT0000000000}")))), "GSCRUS30PXT"},
        };
    }
}
