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

package org.wso2.carbon.module.swiftiso20022.mt103models.transformer;

import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block01;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block02;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block03;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block04;
import org.wso2.carbon.module.swiftiso20022.mt103models.transformer.blocks.Block05;

/**
 * Class that models the request payload.
 */
public class RequestPayload {
    Block01 block01;
    Block02 block02;
    Block03 block03;
    Block04 block04;
    Block05 block05;

    public ErrorModel validate() {
        if (block01 == null) {
            // TODO: replace with constants
            return new ErrorModel("H01", "Basic Header block is mandatory for MT103");
        } else {
            ErrorModel block01ValidationResponse = block01.validate();
            if (block01ValidationResponse.isError()) {
                return block01ValidationResponse;
            }
        }
        if (block02 != null) {
            ErrorModel block02ValidationResponse = block02.validate();
            if (block02ValidationResponse.isError()) {
                return block02ValidationResponse;
            }
        }
        if (block03 == null) {
            // TODO: replace with constants
            return new ErrorModel("H01", "User Header block is mandatory for MT103");
        }
        if (block04 == null) {
            // TODO: replace with constants
            return new ErrorModel("H01", "Text block is mandatory for MT103");
        }
        return new ErrorModel();
    }

    public void setBlock01(Block01 block01) {
        this.block01 = block01;
    }

    public Block01 getBlock01() {
        return block01;
    }

    public void setBlock02(Block02 block02) {
        this.block02 = block02;
    }

    public Block02 getBlock02() {
        return block02;
    }

    public void setBlock03(Block03 block03) {
        this.block03 = block03;
    }

    public Block03 getBlock03() {
        return block03;
    }

    public void setBlock04(Block04 block04) {
        this.block04 = block04;
    }

    public Block04 getBlock04() {
        return block04;
    }

    public void setBlock05(Block05 block05) {
        this.block05 = block05;
    }

    public Block05 getBlock05() {
        return block05;
    }
}
