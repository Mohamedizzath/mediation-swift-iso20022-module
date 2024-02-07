package org.wso2.carbon.module.swiftiso20022.utils;

import org.testng.annotations.DataProvider;

import java.util.Map;

/**
 * Test constants for JsonToMT940Test.
 */
public class JsonToMT940TestConstants {

    public static final String PAYLOAD = "{\n" +
            "   \"block1\": \"F01CBORETAAXXXX22061ZFPHG\",\n" +
            "   \"block2\": \"I940XXXXXXXXXXXXN\",\n" +
            "   \"block3\": \"{108:22061ZFPHG97870}\",\n" +
            "   \"openingBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\",\n" +
            "       \"statementType\": \"current\"\n" +
            "   },\n" +
            "   \"closingBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\",\n" +
            "       \"statementType\": \"last\"\n" +
            "   },\n" +
            "   \"closingAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"forwardAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"reference\": \"20240105-4\",\n" +
            "   \"accountNumber\": \"BDN200000\",\n" +
            "   \"accountNumberIdentifier\": \"BDN200000\",\n" +
            "   \"sequenceNumber\": \"14\",\n" +
            "   \"transactions\": [\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\",\n" +
            "           \"supplementaryData\": \"Tests\",\n" +
            "           \"information\": \"Information to Account Owner\"\n" +
            "       },\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\"\n" +
            "       }\n" +
            "   ]\n" +
            "}\n";

    public static final String NULL_OPENING_BAL_PAYLOAD = "{\n" +
            "   \"block1\": \"F01CBORETAAXXXX22061ZFPHG\",\n" +
            "   \"block2\": \"I940XXXXXXXXXXXXN\",\n" +
            "   \"block3\": \"{108:22061ZFPHG97870}\",\n" +
            "   \"openingBalanceDetails\" : null,\n" +
            "   \"closingBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\",\n" +
            "       \"statementType\": \"last\"\n" +
            "   },\n" +
            "   \"closingAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"forwardAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"reference\": \"20240105-4\",\n" +
            "   \"accountNumber\": \"BDN200000\",\n" +
            "   \"accountNumberIdentifier\": \"BDN200000\",\n" +
            "   \"sequenceNumber\": \"14\",\n" +
            "   \"transactions\": [\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\",\n" +
            "           \"supplementaryData\": \"Tests\",\n" +
            "           \"information\": \"Information to Account Owner\"\n" +
            "       },\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\"\n" +
            "       }\n" +
            "   ]\n" +
            "}\n";

    public static final String NULL_CLOSING_BAL_PAYLOAD = "{\n" +
            "   \"block1\": \"F01CBORETAAXXXX22061ZFPHG\",\n" +
            "   \"block2\": \"I940XXXXXXXXXXXXN\",\n" +
            "   \"block3\": \"{108:22061ZFPHG97870}\",\n" +
            "   \"openingBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\",\n" +
            "       \"statementType\": \"current\"\n" +
            "   },\n" +
            "   \"closingBalanceDetails\" : null,\n" +
            "   \"closingAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"forwardAvailableBalanceDetails\" : {\n" +
            "       \"indicator\": \"D\",\n" +
            "       \"date\": \"230223\",\n" +
            "       \"balanceAmount\": \"215,306.49\",\n" +
            "       \"currency\": \"ETB\"\n" +
            "   },\n" +
            "   \"reference\": \"20240105-4\",\n" +
            "   \"accountNumber\": \"BDN200000\",\n" +
            "   \"accountNumberIdentifier\": \"BDN200000\",\n" +
            "   \"sequenceNumber\": \"14\",\n" +
            "   \"transactions\": [\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\",\n" +
            "           \"supplementaryData\": \"Tests\",\n" +
            "           \"information\": \"Information to Account Owner\"\n" +
            "       },\n" +
            "       {\n" +
            "           \"dateTime\": \"230405\",\n" +
            "           \"amount\": \"1,000.00\",\n" +
            "           \"transactionReference\": \"TT23328X147R\",\n" +
            "           \"transactionIndicator\": \"D\",\n" +
            "           \"currency\": \"ETB\",\n" +
            "           \"customerReference\": \"12h79eu1\",\n" +
            "           \"transactionType\": \"NMSC\"\n" +
            "       }\n" +
            "   ]\n" +
            "}\n";

