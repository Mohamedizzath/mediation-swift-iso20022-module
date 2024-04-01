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
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MT940Message;
import org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages.MTMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Test constants for MTParser.
 */
public class MTParserConstants {
    public static String getBasicHeaderBlockText(Map<String, String> params) {
        return  (params.getOrDefault("AppID", "F")) +
                (params.getOrDefault("ServiceID", "01")) +
                (params.getOrDefault("LTAddress", "GSCRUS30XXXX")) +
                (params.getOrDefault("SessionNumber", "0000")) +
                (params.getOrDefault("SequenceNumber", "000000"));
    }

    public static String getApplicationHeaderBlockText(Map<String, String> params) {
        if (params.containsKey("IOID") && "I".equals(params.get("IOID"))) {
            // Given message is input message
            return  (params.getOrDefault("IOID", "I")) +
                    (params.getOrDefault("MessageType", "103")) +
                    (params.getOrDefault("DestinationAddress", "GSCRUS30XXXX")) +
                    (params.getOrDefault("Priority", "U")) +
                    (params.getOrDefault("DeliveryMonitor", "3")) +
                    (params.getOrDefault("ObsolescencePeriod", "003"));
        } else {
            // Given message is output message(default)
            return  (params.getOrDefault("IOID", "O")) +
                    (params.getOrDefault("MessageType", "940")) +
                    (params.getOrDefault("InputTime", "0400")) +
                    (params.getOrDefault("MessageInputReference", "190425GSCRUS30XXXX0000000000")) +
                    (params.getOrDefault("OutputDate", "240327")) +
                    (params.getOrDefault("OutputTime", "1128")) +
                    (params.getOrDefault("Priority", "N"));
        }
    }

    public static Map<String, String> getMTMessageMap(Map<String, String> params) {
        Map<String, String> blocks = new HashMap<>();

        if (params.containsKey(ConnectorConstants.BASIC_HEADER_BLOCK_KEY)) {
            blocks.put(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                    params.get(ConnectorConstants.BASIC_HEADER_BLOCK_KEY));
        }

        if (params.containsKey(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY)) {
            blocks.put(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                    params.get(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY));
        }

        if (params.containsKey(ConnectorConstants.USER_HEADER_BLOCK_KEY)) {
            blocks.put(ConnectorConstants.USER_HEADER_BLOCK_KEY,
                    params.get(ConnectorConstants.USER_HEADER_BLOCK_KEY));
        }

        if (params.containsKey(ConnectorConstants.TEXT_BLOCK_KEY)) {
            blocks.put(ConnectorConstants.TEXT_BLOCK_KEY,
                    params.get(ConnectorConstants.TEXT_BLOCK_KEY));
        }

        if (params.containsKey(ConnectorConstants.TRAILER_BLOCK_KEY)) {
            blocks.put(ConnectorConstants.TRAILER_BLOCK_KEY,
                    params.get(ConnectorConstants.TRAILER_BLOCK_KEY));
        }

        return blocks;
    }

    public static BasicHeaderBlock getBasicHeaderBlock(Map<String, String> params) {
        BasicHeaderBlock basicHeaderBlock = new BasicHeaderBlock();

        basicHeaderBlock.setApplicationIdentifier(params.getOrDefault("AppID", null));
        basicHeaderBlock.setServiceIdentifier(params.getOrDefault("ServiceID", null));
        basicHeaderBlock.setLogicalTerminalAddress(params.getOrDefault("LTAddress", null));
        basicHeaderBlock.setSessionNumber(params.getOrDefault("SessionNumber", null));
        basicHeaderBlock.setSequenceNumber(params.getOrDefault("SequenceNumber", null));

        return basicHeaderBlock;
    }

