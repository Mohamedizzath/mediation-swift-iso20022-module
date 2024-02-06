package org.wso2.carbon.module.swiftiso20022.utils;

import org.testng.annotations.DataProvider;

/**
 * Constants for the MT940FormatValidator test class.
 */
public class MT940FormatValidatorTestConstants {

    public static final String PAYLOAD = "{1:F01CBORETAAXXXX22061ZFPHG}{2:I940XXXXXXXXXXXXN}" +
            "{3:{108:22061ZFPHG97870}}{4:\n" +
            ":20:20240105-4\n" +
            ":25:BDN200000\n" +
            ":28C:16/16\n" +
            ":60F:D20230223ETB215306,49\n" +
            ":61:202304050405DB1000,00NMSC12h79eu1//TT23328X147R\n" +
            ":86:Information to Account Owner\n" +
            ":61:202304050405DB1000,00NMSC12h79eu1//TT23328X147R\n" +
            ":62F:D20230223ETB217306,49\n" +
            ":64:D20230223ETB215306,49\n" +
            ":65:D20230223ETB215306,49\n" +
            "-}\n";


    @DataProvider(name = "invalidReferenceDataProvider")
    Object[][] getInvalidReferenceDataProvider() {

        return new Object[][]{
                {":20: "},
                {":20:tqibyrtbgergqohgfheuigbFWV743FEUWFB7G34BFVGRYGRWnaehrghhubarh"},
                {":20:test//"},
                {":20:test}"}
        };
    }

    @DataProvider(name = "invalidAccountIdentifierDataProvider")
    Object[][] getInvalidAccountIdentifierDataProvider() {

        return new Object[][]{
                {":25: "},
                {":25:tqibyrtbgergqohgfheuigbFWV743FEUWFB7G34BFVGRYGjbegjbqebubgbquebbdhaRWnaehrghhubarh"},
                {":25:test}"}
        };
    }

    @DataProvider(name = "invalidStatementNumberDataProvider")
    Object[][] getInvalidStatementNumberDataProvider() {

        return new Object[][]{
                {":28C: "},
                {":28C:12345/3456"},
                {":28C:12345"},
                {":28C:123/45"}
        };
    }

    @DataProvider(name = "invalidBalanceDataProvider")
    Object[][] getInvalidBalanceDataProvider() {

        return new Object[][]{
                {":60F: "},
                {":60F:1234534567537056031767589432984756473892"},
                {":60F:D12345456789034567898765433487654658616813865868318683683168618303634683610"},
                {":60F:D12356789276567892764567319.45"}
        };
    }
}
