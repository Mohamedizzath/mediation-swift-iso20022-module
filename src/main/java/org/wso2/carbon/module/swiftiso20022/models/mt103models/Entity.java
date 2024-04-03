package org.wso2.carbon.module.swiftiso20022.models.mt103models;

import java.util.List;

/**
 * Class to represent entity in the request payload.
 */
public class Entity {

    private String option;
    private List<String> details;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