    public static ApplicationHeaderBlock getApplicationHeaderBlock(Map<String, String> params) {
        ApplicationHeaderBlock applicationHeaderBlock = new ApplicationHeaderBlock();

        applicationHeaderBlock.setInputOutputIdentifier(params.getOrDefault("IOID", null));
        applicationHeaderBlock.setMessageType(params.getOrDefault("MessageType", null));
        // Input message related blocks
        applicationHeaderBlock.setDestinationAddress(params.getOrDefault("DestinationAddress", null));
        applicationHeaderBlock.setPriority(params.getOrDefault("Priority", null));
        applicationHeaderBlock.setDeliveryMonitor(params.getOrDefault("DeliveryMonitor", null));
        applicationHeaderBlock.setObsolescencePeriod(params.getOrDefault("ObsolescencePeriod", null));
        // Output message related blocks
        applicationHeaderBlock.setInputTime(params.getOrDefault("InputTime", null));
        applicationHeaderBlock.setMessageInputReference(params.getOrDefault("MessageInputReference",
                null));
        applicationHeaderBlock.setOutputDate(params.getOrDefault("OutputDate", null));
        applicationHeaderBlock.setOutputTime(params.getOrDefault("OutputTime", null));
        // Setting the priority
        applicationHeaderBlock.setPriority(params.getOrDefault("Priority", null));

        return applicationHeaderBlock;
    }

    public static <T extends MTMessage> T getMTMessage(Map<String, Object> params, Class<T> messageType)
            throws Exception {
        T message = messageType.getConstructor().newInstance();

        message.setBasicHeaderBlock((BasicHeaderBlock) params.getOrDefault(
                ConnectorConstants.BASIC_HEADER_BLOCK_KEY, null));
        message.setApplicationHeaderBlock((ApplicationHeaderBlock) params.getOrDefault(
                ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY, null));

        return message;
    }

    public static String getMTMessageText(Map<String, String> params) {
        return  (params.getOrDefault(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                "{1:F01GSCRUS30XXXX0000000000:}")) +
                (params.getOrDefault(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                "{2:O9400400190425GSCRUS30XXXX00000000002403290912N}")) +
                (params.getOrDefault(ConnectorConstants.USER_HEADER_BLOCK_KEY, "{3:{113:URGT}}")) +
                (params.getOrDefault(ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                ":21:258158850\n" +
                ":25:DD01100056869\n" +
                ":28C:1/1\n" +
                ":60F:D230930USD843686,20\n" +
                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001DD10,00ACHPNONREF\n" +
                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001CD10,00ASHP20230928LTERMID2000003\n" +
                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":62F:D230930USD846665,15\n" +
                ":64:C231002USD334432401,27\n-}")) +
                (params.getOrDefault(ConnectorConstants.TRAILER_BLOCK_KEY, "{5:{CHK:123456789ABC}}"));
    }

