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

import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.Field434;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldSYS;

import java.util.Arrays;

/**
 * Utility class for parsing MT message fields.
 */
public class MTFieldParserUtils {

    /**
     * Method to parse and get Field103 object.
     *
     * @param field103String String containing value of 103 field in User Header Block
     * @return A {@link Field103} with values assigned from the passed string.
     */
    public static Field103 parseField103(String field103String) {
        return new Field103()
                .withValue(field103String);
    }

    /**
     * Method to parse and get Field103 object.
     *
     * @param field106String String containing value of 106 field in User Header Block
     * @return A {@link Field106} with values assigned from the passed string.
     */
    public static Field106 parseField106(String field106String) {

        // extract and assign values from field106String
        return new Field106()
                .withDate(field106String.substring(
                        MTParserConstants.FIELD_106_DATE_START, MTParserConstants.FIELD_106_DATE_END))
                .withLogicalTerminalAddress(field106String.substring(
                        MTParserConstants.FIELD_106_LT_ADDRESS_START, MTParserConstants.FIELD_106_LT_ADDRESS_END))
                .withSessionNumber(field106String.substring(
                        MTParserConstants.FIELD_106_SESSION_NO_START, MTParserConstants.FIELD_106_SESSION_NO_END))
                .withSequenceNumber(field106String.substring(
                        MTParserConstants.FIELD_106_SEQUENCE_NO_START));
    }

    /**
     * Method to parse and get Field108 object.
     *
     * @param field108String String containing value of 108 field in User Header Block
     * @return A {@link Field108} with values assigned from the passed string.
     */
    public static Field108 parseField108(String field108String) {
        return new Field108()
                .withValue(field108String);
    }

    /**
     * Method to parse and get Field111 object.
     *
     * @param field111String String containing value of 111 field in User Header Block
     * @return A {@link Field111} with values assigned from the passed string.
     */
    public static Field111 parseField111(String field111String) {
        return new Field111()
                .withValue(field111String);
    }

    /**
     * Method to parse and get Field113 object.
     *
     * @param field113String String containing value of 113 field in User Header Block
     * @return A {@link Field113} with values assigned from the passed string.
     */
    public static Field113 parseField113(String field113String) {
        return new Field113()
                .withValue(field113String);
    }

    /**
     * Method to parse and get Field115 object.
     *
     * @param field115String String containing value of 115 field in User Header Block
     * @return A {@link Field115} with values assigned from the passed string.
     */
    public static Field115 parseField115(String field115String) {

        // extract and assign values from field115String
        return new Field115()
                .withCreditingTime(field115String.substring(
                        MTParserConstants.FIELD_115_CREDITING_TIME_START,
                        MTParserConstants.FIELD_115_CREDITING_TIME_END))
                .withDebitingTime(field115String.substring(
                        MTParserConstants.FIELD_115_DEBITING_TIME_START, MTParserConstants.FIELD_115_DEBITING_TIME_END))
                .withCountryCode(field115String.substring(
                        MTParserConstants.FIELD_115_COUNTRY_CODE_START, MTParserConstants.FIELD_115_COUNTRY_CODE_END))
                .withReference(field115String.substring(MTParserConstants.FIELD_115_REFERENCE_START));
    }

    /**
     * Method to parse and get Field119 object.
     *
     * @param field119String String containing value of 103 field in User Header Block
     * @return A {@link Field119} with values assigned from the passed string.
     */
    public static Field119 parseField119(String field119String) {
        return new Field119()
                .withValue(field119String);
    }

    /**
     * Method to parse and get Field121 object.
     *
     * @param field121String String containing value of 103 field in User Header Block
     * @return A {@link Field121} with values assigned from the passed string.
     */
    public static Field121 parseField121(String field121String) {
        return new Field121()
                .withValue(field121String);
    }

