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

package org.wso2.carbon.module.swiftiso20022.mt.models.fields;


/**
 * Model for message reference in Trailer Block (Block 05).
 * <p>
 * format: (Sent Date)(Time)(Date)(LT Identifier)(Session Number)(Sequence Number)
 * example: {MRF:1806271539180626BANKFRPPAXXX2222123456}
 *
 * @see <a href="https://www.paiementor.com/swift-mt-message-block-5-trailers-description/">
 * Trailer Block Fields</a>
 */
public class FieldMRF {

    public static final String TAG = "MRF";

    // format: YYMMDD
    // example: 180627
    private String sentDate;

    // format: HHMM
    // example: 1539
    private String time;

    // format: YYMMDD
    // example: 180626
    private String date;

    // example: BANKFRPPAXXX
    private String ltIdentifier;

    // example: 2222
    private String sessionNumber;

    // example: 123456
    private String sequenceNumber;

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLtIdentifier() {
        return ltIdentifier;
    }

    public void setLtIdentifier(String ltIdentifier) {
        this.ltIdentifier = ltIdentifier;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Method to set sent date of the field and return the instance.
     *
     * @param sentDate Sent date value to be set.
     * @return object itself
     */
    public FieldMRF withSentDate(String sentDate) {
        setSentDate(sentDate);
        return this;
    }

    /**
     * Method to set time of the field and return the instance.
     *
     * @param time Time value to be set.
     * @return object itself
     */
    public FieldMRF withTime(String time) {
        setTime(time);
        return this;
    }

    /**
     * Method to set date of the field and return the instance.
     *
     * @param date Date value to be set.
     * @return object itself
     */
    public FieldMRF withDate(String date) {
        setDate(date);
        return this;
    }

    /**
     * Method to set logical terminal identifier of the field and return the instance.
     *
     * @param ltIdentifier Logical terminal identifier value to be set.
     * @return object itself
     */
    public FieldMRF withLtIdentifier(String ltIdentifier) {
        setLtIdentifier(ltIdentifier);
        return this;
    }

    /**
     * Method to set session number of the field and return the instance.
     *
     * @param sessionNumber Session number value to be set.
     * @return object itself
     */
    public FieldMRF withSessionNumber(String sessionNumber) {
        setSessionNumber(sessionNumber);
        return this;
    }

    /**
     * Method to set sequence number of the field and return the instance.
     *
     * @param sequenceNumber Sequence number value to be set.
     * @return object itself
     */
    public FieldMRF withSequenceNumber(String sequenceNumber) {
        setSequenceNumber(sequenceNumber);
        return this;
    }
}
