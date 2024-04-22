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

package org.wso2.carbon.module.swiftiso20022.mt.models.messages;

import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.ApplicationHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.BasicHeaderBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.TrailerBlock;
import org.wso2.carbon.module.swiftiso20022.mt.models.blocks.UserHeaderBlock;

/**
 * Base model class for MT messages which contains common blocks.
 */
public class MTMessage {
    private BasicHeaderBlock basicHeaderBlock;
    private ApplicationHeaderBlock applicationHeaderBlock;
    private UserHeaderBlock userHeaderBlock;
    private TrailerBlock trailerBlock;

    public BasicHeaderBlock getBasicHeaderBlock() {
        return basicHeaderBlock;
    }

    public void setBasicHeaderBlock(BasicHeaderBlock basicHeaderBlock) {
        this.basicHeaderBlock = basicHeaderBlock;
    }

    public ApplicationHeaderBlock getApplicationHeaderBlock() {
        return applicationHeaderBlock;
    }

    public void setApplicationHeaderBlock(ApplicationHeaderBlock applicationHeaderBlock) {
        this.applicationHeaderBlock = applicationHeaderBlock;
    }

    public UserHeaderBlock getUserHeaderBlock() {
        return userHeaderBlock;
    }

    public void setUserHeaderBlock(UserHeaderBlock userHeaderBlock) {
        this.userHeaderBlock = userHeaderBlock;
    }

    public TrailerBlock getTrailerBlock() {
        return trailerBlock;
    }

    public void setTrailerBlock(TrailerBlock trailerBlock) {
        this.trailerBlock = trailerBlock;
    }
}
