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
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field103;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field106;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field108;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field111;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field113;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field115;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field119;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field121;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field165;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field423;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field424;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field433;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.Field434;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mtmodels.fields.FieldSYS;

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
                .value(field103String);
    }

    /**
     * Method to parse and get Field103 object.
     *
     * @param field106String String containing value of 106 field in User Header Block
     * @return A {@link Field106} with values assigned from the passed string.
     */
    public static Field106 parseField106(String field106String) {

        // initiate stringBuilder object to consume field106String
        StringBuilder field106StringBuilder = new StringBuilder(field106String);

        // extract and assign values from field106String
        return new Field106()
                .date(extractSubstringByIndex(field106StringBuilder, ConnectorConstants.DATE_LENGTH))
                .logicalTerminalAddress(extractSubstringByIndex(
                        field106StringBuilder, ConnectorConstants.LOGICAL_TERMINAL_ADDRESS_LENGTH))
                .sessionNumber(extractSubstringByIndex(
                        field106StringBuilder, ConnectorConstants.SESSION_NUMBER_LENGTH))
                .sequenceNumber(extractSubstringByIndex(
                        field106StringBuilder, ConnectorConstants.SEQUENCE_NUMBER_LENGTH));
    }

    /**
     * Method to parse and get Field108 object.
     *
     * @param field108String String containing value of 108 field in User Header Block
     * @return A {@link Field108} with values assigned from the passed string.
     */
    public static Field108 parseField108(String field108String) {
        return new Field108()
                .value(field108String);
    }

    /**
     * Method to parse and get Field111 object.
     *
     * @param field111String String containing value of 111 field in User Header Block
     * @return A {@link Field111} with values assigned from the passed string.
     */
    public static Field111 parseField111(String field111String) {
        return new Field111()
                .value(field111String);
    }

    /**
     * Method to parse and get Field113 object.
     *
     * @param field113String String containing value of 113 field in User Header Block
     * @return A {@link Field113} with values assigned from the passed string.
     */
    public static Field113 parseField113(String field113String) {
        return new Field113()
                .value(field113String);
    }

    /**
     * Method to parse and get Field115 object.
     *
     * @param field115String String containing value of 115 field in User Header Block
     * @return A {@link Field115} with values assigned from the passed string.
     */
    public static Field115 parseField115(String field115String) {

        // initiate stringBuilder object to consume field106String
        StringBuilder field115StringBuilder = new StringBuilder(field115String);

        // extract and assign values from field115String
        return new Field115()
                .creditingTime(extractSubstringByIndex(
                        field115StringBuilder, ConnectorConstants.HHMMSS_TIME_LENGTH))
                .debitingTime(extractSubstringByIndex(
                        field115StringBuilder, ConnectorConstants.HHMMSS_TIME_LENGTH))
                .countryCode(extractSubstringByIndex(
                        field115StringBuilder, ConnectorConstants.COUNTRY_CODE_LENGTH))
                .reference(extractSubstringByIndex(field115StringBuilder, ConnectorConstants.REFERENCE_LENGTH));
    }

    /**
     * Method to parse and get Field119 object.
     *
     * @param field119String String containing value of 103 field in User Header Block
     * @return A {@link Field119} with values assigned from the passed string.
     */
    public static Field119 parseField119(String field119String) {
        return new Field119()
                .value(field119String);
    }

    /**
     * Method to parse and get Field121 object.
     *
     * @param field121String String containing value of 103 field in User Header Block
     * @return A {@link Field121} with values assigned from the passed string.
     */
    public static Field121 parseField121(String field121String) {
        return new Field121()
                .value(field121String);
    }

    /**
     * Method to parse and get Field165 object.
     *
     * @param field165String String containing value of 103 field in User Header Block
     * @return A {@link Field165} with values assigned from the passed string.
     */
    public static Field165 parseField165(String field165String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedValues = splitBySlash(field165String, Field165.TAG);

        // assign separated values
        return new Field165()
                .code(separatedValues.length > 0 ? separatedValues[0] : null)
                .information(separatedValues.length > 1 ? separatedValues[1] : null);
    }

    /**
     * Method to parse and get Field423 object.
     *
     * @param field423String String containing value of 423 field in User Header Block
     * @return A {@link Field423} with values assigned from the passed string.
     */
    public static Field423 parseField423(String field423String) {

        // initiate stringBuilder object to consume field423String
        StringBuilder field423StringBuilder = new StringBuilder(field423String);

        // extract and assign values from field423String
        return new Field423()
                .date(extractSubstringByIndex(
                        field423StringBuilder, ConnectorConstants.DATE_LENGTH))
                .time(extractSubstringByIndex(
                        field423StringBuilder, ConnectorConstants.HHMMSSSS_TIME_LENGTH));
    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field424 parseField424(String field434String) {
        return new Field424()
                .value(field434String);
    }

    /**
     * Method to parse and get Field433 object.
     *
     * @param field433String String containing value of 433 field in User Header Block
     * @return A {@link Field433} with values assigned from the passed string.
     */
    public static Field433 parseField433(String field433String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedValue = splitBySlash(field433String, Field433.TAG);

        // assign separated values
        return new Field433()
                .code(separatedValue.length > 1 ? separatedValue[0] : null)
                .additionalInformation(separatedValue.length == 2 ? separatedValue[1] : null);
    }

    /**
     * Method to parse and get Field434 object.
     *
     * @param field434String String containing value of 434 field in User Header Block
     * @return A {@link Field434} with values assigned from the passed string.
     */
    public static Field434 parseField434(String field434String) throws MTMessageParsingException {

        // separate values by "/"
        String[] separatedValue = splitBySlash(field434String, Field434.TAG);

        // assign separated values
        return new Field434()
                .code(separatedValue.length > 1 ? separatedValue[0] : null)
                .additionalInformation(separatedValue.length == 2 ? separatedValue[1] : null);
    }

    /**
     * Method to parse and get FieldCHK object.
     *
     * @param fieldCHKString String containing value of CHK field in Trailer Block
     * @return A {@link FieldCHK} with values assigned from the passed string.
     */
    public static FieldCHK parseFieldCHK(String fieldCHKString) {
        return new FieldCHK()
                .value(fieldCHKString);
    }

    /**
     * Method to parse and get FieldMRF object.
     *
     * @param fieldMRFString String containing value of MRF field in Trailer Block
     * @return A {@link FieldMRF} with values assigned from the passed string.
     */
    public static FieldMRF parseFieldMRF(String fieldMRFString) {

        // initiate stringBuilder object to consume fieldMRFString
        StringBuilder fieldMRFStringBuilder = new StringBuilder(fieldMRFString);

        // extract and assign values from fieldMRFString
        return new FieldMRF()
                .sentDate(extractSubstringByIndex(fieldMRFStringBuilder, ConnectorConstants.DATE_LENGTH))
                .time(extractSubstringByIndex(fieldMRFStringBuilder, ConnectorConstants.TIME_LENGTH))
                .date(extractSubstringByIndex(fieldMRFStringBuilder, ConnectorConstants.DATE_LENGTH))
                .ltIdentifier(extractSubstringByIndex(
                        fieldMRFStringBuilder, ConnectorConstants.LOGICAL_TERMINAL_ADDRESS_LENGTH))
                .sessionNumber(extractSubstringByIndex(
                        fieldMRFStringBuilder, ConnectorConstants.SESSION_NUMBER_LENGTH))
                .sequenceNumber(extractSubstringByIndex(
                        fieldMRFStringBuilder, ConnectorConstants.SEQUENCE_NUMBER_LENGTH));
    }

    /**
     * Method to parse and get FieldPDE object.
     *
     * @param fieldPDEString String containing value of PDE field in Trailer Block
     * @return A {@link FieldPDE} with values assigned from the passed string.
     */
    public static FieldPDE parseFieldPDE(String fieldPDEString) {

        // initiate stringBuilder object to consume fieldPDEString
        StringBuilder fieldPDEStringBuilder = new StringBuilder(fieldPDEString);

        // extract and assign values from fieldPDEString
        return new FieldPDE()
                .time(extractSubstringByIndex(fieldPDEStringBuilder, ConnectorConstants.TIME_LENGTH))
                .date(extractSubstringByIndex(fieldPDEStringBuilder, ConnectorConstants.DATE_LENGTH))
                .ltIdentifier(extractSubstringByIndex(
                        fieldPDEStringBuilder, ConnectorConstants.LOGICAL_TERMINAL_ADDRESS_LENGTH))
                .sessionNumber(extractSubstringByIndex(
                        fieldPDEStringBuilder, ConnectorConstants.SESSION_NUMBER_LENGTH))
                .sequenceNumber(extractSubstringByIndex(
                        fieldPDEStringBuilder, ConnectorConstants.SEQUENCE_NUMBER_LENGTH));
    }

    /**
     * Method to parse and get FieldPDM object.
     *
     * @param fieldPDMString String containing value of PDM field in Trailer Block
     * @return A {@link FieldPDM} with values assigned from the passed string.
     */
    public static FieldPDM parseFieldPDM(String fieldPDMString) {

        // initiate stringBuilder object to consume fieldPDMString
        StringBuilder fieldPDMStringBuilder = new StringBuilder(fieldPDMString);

        // extract and assign values from fieldPDMString
        return new FieldPDM()
                .time(extractSubstringByIndex(fieldPDMStringBuilder, ConnectorConstants.TIME_LENGTH))
                .date(extractSubstringByIndex(fieldPDMStringBuilder, ConnectorConstants.DATE_LENGTH))
                .ltIdentifier(extractSubstringByIndex(
                        fieldPDMStringBuilder, ConnectorConstants.LOGICAL_TERMINAL_ADDRESS_LENGTH))
                .sessionNumber(extractSubstringByIndex(
                        fieldPDMStringBuilder, ConnectorConstants.SESSION_NUMBER_LENGTH))
                .sequenceNumber(extractSubstringByIndex(
                        fieldPDMStringBuilder, ConnectorConstants.SEQUENCE_NUMBER_LENGTH));
    }

    /**
     * Method to parse and get FieldSYS object.
     *
     * @param fieldSYSString String containing value of SYS field in Trailer Block
     * @return A {@link FieldSYS} with values assigned from the passed string.
     */
    public static FieldSYS parseFieldSYS(String fieldSYSString) {

        // initiate stringBuilder object to consume fieldSYSString
        StringBuilder fieldSYSStringBuilder = new StringBuilder(fieldSYSString);

        // extract and assign values from fieldSYSString
        return new FieldSYS()
                .time(extractSubstringByIndex(fieldSYSStringBuilder, ConnectorConstants.TIME_LENGTH))
                .date(extractSubstringByIndex(fieldSYSStringBuilder, ConnectorConstants.DATE_LENGTH))
                .ltIdentifier(extractSubstringByIndex(
                        fieldSYSStringBuilder, ConnectorConstants.LOGICAL_TERMINAL_ADDRESS_LENGTH))
                .sessionNumber(extractSubstringByIndex(
                        fieldSYSStringBuilder, ConnectorConstants.SESSION_NUMBER_LENGTH))
                .sequenceNumber(extractSubstringByIndex(
                        fieldSYSStringBuilder, ConnectorConstants.SEQUENCE_NUMBER_LENGTH));
    }

    /**
     * Method to extract a value from StringBuilder object and delete the substring from the object.
     *
     * @param stringBuilder StringBuilder object with the string
     * @param startIndex    Starting index of the substring
     * @param endIndex      Ending index of the substring
     * @return substring or null if the StringBuilder object is empty.
     */
    public static String extractSubstringByIndex(StringBuilder stringBuilder, int startIndex, int endIndex) {

        // if the string is empty
        if (stringBuilder.length() == 0) {
            return null;
        }

        // if remaining length is less than defined length
        if (stringBuilder.length() <= endIndex - startIndex) {

            // assign whole remaining string to a variable
            String substring = stringBuilder.toString();

            // empty StringBuilder object
            stringBuilder.setLength(0);

            // return assigned string
            return substring;
        }

        // else extract defined substring
        String substring = stringBuilder.substring(startIndex, endIndex);

        // remove substring from the StringBuilder object
        stringBuilder.delete(startIndex, endIndex);

        // return extracted substring
        return substring;
    }

    /**
     * Overloaded method to extract substring from the zero index.
     *
     * @param stringBuilder StringBuilder object with the string
     * @param index         Ending index of the substring
     * @return substring or null if the StringBuilder object is empty.
     */
    public static String extractSubstringByIndex(StringBuilder stringBuilder, int index) {
        return extractSubstringByIndex(stringBuilder, 0, index);
    }

    /**
     * Method to get elements as an array of strings after splitting by "/".
     * Used for fields in the format -> /(element1)/(element2)
     *
     * @param fieldString String with the field value
     * @param fieldName   Name of the field
     * @return An array of string with the 2 elements
     * @throws MTMessageParsingException if the passed string is in incorrect format
     */
    public static String[] splitBySlash(
            String fieldString, String fieldName) throws MTMessageParsingException {

        // split the string by "/"
        // /(element1)/(element2)
        String[] separatedElements = fieldString.split(ConnectorConstants.SLASH);

        // First element always should be a empty string and max there can be 2 more elements
        if (separatedElements.length > 3 || !separatedElements[0].isEmpty()) {
            throw new MTMessageParsingException(ConnectorConstants.ERROR_U00,
                    String.format(ConnectorConstants.ERROR_FIELD_INVALID_IN_BLOCK,
                            fieldName, ConnectorConstants.USER_HEADER_BLOCK));
        }

        // return separated values excluding empty string
        return Arrays.copyOfRange(separatedElements, 1, separatedElements.length);
    }

}
