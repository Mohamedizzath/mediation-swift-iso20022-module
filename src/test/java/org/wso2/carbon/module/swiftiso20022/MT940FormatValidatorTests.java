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
import org.wso2.carbon.module.swiftiso20022.constants.MT940Constants;
import org.wso2.carbon.module.swiftiso20022.utils.ConnectorUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940FormatValidatorTestConstants;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ValidationUtils;
import org.wso2.carbon.module.swiftiso20022.validation.common.ValidationResult;

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
        ValidationResult validationResult = MT940ValidationUtils.validateReference(reference,
                MT940Constants.DN_TRANSACTION_REFERENCE);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidReferenceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateRelatedReference(String reference) {
        ValidationResult validationResult = MT940ValidationUtils.validateReference(reference,
                MT940Constants.DN_RELATED_REF);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidAccountIdentifierDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateAccountIdentifier(String account) {
        ValidationResult validationResult = MT940ValidationUtils.validateAccountIdentifier(account);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidStatementNumberDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateStatementNumber(String statementNumber) {
        ValidationResult validationResult = MT940ValidationUtils.validateStatementNumber(statementNumber);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateOpeningBalance(String balance) {
        ValidationResult validationResult = MT940ValidationUtils.validateOpeningBalance(balance);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateClosingBalance(String balance) {
        ValidationResult validationResult = MT940ValidationUtils.validateClosingBalance(balance);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateClosingAvailableBalance(String balance) {
        ValidationResult validationResult = MT940ValidationUtils.validateClosingAvailableBalance(balance);

        Assert.assertFalse(validationResult.isValid());
    }

    @Test(dataProvider = "invalidBalanceDataProvider",
            dataProviderClass = MT940FormatValidatorTestConstants.class)
    public void testValidateForwardAvailableBalance(String balance) {
        ValidationResult validationResult = MT940ValidationUtils.validateForwardAvailableBalance(balance);

        Assert.assertFalse(validationResult.isValid());
    }

}
