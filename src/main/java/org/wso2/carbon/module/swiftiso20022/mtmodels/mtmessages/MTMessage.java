package org.wso2.carbon.module.swiftiso20022.mtmodels.mtmessages;

import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.TrailerBlock;
import org.wso2.carbon.module.swiftiso20022.mtmodels.blocks.UserHeaderBlock;

/**
 * Model with common blocks of MT messages.
 */
public class MTMessage {

    private UserHeaderBlock userHeaderBlock;
    private TrailerBlock trailerBlock;

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
