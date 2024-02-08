package org.wso2.carbon.module.swiftiso20022.mt103models.transformer;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;
import org.wso2.carbon.module.swiftiso20022.model.ErrorModel;
import org.wso2.carbon.module.swiftiso20022.utils.JsonToMt103Utils;

import java.util.List;
import java.util.Objects;

/**
 * Class to represent entity in the request payload.
 */
public class Entity {
    String option;
    List<String> details;

    public ErrorModel validate(String fieldName) {
        if (StringUtils.isBlank(option)) {
            return new ErrorModel(ConnectorConstants.ERROR_NO_CODE,
                    String.format(MT103Constants.ERROR_EMPTY_ENTITY_OPTION, fieldName));
        }
        if (option.length() > 1) {
            return new ErrorModel(ConnectorConstants.ERROR_NO_CODE,
                    String.format(MT103Constants.ERROR_INVALID_ENTITY_OPTION, fieldName));
        }
        if (Objects.isNull(details)) {
            return new ErrorModel(ConnectorConstants.ERROR_T17,
                    String.format(MT103Constants.ERROR_EMPTY_ENTITY_DETAILS, fieldName));
        }
        ErrorModel detailsValidationResponse =
                JsonToMt103Utils.validateFieldLines(details, fieldName, 35, 5);
        if (detailsValidationResponse.isError()) {
            return detailsValidationResponse;
        }
        return new ErrorModel();
    }

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