    /**
     * Method to parse and get Field165 object.
     *
     * @param field165String String containing value of 103 field in User Header Block
     * @return A {@link Field165} with values assigned from the passed string.
     */
    public static Field165 parseField165(String field165String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedStringValues = splitBySlash(field165String, Field165.TAG);

        // assign separated values
        return new Field165()
                .withCode(separatedStringValues.length > 0 ? separatedStringValues[0] : null)
                .withInformation(separatedStringValues.length > 1 ? separatedStringValues[1] : null);
    }

    /**
     * Method to parse and get Field423 object.
     *
     * @param field423String String containing value of 423 field in User Header Block
     * @return A {@link Field423} with values assigned from the passed string.
     */
    public static Field423 parseField423(String field423String) {

        // extract and assign values from field423String
        return new Field423()
                .withDate(field423String.substring(
                        MTParserConstants.FIELD_423_DATE_START, MTParserConstants.FIELD_423_DATE_END))
                .withTime(field423String.substring(MTParserConstants.FIELD_423_TIME_START));
    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field424 parseField424(String field434String) {
        return new Field424()
                .withValue(field434String);
    }

    /**
     * Method to parse and get Field433 object.
     *
     * @param field433String String containing value of 433 field in User Header Block
     * @return A {@link Field433} with values assigned from the passed string.
     */
    public static Field433 parseField433(String field433String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedStringValues = splitBySlash(field433String, Field433.TAG);

        // assign separated values
        return new Field433()
                .withCode(separatedStringValues.length > 0 ? separatedStringValues[0] : null)
                .withAdditionalInformation(separatedStringValues.length > 1 ? separatedStringValues[1] : null);
    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field434 parseField434(String field434String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedStringValues = splitBySlash(field434String, Field434.TAG);

        // assign separated values
        return new Field434()
                .withCode(separatedStringValues.length > 0 ? separatedStringValues[0] : null)
                .withAdditionalInformation(separatedStringValues.length > 1 ? separatedStringValues[1] : null);
    }

    /**
     * Method to parse and get FieldCHK object.
     *
     * @param fieldCHKString String containing value of CHK field in Trailer Block
     * @return A {@link FieldCHK} with values assigned from the passed string.
     */
    public static FieldCHK parseFieldCHK(String fieldCHKString) {
        return new FieldCHK()
                .withValue(fieldCHKString);
    }

    /**
     * Method to parse and get FieldMRF object.
     *
     * @param fieldMRFString String containing value of MRF field in Trailer Block
     * @return A {@link FieldMRF} with values assigned from the passed string.
     */
    public static FieldMRF parseFieldMRF(String fieldMRFString) {

        // extract and assign values from fieldMRFString
        return new FieldMRF()
                .withSentDate(fieldMRFString.substring(
                        MTParserConstants.FIELD_MRF_SENT_DATE_START, MTParserConstants.FIELD_MRF_SENT_DATE_END))
                .withTime(fieldMRFString.substring(
                        MTParserConstants.FIELD_MRF_TIME_START, MTParserConstants.FIELD_MRF_TIME_END))
                .withDate(fieldMRFString.substring(
                        MTParserConstants.FIELD_MRF_DATE_START, MTParserConstants.FIELD_MRF_DATE_END))
                .withLtIdentifier(fieldMRFString.substring(
                        MTParserConstants.FIELD_MRF_LT_ADDRESS_START, MTParserConstants.FIELD_MRF_LT_ADDRESS_END))
                .withSessionNumber(fieldMRFString.substring(
                        MTParserConstants.FIELD_MRF_SESSION_NO_START, MTParserConstants.FIELD_MRF_SESSION_NO_END))
                .withSequenceNumber(fieldMRFString.substring(MTParserConstants.FIELD_MRF_SEQUENCE_NO_START));
    }

    /**
     * Method to parse and get FieldPDE object.
     *
     * @param fieldPDEString String containing value of PDE field in Trailer Block
     * @return A {@link FieldPDE} with values assigned from the passed string.
     */
    public static FieldPDE parseFieldPDE(String fieldPDEString) {

        // extract and assign values from fieldPDEString
        return new FieldPDE()
                .withTime(fieldPDEString.substring(
                        MTParserConstants.FIELD_PDE_TIME_START, MTParserConstants.FIELD_PDE_TIME_END))
                .withDate(fieldPDEString.substring(
                        MTParserConstants.FIELD_PDE_DATE_START, MTParserConstants.FIELD_PDE_DATE_END))
                .withLtIdentifier(fieldPDEString.substring(
                        MTParserConstants.FIELD_PDE_LT_ADDRESS_START, MTParserConstants.FIELD_PDE_LT_ADDRESS_END))
                .withSessionNumber(fieldPDEString.substring(
                        MTParserConstants.FIELD_PDE_SESSION_NO_START, MTParserConstants.FIELD_PDE_SESSION_NO_END))
                .withSequenceNumber(fieldPDEString.substring(MTParserConstants.FIELD_PDE_SEQUENCE_NO_START));
    }

    /**
     * Method to parse and get FieldPDM object.
     *
     * @param fieldPDMString String containing value of PDM field in Trailer Block
     * @return A {@link FieldPDM} with values assigned from the passed string.
     */
    public static FieldPDM parseFieldPDM(String fieldPDMString) {

        // extract and assign values from fieldPDMString
        return new FieldPDM()
                .withTime(fieldPDMString.substring(
                        MTParserConstants.FIELD_PDM_TIME_START, MTParserConstants.FIELD_PDM_TIME_END))
                .withDate(fieldPDMString.substring(
                        MTParserConstants.FIELD_PDM_DATE_START, MTParserConstants.FIELD_PDM_DATE_END))
                .withLtIdentifier(fieldPDMString.substring(
                        MTParserConstants.FIELD_PDM_LT_ADDRESS_START, MTParserConstants.FIELD_PDM_LT_ADDRESS_END))
                .withSessionNumber(fieldPDMString.substring(
                        MTParserConstants.FIELD_PDM_SESSION_NO_START, MTParserConstants.FIELD_PDM_SESSION_NO_END))
                .withSequenceNumber(fieldPDMString.substring(MTParserConstants.FIELD_PDM_SEQUENCE_NO_START));
    }

    /**
     * Method to parse and get FieldSYS object.
     *
     * @param fieldSYSString String containing value of SYS field in Trailer Block
     * @return A {@link FieldSYS} with values assigned from the passed string.
     */
    public static FieldSYS parseFieldSYS(String fieldSYSString) {

        // extract and assign values from fieldSYSString
        return new FieldSYS()
                .withTime(fieldSYSString.substring(
                        MTParserConstants.FIELD_SYS_TIME_START, MTParserConstants.FIELD_SYS_TIME_END))
                .withDate(fieldSYSString.substring(
                        MTParserConstants.FIELD_SYS_DATE_START, MTParserConstants.FIELD_SYS_DATE_END))
                .withLtIdentifier(fieldSYSString.substring(
                        MTParserConstants.FIELD_SYS_LT_ADDRESS_START, MTParserConstants.FIELD_SYS_LT_ADDRESS_END))
                .withSessionNumber(fieldSYSString.substring(
                        MTParserConstants.FIELD_SYS_SESSION_NO_START, MTParserConstants.FIELD_SYS_SESSION_NO_END))
                .withSequenceNumber(fieldSYSString.substring(MTParserConstants.FIELD_SYS_SEQUENCE_NO_START));
    }

    /**
     * Method to get elements as an array of strings after splitting by "/".
     * Used for fields in the format -> /(element1)/(element2)/...
     *
     * @param fieldString String with the field value
     * @param fieldName   Name of the field
     * @return An array of string excluding first empty string
     */
    public static String[] splitBySlash(
            String fieldString, String fieldName) {

        // split the string by "/"
        // /(element1)/(element2)/... -> ["", "element1", "element2", ...]
        String[] separatedStringValues = fieldString.split(ConnectorConstants.SLASH);

        // return separated values excluding empty string
        return Arrays.copyOfRange(separatedStringValues, 1, separatedStringValues.length);
    }

}
