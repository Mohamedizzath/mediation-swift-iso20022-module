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

package org.wso2.carbon.module.swiftiso20022.mt.models.blocks;

import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldCHK;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldMRF;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDE;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldPDM;
import org.wso2.carbon.module.swiftiso20022.mt.models.fields.FieldSYS;

/**
 * Model for Trailer Block (Block 05).
 */
public class TrailerBlock {

    private FieldCHK checksum;
    private boolean delayedMessage = false;
    private FieldMRF messageReference;
    private FieldPDE possibleDuplicateEmission;
    private FieldPDM possibleDuplicateMessage;
    private FieldSYS systemOriginatedMessage;
    private boolean testAndTrainingMessage = false;

    public FieldCHK getChecksum() {
        return checksum;
    }

    public void setChecksum(FieldCHK checksum) {
        this.checksum = checksum;
    }

    public boolean isDelayedMessage() {
        return delayedMessage;
    }

    public void setDelayedMessage(boolean delayedMessage) {
        this.delayedMessage = delayedMessage;
    }

    public FieldMRF getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(FieldMRF messageReference) {
        this.messageReference = messageReference;
    }

    public FieldPDE getPossibleDuplicateEmission() {
        return possibleDuplicateEmission;
    }

    public void setPossibleDuplicateEmission(FieldPDE possibleDuplicateEmission) {
        this.possibleDuplicateEmission = possibleDuplicateEmission;
    }

    public FieldPDM getPossibleDuplicateMessage() {
        return possibleDuplicateMessage;
    }

    public void setPossibleDuplicateMessage(FieldPDM possibleDuplicateMessage) {
        this.possibleDuplicateMessage = possibleDuplicateMessage;
    }

    public FieldSYS getSystemOriginatedMessage() {
        return systemOriginatedMessage;
    }

    public void setSystemOriginatedMessage(FieldSYS systemOriginatedMessage) {
        this.systemOriginatedMessage = systemOriginatedMessage;
    }

    public boolean isTestAndTrainingMessage() {
        return testAndTrainingMessage;
    }

    public void setTestAndTrainingMessage(boolean testAndTrainingMessage) {
        this.testAndTrainingMessage = testAndTrainingMessage;
    }
}
