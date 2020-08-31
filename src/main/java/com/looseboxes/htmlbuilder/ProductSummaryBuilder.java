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

import com.bc.functions.Truncate;
import com.bc.htmlbuilder.DataBuilder;
import com.looseboxes.pu.entities.Product;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author Chinomso Bassey Ikwuagwu on Dec 17, 2016 4:09:55 PM
 */
public class ProductSummaryBuilder implements DataBuilder<Product> {

    private final NumberFormat numberFormat;
    
    private final int maxLength;
    
    private final BiFunction<String, Integer, String> truncate;

    public ProductSummaryBuilder() {
        this(NumberFormat.getCurrencyInstance(), 150);
    }
    
    public ProductSummaryBuilder(NumberFormat numberFormat, int maxLength) {
        this.numberFormat = Objects.requireNonNull(numberFormat);
        this.maxLength = maxLength;
        this.truncate = new Truncate(true);
    }

    @Override
    public String build(Product product) {
        final StringBuilder builder = new StringBuilder(this.maxLength * 2);
        final String text = truncate.apply(product.getProductName(), maxLength);
        builder.append("<b>").append(text).append("</b>");
        builder.append("<br/>");
        builder.append("<span style=\"text-decoration:line-through;\">").append(this.toCurrencyFormat(product)).append("</span>");
        Object discountPrice = this.toCurrencyFormat(product, this.getDiscountPrice(product));
        builder.append("&emsp;<b style=\"color:red;\">").append(discountPrice).append("</b>");
        builder.append("<br/>");
        builder.append(product.getViews()).append(" views");
        return builder.toString();
    }
    
    private BigDecimal getDiscountPrice(Product product) {
        if(product.getDiscount() == null) {
            return product.getPrice();
        }else{
            return product.getPrice().subtract(product.getDiscount());
        }
    }

    private Object toCurrencyFormat(Product product) {
        return this.toCurrencyFormat(product, product.getPrice());
    }
    
    private Object toCurrencyFormat(Product product, BigDecimal price) {
        final com.looseboxes.pu.entities.Currency curr = product.getCurrencyid();
        this.numberFormat.setCurrency(java.util.Currency.getInstance(curr.getCurrency()));
        this.numberFormat.setMaximumFractionDigits(curr.getFractionDigits());
        return this.numberFormat.format(price);
    }
}
