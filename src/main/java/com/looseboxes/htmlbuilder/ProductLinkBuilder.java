/*
 * Copyright 2016 NUROX Ltd.
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
import com.looseboxes.pu.entities.Product;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Dec 17, 2016 4:00:07 PM
 */
public class ProductLinkBuilder implements DataBuilder<Product> {

    private final int maxLength;
    
    private final String contextUrl;

    public ProductLinkBuilder(String baseUrl, String contextPath) {
        this(baseUrl, contextPath, 150);
    }
    
    public ProductLinkBuilder(String baseUrl, String contextPath, int maxLength) {
        this.maxLength = maxLength;
        this.contextUrl = Objects.requireNonNull(baseUrl) + Objects.requireNonNull(contextPath);
    }
    
    @Override
    public String build(Product product) {
        
        final String title = this.truncate(product.getProductName(), this.maxLength, false);
        
        final String url = contextUrl + "/products/"+product.getProductid()+"_"+title.replaceAll("[^a-zA-Z0-9]", "_")+".jsp";        
        
        return url;
    }

    protected String truncate(String str, int maxLen, boolean ellipsize) {
        String output;
        if(str == null || str.isEmpty() || str.length() <= maxLen) {
            output = str;
        }else {
            final String prefix = ellipsize ? "..." : "";
            output = str.substring(0, maxLen-prefix.length()) + prefix;
        }
        return output;
    }
}
