package org.wso2.carbon.module.swiftiso20022;

import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.context.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940FormatValidatorTestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ValidationUtils;

/**
 * Test class for MT940FormatValidator.
 */
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ConnectorUtils.class})
public class MT940FormatValidatorTests extends PowerMockTestCase {

    private MessageContext messageContext;
    MT940FormatValidator mt940FormatValidator = new MT940FormatValidator();

    @BeforeClass
    public void init() {
        messageContext = Mockito.spy(MessageContext.class);
    }

    @Test
    public void testMT940FormatValidator() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        SOAPEnvelope soapEnvelope = Mockito.spy(SOAPEnvelope.class);
        Mockito.doReturn(soapEnvelope).when(msgCtx).getEnvelope();
        SOAPBody soapBody = Mockito.spy(SOAPBody.class);
        Mockito.doReturn(soapBody).when(soapEnvelope).getBody();
        Mockito.doReturn(MT940FormatValidatorTestConstants.PAYLOAD).when(soapBody).toString();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        mt940FormatValidator.connect(msgCtx);
    }

    @Test
    public void testMT940FormatValidatorForAxis2Payload() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        SOAPEnvelope soapEnvelope = Mockito.spy(SOAPEnvelope.class);
        Mockito.doReturn(soapEnvelope).when(msgCtx).getEnvelope();
        SOAPBody soapBody = Mockito.spy(SOAPBody.class);
        Mockito.doReturn(soapBody).when(soapEnvelope).getBody();
        Mockito.doReturn(MT940FormatValidatorTestConstants.AXIS2_PAYLOAD).when(soapBody).toString();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        mt940FormatValidator.connect(msgCtx);
    }

    @Test(expectedExceptions = SynapseException.class)
    public void testMT940FormatValidatorForInvalidPayload() throws Exception {

        Axis2MessageContext msgCtx = Mockito.mock(Axis2MessageContext.class);
        SOAPEnvelope soapEnvelope = Mockito.spy(SOAPEnvelope.class);
        Mockito.doReturn(soapEnvelope).when(msgCtx).getEnvelope();
        SOAPBody soapBody = Mockito.spy(SOAPBody.class);
        Mockito.doReturn(soapBody).when(soapEnvelope).getBody();
        Mockito.doReturn(MT940FormatValidatorTestConstants.INVALID_PAYLOAD).when(soapBody).toString();

        PowerMockito.mockStatic(ConnectorUtils.class);
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendJsonResponseToMessageContext",
                Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(ConnectorUtils.class, "appendErrorToMessageContext",
                Mockito.any(), Mockito.anyString(), Mockito.anyString());

        mt940FormatValidator.connect(msgCtx);
    }

    @Test(dataProvider = "invalidReferenceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateTransactionReference(String reference) {
        ErrorModel error = MT940ValidationUtils.validateReference(reference,
                ConnectorConstants.TRANSACTION_REF);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidReferenceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateRelatedReference(String reference) {
        ErrorModel error = MT940ValidationUtils.validateReference(reference, ConnectorConstants.RELATED_REF);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidAccountIdentifierDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateAccountIdentifier(String account) {
        ErrorModel error = MT940ValidationUtils.validateAccountIdentifier(account);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidStatementNumberDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateStatementNumber(String statementNumber) {
        ErrorModel error = MT940ValidationUtils.validateStatementNumber(statementNumber);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateOpeningBalance(String balance) {
        ErrorModel error = MT940ValidationUtils.validateOpeningBalance(balance);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateClosingBalance(String balance) {
        ErrorModel error = MT940ValidationUtils.validateClosingBalance(balance);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateClosingAvailableBalance(String balance) {
        ErrorModel error = MT940ValidationUtils.validateClosingAvailableBalance(balance);

        Assert.assertTrue(error.isError());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateForwardAvailableBalance(String balance) {
        ErrorModel error = MT940ValidationUtils.validateForwardAvailableBalance(balance);

        Assert.assertTrue(error.isError());
    }

}
