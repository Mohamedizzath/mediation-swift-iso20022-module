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

package org.wso2.carbon.module.swiftiso20022.payloadfactory;

import com.google.gson.Gson;
import freemarker.template.Template;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.module.swiftiso20022.utils.FreemarkerTemplateTestUtils;
import org.wso2.carbon.module.swiftiso20022.utils.MT940ToISO20022PayloadFactoryTestConstants;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Test class for validating payload factory freemarker template for
 * convert MT940 message to ISO20022 message.
 */
public class MT940ToISO20022PayloadFactoryTests {

    private static final Gson gson = new Gson();

    @Test(dataProvider = "parseAppHdrToElement", dataProviderClass = MT940ToISO20022PayloadFactoryTestConstants.class)
    public void testValidMT940MessageScenario(String mt940Message, String targetValue) throws Exception {
        // Get the ftl file as input stream
        InputStream freemarkerTempStream = getClass().getClassLoader()
                .getResourceAsStream("freemarkerTemplates/MT940ToISO.ftl");

        // Create the map from the MT940 JSON
        Map<String, Object> mt940Map = Map.of("payload", gson.fromJson(mt940Message, Map.class));

        Template template = FreemarkerTemplateTestUtils
                .getFreemarkerTemplate(freemarkerTempStream, "ISOToMT940");

        Writer stringwriter = new StringWriter();
        template.process(mt940Map, stringwriter);
        String freemarkerOutput = stringwriter.toString();

        Assert.assertEquals(MT940ToISO20022PayloadFactoryTestConstants.
                getXMLElement(freemarkerOutput, "/BizMsgEnvlp/app:AppHdr/app:To/app:FIId/app:FinInstnId/app:BICFI"), targetValue);

    }
}
