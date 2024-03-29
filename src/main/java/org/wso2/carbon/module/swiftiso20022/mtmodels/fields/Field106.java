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

package org.wso2.carbon.module.swiftiso20022.mtmodels.fields;

/**
 * Model for message input reference in User Header Block (Block 03).
 *
 * format: (Date)(LT Address)(Session Number)(Sequence Number)
 */
public class Field106 extends Field {

    public static final String TAG = "106";

    // format: YYMMDD
    private String date;

    // format: 12x
    private String logicalTerminalAddress;

    // format: 4!n
    private String sessionNumber;

    // format: 6!n
    private String sequenceNumber;

    public String getDate() {
        return date;
    }

    public Field106 setDate(String date) {
        this.date = date;
        return this;
    }

    public String getLogicalTerminalAddress() {
        return logicalTerminalAddress;
    }

    public Field106 setLogicalTerminalAddress(String logicalTerminalAddress) {
        this.logicalTerminalAddress = logicalTerminalAddress;
        return this;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public Field106 setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
        return this;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public Field106 setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }
}
