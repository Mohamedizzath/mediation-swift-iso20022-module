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

package org.wso2.carbon.module.swiftiso20022.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.wso2.carbon.module.swiftiso20022.constants.ConnectorConstants;
import org.wso2.carbon.module.swiftiso20022.constants.MT103Constants;

/**
 * Utility methods for transforming a JSON.
 */
public class JsonTransformationUtils {

    private JsonTransformationUtils() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Method to convert amount string to a number and get amount as a JsonPrimitive.
     *
     * @param jsonObject JsonObject with amount present as a String in MT decimal format.
     * @param key        Key of the amount field.
     */
    public static void replaceAmountAsNumber(JsonObject jsonObject, String key) {

        jsonObject.addProperty(key, Double.parseDouble(jsonObject.get(key).getAsString().replace(",", ".")));
    }

    /**
     * Method to remove No_letter option from the text block.
     * Current implementation only covers JsonObject.
     *
     * @param textBlock JsonObject created from the text block model of a MT message implementation.
     */
    public static void removeNoLetterOption(JsonObject textBlock) {

        for (String key : textBlock.keySet()) {

            // Since field can either be a JsonObject or JsonArray
            JsonElement jsonElement = textBlock.get(key);

            if (jsonElement instanceof JsonObject) {
                JsonObject field = jsonElement.getAsJsonObject();
                char option = field.get(MT103Constants.OPTION_KEY).getAsCharacter();

                if (option == ConnectorConstants.NO_LETTER_OPTION) {
                    field.remove(MT103Constants.OPTION_KEY);
                    textBlock.add(key, field);
                }
            }
        }
    }
}
