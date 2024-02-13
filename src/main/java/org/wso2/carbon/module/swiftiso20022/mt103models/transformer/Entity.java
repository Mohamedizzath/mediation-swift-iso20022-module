package org.wso2.carbon.module.swiftiso20022.mt103models.transformer;

import java.util.List;

/**
 * Class to represent entity in the request payload.
 */
public class Entity {
    String option;
    List<String> details;

    public void setOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public List<String> getDetails() {
        return details;
    }
}
