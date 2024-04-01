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

package org.wso2.carbon.module.swiftiso20022.models.mt103models.blocks;

/**
 * Class that models request payload block05.
 */
public class Block05 {

    private String checksum;
    private String testNTrainingMessage;
    private String possibleDuplicateEmission;
    private String delayedMessage;
    private String messageReference;
    private String possibleDuplicateMessage;
    private String systemOriginatedMessage;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getTestNTrainingMessage() {
        return testNTrainingMessage;
    }

    public void setTestNTrainingMessage(String testNTrainingMessage) {
        this.testNTrainingMessage = testNTrainingMessage;
    }

    public String getPossibleDuplicateEmission() {
        return possibleDuplicateEmission;
    }

    public void setPossibleDuplicateEmission(String possibleDuplicateEmission) {
        this.possibleDuplicateEmission = possibleDuplicateEmission;
    }

    public String getDelayedMessage() {
        return delayedMessage;
    }

    public void setDelayedMessage(String delayedMessage) {
        this.delayedMessage = delayedMessage;
    }

    public String getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(String messageReference) {
        this.messageReference = messageReference;
    }

    public String getPossibleDuplicateMessage() {
        return possibleDuplicateMessage;
    }

    public void setPossibleDuplicateMessage(String possibleDuplicateMessage) {
        this.possibleDuplicateMessage = possibleDuplicateMessage;
    }

    public String getSystemOriginatedMessage() {
        return systemOriginatedMessage;
    }

    public void setSystemOriginatedMessage(String systemOriginatedMessage) {
        this.systemOriginatedMessage = systemOriginatedMessage;
    }
}
