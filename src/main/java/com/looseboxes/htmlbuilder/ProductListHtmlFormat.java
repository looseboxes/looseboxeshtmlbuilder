/*
 * Copyright 2017 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.looseboxes.htmlbuilder;

import com.bc.htmlbuilder.DataBuilder;
import com.bc.htmlbuilder.HtmlFormatConfig;
import com.bc.htmlbuilder.ListHtmlFormat;
import com.looseboxes.pu.entities.Product;
import java.text.NumberFormat;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 16, 2018 5:56:34 PM
 */
public class ProductListHtmlFormat extends ListHtmlFormat<Product> {

    public ProductListHtmlFormat(HtmlFormatConfig config) {
        this(
                new ProductImageUrlBuilder(
                        config.getBaseUrl(), config.getContextPath(), config.getDefaultImageUrl()
                ),
                new ProductLinkBuilder(
                        config.getBaseUrl(), config.getContextPath(), config.getMaxTextLength()
                ),
                new ProductSummaryBuilder(
                        NumberFormat.getCurrencyInstance(), config.getMaxTextLength()
                )
        );
    }
    
    public ProductListHtmlFormat(
            DataBuilder<Product> imageUrlBuilder, 
            DataBuilder<Product> linkBuilder, 
            DataBuilder<Product> summaryBuilder) {
        super(imageUrlBuilder, linkBuilder, summaryBuilder);
    }
}