    public static String getPayload(Map<String, String> params) {

        return "{\n" +
                "   \"block1\":" + (params.getOrDefault("block1", "\"F01CBORETAAXXXX22061ZFPHG\",\n")) +
                "   \"block2\":" + (params.getOrDefault("block2", "\"I940XXXXXXXXXXXXN\",\n")) +
                "   \"block3\":" + (params.getOrDefault("block3", "\"{108:22061ZFPHG97870}\",\n")) +
                "   \"openingBalanceDetails\" : {\n" +
                "       \"indicator\":" + (params.getOrDefault("openBalIndicator", "\"D\",\n")) +
                "       \"date\":" + (params.getOrDefault("openBalDate", "\"230223\",\n")) +
                "       \"balanceAmount\":" + (params.getOrDefault("openBalAmount",
                "\"215,306.49\",\n")) +
                "       \"currency\":" + (params.getOrDefault("openBalCurrency", "\"ETB\",\n")) +
                "       \"statementType\":" + (params.getOrDefault("openBalStatementType",
                "\"current\"\n")) +
                "   },\n" +
                "   \"closingBalanceDetails\" : {\n" +
                "       \"indicator\":" + (params.getOrDefault("closeBalIndicator", "\"D\",\n")) +
                "       \"date\":" + (params.getOrDefault("closeBalDate", "\"230223\",\n")) +
                "       \"balanceAmount\":" + (params.getOrDefault("closeBalAmount",
                "\"215,306.49\",\n")) +
                "       \"currency\":" + (params.getOrDefault("closeBalCurrency", "\"ETB\",\n")) +
                "       \"statementType\":" + (params.getOrDefault("closeBalStatementType",
                "\"current\"\n")) +
                "   },\n" +
                "   \"closingAvailableBalanceDetails\" : {\n" +
                "       \"indicator\":" + (params.getOrDefault("closeAvailBalIndicator", "\"D\",\n")) +
                "       \"date\":" + (params.getOrDefault("closeAvailBalDate", "\"230223\",\n")) +
                "       \"balanceAmount\":" + (params.getOrDefault("closeAvailBalAmount",
                "\"215,306.49\",\n")) +
                "       \"currency\":" + (params.getOrDefault("closeAvailBalCurrency", "\"ETB\"\n")) +
                "   },\n" +
                "   \"forwardAvailableBalanceDetails\" : {\n" +
                "       \"indicator\":" + (params.getOrDefault("forwardAvailBalIndicator",
                "\"D\",\n")) +
                "       \"date\":" + (params.getOrDefault("forwardAvailBalDate", "\"230223\",\n")) +
                "       \"balanceAmount\":" + (params.getOrDefault("forwardAvailBalAmount",
                "\"215,306.49\",\n")) +
                "       \"currency\":" + (params.getOrDefault("forwardAvailBalCurrency", "\"ETB\"\n")) +
                "   },\n" +
                "   \"reference\":" + (params.getOrDefault("reference", "\"20240105-4\",\n")) +
                "   \"accountNumber\":" + (params.getOrDefault("accountNumber", "\"BDN200000\",\n")) +
                "   \"accountNumberIdentifier\":" + (params.getOrDefault("accountNumberIdentifier",
                "\"BDN200000\",\n")) +
                "   \"sequenceNumber\":" + (params.getOrDefault("sequenceNumber", "\"14\",\n")) +
                "   \"transactions\": [\n" +
                "       {\n" +
                "           \"dateTime\":" + (params.getOrDefault("transactionDateTime",
                "\"230405\",\n")) +
                "           \"amount\":" + (params.getOrDefault("transactionAmount",
                "\"1000.00\",\n")) +
                "           \"transactionReference\":" + (params.getOrDefault("transactionReference",
                "\"TT23328X147R\",\n")) +
                "           \"transactionIndicator\":" + (params.getOrDefault("transactionIndicator",
                "\"D\",\n")) +
                "           \"currency\":" + (params.getOrDefault("transactionCurrency",
                "\"ETB\",\n")) +
                "           \"customerReference\":" + (params.getOrDefault("customerReference",
                "\"12h79eu1\",\n")) +
                "           \"transactionType\":" + (params.getOrDefault("transactionType",
                "\"NMSC\",\n")) +
                "           \"supplementaryData\":" + (params.getOrDefault("supplementaryData",
                "\"Test Data\",\n")) +
                "           \"information\":" + (params.getOrDefault("information",
                "\"Information to Account Owner\"\n")) +
                "       }\n" +
                "   ]\n" +
                "}\n";
    }