    @DataProvider(name = "parseMTMessage")
    Object[][] parseMTMessage() throws Exception {
        return new Object[][] {
                {getMTMessageText(Map.of()),
                 getMTMessage(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                         getBasicHeaderBlock(Map.of(
                         "AppID", "F", "ServiceID", "01", "LTAddress", "GSCRUS30XXXX",
                         "SessionNumber", "0000", "SequenceNumber", "000000")),
                         ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                         getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                 "InputTime", "0400", "MessageInputReference",
                                 "190425GSCRUS30XXXX0000000000", "OutputDate", "240329"
                                 , "OutputTime", "0912", "Priority", "N"))), MT940Message.class)
                },
                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "")),
                        getMTMessage(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                                getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                        "InputTime", "0400", "MessageInputReference",
                                        "190425GSCRUS30XXXX0000000000", "OutputDate", "240329"
                                        , "OutputTime", "0912", "Priority", "N"))), MT940Message.class)
                },
                {getMTMessageText(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY, "")),
                        getMTMessage(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                                getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                        "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000",
                                        "SequenceNumber", "000000"))), MT940Message.class)
                },
                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "",
                                        ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY, "")),
                        getMTMessage(Map.of(), MT940Message.class)
                },
        };
    }

    @DataProvider(name = "parseMTMessageBlocks")
    Object[][] parseMTMessageBlocks() {
        return new Object[][] {
                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                "F01GSCRUS30XXXX0000000000",
                ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                "O9400400190425GSCRUS30XXX00000000002403290912N")),
                getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                "F01GSCRUS30XXXX0000000000",
                ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                "O9400400190425GSCRUS30XXX00000000002403290912N",
                ConnectorConstants.USER_HEADER_BLOCK_KEY, "{3:{113:URGT}}", ConnectorConstants.TEXT_BLOCK_KEY,
                "{4:\n:20:258158850\n" +
                ":21:258158850\n" +
                ":25:DD01100056869\n" +
                ":28C:1/1\n" +
                ":60F:D230930USD843686,20\n" +
                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001DD10,00ACHPNONREF\n" +
                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001CD10,00ASHP20230928LTERMID2000003\n" +
                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":62F:D230930USD846665,15\n" +
                ":64:C231002USD334432401,27\n-}",
                ConnectorConstants.TRAILER_BLOCK_KEY, "{5:{CHK:123456789ABC}}"))},

                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "",
                ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY, "")),
                getMTMessageMap(Map.of(ConnectorConstants.USER_HEADER_BLOCK_KEY, "{3:{113:URGT}}",
                ConnectorConstants.TEXT_BLOCK_KEY, "{4:\n:20:258158850\n" +
                ":21:258158850\n" +
                ":25:DD01100056869\n" +
                ":28C:1/1\n" +
                ":60F:D230930USD843686,20\n" +
                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001DD10,00ACHPNONREF\n" +
                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001CD10,00ASHP20230928LTERMID2000003\n" +
                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":62F:D230930USD846665,15\n" +
                ":64:C231002USD334432401,27\n-}",
                ConnectorConstants.TRAILER_BLOCK_KEY, "{5:{CHK:123456789ABC}}"))},

                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY, "",
                ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                "O9400400190425GSCRUS30XXX00000000002403290912N")),
                getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                "O9400400190425GSCRUS30XXX00000000002403290912N",
                ConnectorConstants.USER_HEADER_BLOCK_KEY, "{3:{113:URGT}}", ConnectorConstants.TEXT_BLOCK_KEY,
                "{4:\n:20:258158850\n" +
                ":21:258158850\n" +
                ":25:DD01100056869\n" +
                ":28C:1/1\n" +
                ":60F:D230930USD843686,20\n" +
                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001DD10,00ACHPNONREF\n" +
                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001CD10,00ASHP20230928LTERMID2000003\n" +
                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":62F:D230930USD846665,15\n" +
                ":64:C231002USD334432401,27\n-}",
                ConnectorConstants.TRAILER_BLOCK_KEY, "{5:{CHK:123456789ABC}}"))},

                {getMTMessageText(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                "F01GSCRUS30XXXX0000000000")),
                getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                "F01GSCRUS30XXXX0000000000",
                ConnectorConstants.USER_HEADER_BLOCK_KEY, "{3:{113:URGT}}", ConnectorConstants.TEXT_BLOCK_KEY,
                "{4:\n:20:258158850\n" +
                ":21:258158850\n" +
                ":25:DD01100056869\n" +
                ":28C:1/1\n" +
                ":60F:D230930USD843686,20\n" +
                ":61:2310011001RCD10,00ACHPGSGWGDNCTAHQM8\n" +
                ":86:EREF/GSGWGDNCTAHQM8/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001DD10,00ACHPNONREF\n" +
                ":86:PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":61:2310011001CD10,00ASHP20230928LTERMID2000003\n" +
                ":86:EREF/20230928LTERMID2000003/PREF/RP/GS/CTFILERP0002/CTBA0003\n" +
                ":62F:D230930USD846665,15\n" +
                ":64:C231002USD334432401,27\n-}",
                ConnectorConstants.TRAILER_BLOCK_KEY, "{5:{CHK:123456789ABC}}"))}
        };
    }

    @DataProvider(name = "parserBasicHeaderApplicationID")
    Object[][] parseBasicHeaderApplicationID() {
        return new Object[][] {
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("AppID", "F")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000",
                                "SequenceNumber", "000000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("AppID", "", "ServiceID", "",
                                "LTAddress", "", "SessionNumber", "", "SequenceNumber", "")))),
                        getBasicHeaderBlock(Map.of())},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("AppID", "")))),
                        getBasicHeaderBlock(Map.of("AppID", "0", "ServiceID", "1G",
                                "LTAddress", "SCRUS30XXXX0", "SessionNumber", "0000",
                                "SequenceNumber", "00000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("AppID", "CDGK")))),
                        getBasicHeaderBlock(Map.of("AppID", "C", "ServiceID", "DG",
                                "LTAddress", "K01GSCRUS30X", "SessionNumber", "XXX0",
                                "SequenceNumber", "000000"))}

        };
    }

    @DataProvider(name = "parserBasicHeaderServiceID")
    Object[][] parseBasicHeaderServiceID() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("ServiceID", "")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "GS",
                                "LTAddress", "CRUS30XXXX00", "SessionNumber", "0000",
                                "SequenceNumber", "0000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("ServiceID", "0")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "0G",
                                "LTAddress", "SCRUS30XXXX0", "SessionNumber", "0000",
                                "SequenceNumber", "00000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("ServiceID", "011")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "1GSCRUS30XXX", "SessionNumber", "X000",
                                "SequenceNumber", "000000"))},
        };
    }

    @DataProvider(name = "parserBasicHeaderLTAddress")
    Object[][] parseBasicHeaderLTAddress() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("LTAddress", "")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "0000000000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("LTAddress", "GSCRUS30")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS300000", "SessionNumber", "0000",
                                "SequenceNumber", "00"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("LTAddress", "GSCRUS30XXXX324")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "3240",
                                "SequenceNumber", "000000"))}
        };
    }

    @DataProvider(name = "parserBasicHeaderSessionNumber")
    Object[][] parseBasicHeaderSessionNumber() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SessionNumber", "")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000",
                                "SequenceNumber", "00"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SessionNumber", "12")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "1200",
                                "SequenceNumber", "0000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SessionNumber", "243212")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "2432",
                                "SequenceNumber", "120000"))}
        };
    }

    @DataProvider(name = "parserBasicHeaderSequenceNumber")
    Object[][] parseBasicHeaderSequenceNumber() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SequenceNumber", "")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SequenceNumber", "1290")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000",
                                "SequenceNumber", "1290"))},
                {getMTMessageMap(Map.of(ConnectorConstants.BASIC_HEADER_BLOCK_KEY,
                        getBasicHeaderBlockText(Map.of("SequenceNumber", "32119012")))),
                        getBasicHeaderBlock(Map.of("AppID", "F", "ServiceID", "01",
                                "LTAddress", "GSCRUS30XXXX", "SessionNumber", "0000",
                                "SequenceNumber", "321190"))}
        };
    }

    @DataProvider(name = "parserApplicationHeaderInputOutputID")
    Object[][] parseApplicationHeaderInputOutputID() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "O")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "", "MessageType", "",
                                "DestinationAddress", "", "Priority", "",
                                "DeliveryMonitor", "", "ObsolescencePeriod", "", "InputTime", "",
                                "MessageInputReference", "", "OutputDate", "",
                                "OutputTime", "")))),
                        getApplicationHeaderBlock(Map.of())},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "9", "MessageType", "400"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "KT")))),
                        getApplicationHeaderBlock(Map.of("IOID", "K", "MessageType", "T94"))},
        };
    }

    @DataProvider(name = "parserApplicationHeaderMessageType")
    Object[][] parseApplicationHeaderMessageType() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "MessageType", "103")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageType", "940")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "MessageType", "10")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "10G",
                                "DestinationAddress", "SCRUS30XXXXU",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageType", "94")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "4001", "MessageInputReference",
                                "90425GSCRUS30XXXX00000000002", "OutputDate", "403271"
                                , "OutputTime", "128N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "MessageType", "1033")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "3GSCRUS30XXX", "Priority", "X"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageType", "9440")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "944",
                                "InputTime", "0040",  "MessageInputReference",
                                "0190425GSCRUS30XXXX000000000", "OutputDate", "024032"
                                , "OutputTime", "7112"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "MessageType", "",
                                "DestinationAddress", "", "Priority", "",
                                "DeliveryMonitor", "", "ObsolescencePeriod", "", "InputTime", "",
                                "MessageInputReference", "", "OutputDate", "",
                                "OutputTime", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "O", "MessageType", "",
                                "DestinationAddress", "", "Priority", "",
                                "DeliveryMonitor", "", "ObsolescencePeriod", "", "InputTime", "",
                                "MessageInputReference", "", "OutputDate", "",
                                "OutputTime", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O"))}

        };
    }

    @DataProvider(name = "parserApplicationHeaderPriority")
    Object[][] parserApplicationHeaderPriority() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "Priority", "U")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("Priority", "N")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "Priority", "5")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "DeliveryMonitor", "5",
                                "ObsolescencePeriod", "300"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("Priority", "8")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327",
                                "OutputTime", "1128"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "Priority", "NT")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("Priority", "MP")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "M"))}

        };
    }

    @DataProvider(name = "parserApplicationHeaderDestinationAddress")
    Object[][] parserApplicationHeaderDestinationAddress() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I",
                                "DestinationAddress", "GSCRUS30XXXX")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DestinationAddress", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "U3003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I",
                                "DestinationAddress", "GSCRUS30XXXX1PTX")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "DeliveryMonitor", "1"))}

        };
    }

    @DataProvider(name = "parserApplicationHeaderDeliveryMonitor")
    Object[][] parserApplicationHeaderDeliveryMonitor() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DeliveryMonitor", "3")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DeliveryMonitor", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DeliveryMonitor", "P")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DeliveryMonitor", "DTP")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "DeliveryMonitor", "124")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "1", "ObsolescencePeriod", "240"))}
        };
    }

    @DataProvider(name = "parserApplicationHeaderObsolescencePeriod")
    Object[][] parserApplicationHeaderObsolescencePeriod() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "ObsolescencePeriod", "003")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "003"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "ObsolescencePeriod", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "ObsolescencePeriod", "32345")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3", "ObsolescencePeriod", "323"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("IOID", "I", "ObsolescencePeriod", "TYP")))),
                        getApplicationHeaderBlock(Map.of("IOID", "I", "MessageType", "103",
                                "DestinationAddress", "GSCRUS30XXXX", "Priority", "U",
                                "DeliveryMonitor", "3"))},
        };
    }

    @DataProvider(name = "parserApplicationHeaderInputTime")
    Object[][] parserApplicationHeaderInputTime() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("InputTime", "0400")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("InputTime", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "1904", "MessageInputReference",
                                "25GSCRUS30XXXX00000000002403", "OutputDate", "271128", "OutputTime", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("InputTime", "04")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0419", "MessageInputReference",
                                "0425GSCRUS30XXXX000000000024", "OutputDate", "032711"
                                , "OutputTime", "28N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("InputTime", "042343")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0423", "MessageInputReference",
                                "43190425GSCRUS30XXXX00000000", "OutputDate", "002403"
                                , "OutputTime", "2711"))}
        };
    }

    @DataProvider(name = "parserApplicationHeaderMessageInputReference")
    Object[][] parserApplicationHeaderMessageInputReference() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageInputReference",
                                "190425GSCRUS30XXXX0000000000")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageInputReference", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "2403271128N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageInputReference",
                                "190425GSCRUS30XXXX")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX2403271128", "OutputDate", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageInputReference",
                                "25GSCRUS30XXXX0000000000")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "25GSCRUS30XXXX00000000002403", "OutputDate", "271128"
                                , "OutputTime", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("MessageInputReference",
                                "190425GSCRUS30XXXX0000000000PQRT")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "PQRT24"
                                , "OutputTime", "0327"))},
        };
    }

    @DataProvider(name = "parserApplicationHeaderOutputDate")
    Object[][] parserApplicationHeaderOutputDate() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputDate", "240327")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputDate", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "1128N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputDate", "240")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240112"
                                , "OutputTime", "8N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputDate", "24032712")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1211"))},
        };
    }

    @DataProvider(name = "parserApplicationHeaderOutputTime")
    Object[][] parserApplicationHeaderOutputTime() {
        return new Object[][]{
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputTime", "1128")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128", "Priority", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputTime", "")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputTime", "11")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "11N"))},
                {getMTMessageMap(Map.of(ConnectorConstants.APPLICATION_HEADER_BLOCK_KEY,
                        getApplicationHeaderBlockText(Map.of("OutputTime", "112878")))),
                        getApplicationHeaderBlock(Map.of("IOID", "O", "MessageType", "940",
                                "InputTime", "0400", "MessageInputReference",
                                "190425GSCRUS30XXXX0000000000", "OutputDate", "240327"
                                , "OutputTime", "1128"))},
        };
    }
}

