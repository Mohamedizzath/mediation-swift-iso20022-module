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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import org.wso2.carbon.module.swiftiso20022.constants.MTParserConstants;
import org.wso2.carbon.module.swiftiso20022.exceptions.MTMessageParsingException;

import java.util.regex.Matcher;

/**
 * Model for Swift MT Tag 25P.
 *<p>
 *     format: (Account)
 *             (Identifier code)
 *     example: 25P:DD01100056869
 *              GSCRUS30XXX
 *@see <a href="https://www2.swift.com/knowledgecentre/publications/us9m_20230720/
 * 2.0?topic=con_sfld_MaOrZQQQEe2AI4OK6vBjrg_1154221120fld.htm">Tag 25P</a>
 * </p>
 */
public class Field25P {
    public static final String TAG = "25P";

    // Example - DD01100056869
    private String account;

    // Example - GSCRUS30XXX
    private String identifierCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    /**
     * Method for set account and return the instance.
     * @param account     Account value of Field25P
     * @return            Created instance of Field25P
     */
    public Field25P withAccount(String account) {
        setAccount(account);
        return this;
    }

    /**
     * Method for set identifier code and return the instance.
     * @param identifierCode     Identifier code value of Field25P
     * @return                  Created instance of Field25P
     */
    public Field25P withIdentifierCode(String identifierCode) {
        setIdentifierCode(identifierCode);
        return this;
    }

    /**
     * Method for parse and get Field25P object.
     * @param field25PString       String which contains value of Field25P
     * @return                     Created instance of Field25P
     * @throws MTMessageParsingException
     */
    public static Field25P parse(String field25PString) throws MTMessageParsingException {
        Matcher field25PMatcher = MTParserConstants.FIELD_25P_REGEX_PATTERN.matcher(field25PString);

        if (field25PMatcher.matches()) {
            return new Field25P().withAccount(field25PMatcher.group(1))
                        .withIdentifierCode(field25PMatcher.group(3));
        } else {
            throw new MTMessageParsingException(String.format(MTParserConstants.INVALID_FIELD_FORMAT,
                    Field25P.TAG));
        }
    }
}
