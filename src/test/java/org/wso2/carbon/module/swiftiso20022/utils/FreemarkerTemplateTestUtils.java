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

package org.wso2.carbon.module.swiftiso20022.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 *  Contains util functions for validating freemarker templates.
 */
public class FreemarkerTemplateTestUtils {
    /**
     * Return HashMap which build against given XML data for freemarker validation.
     * @param content XML message content which need to parse
     * @return        HashMap which constructed according to the XML content
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Map<String, Object> getXMLMapFromXMLContent(String content)
            throws IOException, ParserConfigurationException, SAXException {

        Map<String, Object> root = new HashMap<>();
        InputSource inputSource = new InputSource(new StringReader(content));
        root.put("payload", NodeModel.parse(inputSource));

        return root;
    }

    /**
     * Construct freemarker template from ftl file located in the resources' folder.
     * @param fTemplateStream  Ftl template contain as inputStream
     * @param templateName     Name of the template and this can be any name. Eg:- ISOtoMT940
     * @return                 Template which loaded according to ftl file given
     * @throws IOException
     */
    public static Template getFreemarkerTemplate(InputStream fTemplateStream, String templateName) throws IOException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_30);
        config.setDefaultEncoding("UTF-8");
        String templateStr = IOUtils.toString(fTemplateStream, String.valueOf(StandardCharsets.UTF_8));

        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(templateName, templateStr);
        config.setTemplateLoader(templateLoader);

        return config.getTemplate(templateName);
    }
}