    @DataProvider(name = "invalidHeaderBlockDataProvider")
    Object[][] getInvalidHeaderBlockDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("block1", "\"\",\n"))},
                {getPayload(Map.of("block2", "\"\",\n"))}
        };
    }

    @DataProvider(name = "invalidOpeningBalDetailsDataProvider")
    Object[][] getInvalidOpeningBalDetailsDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("openBalIndicator", "\"\",\n"))},
                {getPayload(Map.of("openBalIndicator", "\"R\",\n"))},
                {getPayload(Map.of("openBalDate", "\"\",\n"))},
                {getPayload(Map.of("openBalDate", "\"2345\",\n"))},
                {getPayload(Map.of("openBalAmount", "\"\",\n"))},
                {getPayload(Map.of("openBalAmount", "\"456789098765432345678\",\n"))},
                {getPayload(Map.of("openBalCurrency", "\"\",\n"))},
                {getPayload(Map.of("openBalCurrency", "\"HND\",\n"))}
        };
    }

    @DataProvider(name = "invalidClosingBalDetailsDataProvider")
    Object[][] getInvalidClosingBalDetailsDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("closeBalIndicator", "\"\",\n"))},
                {getPayload(Map.of("closeBalIndicator", "\"R\",\n"))},
                {getPayload(Map.of("closeBalDate", "\"\",\n"))},
                {getPayload(Map.of("closeBalDate", "\"2345\",\n"))},
                {getPayload(Map.of("closeBalAmount", "\"\",\n"))},
                {getPayload(Map.of("closeBalAmount", "\"456789098765432345678\",\n"))},
                {getPayload(Map.of("closeBalCurrency", "\"\",\n"))},
                {getPayload(Map.of("closeBalCurrency", "\"HNB\",\n"))}
        };
    }

    @DataProvider(name = "invalidClosingAvailableBalDetailsDataProvider")
    Object[][] getInvalidClosingAvailableBalDetailsDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("closeAvailBalIndicator", "\"\",\n"))},
                {getPayload(Map.of("closeAvailBalIndicator", "\"R\",\n"))},
                {getPayload(Map.of("closeAvailBalDate", "\"\",\n"))},
                {getPayload(Map.of("closeAvailBalDate", "\"2345\",\n"))},
                {getPayload(Map.of("closeAvailBalAmount", "\"\",\n"))},
                {getPayload(Map.of("closeAvailBalAmount", "\"456789098765432345678\",\n"))},
                {getPayload(Map.of("closeAvailBalCurrency", "\"\"\n"))},
                {getPayload(Map.of("closeAvailBalCurrency", "\"HND\"\n"))}
        };
    }

    @DataProvider(name = "invalidForwardAvailBalDetailsDataProvider")
    Object[][] getInvalidForwardAvailBalDetailsDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("forwardAvailBalIndicator", "\"\",\n"))},
                {getPayload(Map.of("forwardAvailBalIndicator", "\"R\",\n"))},
                {getPayload(Map.of("forwardAvailBalDate", "\"\",\n"))},
                {getPayload(Map.of("forwardAvailBalDate", "\"2345\",\n"))},
                {getPayload(Map.of("forwardAvailBalAmount", "\"\",\n"))},
                {getPayload(Map.of("forwardAvailBalAmount", "\"456789098765432345678\",\n"))},
                {getPayload(Map.of("forwardAvailBalCurrency", "\"\"\n"))},
                {getPayload(Map.of("forwardAvailBalCurrency", "\"HND\"\n"))}
        };
    }

    @DataProvider(name = "invalidTransactionDetailsDataProvider")
    Object[][] getInvalidTransactionDetailsDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("transactionDateTime", "\"\",\n"))},
                {getPayload(Map.of("transactionDateTime", "\"2345\",\n"))},
                {getPayload(Map.of("transactionAmount", "\"\",\n"))},
                {getPayload(Map.of("transactionAmount", "\"45678909876543234567898765432\",\n"))},
                {getPayload(Map.of("transactionReference", "\"\",\n"))},
                {getPayload(Map.of("transactionReference", "\"45678909876543234567898765432\",\n"))},
                {getPayload(Map.of("transactionIndicator", "\"\",\n"))},
                {getPayload(Map.of("transactionIndicator", "\"T\",\n"))},
                {getPayload(Map.of("transactionCurrency", "\"\",\n"))},
                {getPayload(Map.of("transactionCurrency", "\"HND\",\n"))},
                {getPayload(Map.of("customerReference", "\"\",\n"))},
                {getPayload(Map.of("customerReference", "\"45678909876543234567898765432\",\n"))},
                {getPayload(Map.of("transactionType", "\"\",\n"))},
                {getPayload(Map.of("transactionType", "\"UTRE\",\n"))}
        };
    }

    @DataProvider(name = "invalidPayloadValuesDataProvider")
    Object[][] getInvalidPayloadValuesDataProvider() {

        return new Object[][]{
                {getPayload(Map.of("reference", "\"\",\n"))},
                {getPayload(Map.of("reference", "\"45678909876543234567898765432\",\n"))},
                {getPayload(Map.of("accountNumber", "\"\",\n"))},
                {getPayload(Map.of("accountNumber", "\"456789jbgrwquvgvuuyf76yf6ff6ftc3234567898765432\",\n"))},
                {getPayload(Map.of("sequenceNumber", "\"\",\n"))},
                {getPayload(Map.of("sequenceNumber", "\"1234567\",\n"))}
        };
    }
}
