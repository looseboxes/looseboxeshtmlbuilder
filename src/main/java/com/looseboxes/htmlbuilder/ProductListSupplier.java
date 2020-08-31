/*
 * Copyright 2018 NUROX Ltd.
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

import com.bc.jpa.context.eclipselink.PersistenceContextEclipselinkOptimized;
import com.bc.jpa.context.PersistenceUnitContext;
import com.bc.jpa.dao.Select;
import com.bc.jpa.functions.FetchItemsByAge;
import com.bc.jpa.functions.GetPersistenceUnitContextForEntityClasses;
import com.bc.jpa.dao.sql.MySQLDateTimePatterns;
import com.looseboxes.pu.entities.Product;
import com.looseboxes.pu.entities.Product_;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Chinomso Bassey Ikwuagwu on May 1, 2018 1:48:29 AM
 */
public class ProductListSupplier implements Serializable, Supplier<List<Product>> {
    
    private final PersistenceUnitContext puContext;
    
    private final long maxAgeHours;
    
    private final int limit;
    
    private final UnaryOperator<Select<Product>> formatter;
    
    public ProductListSupplier(long maxAgeHours, int limit) {
        this(
                new GetPersistenceUnitContextForEntityClasses().apply(
                        new PersistenceContextEclipselinkOptimized(new MySQLDateTimePatterns(), Product.class), 
                        Collections.singletonList(Product.class)
                ), 
                maxAgeHours,
                limit
        );
    }
    
    public ProductListSupplier(PersistenceUnitContext puContext, long maxAgeHours, int limit) {
        this.puContext = Objects.requireNonNull(puContext);
        this.maxAgeHours = maxAgeHours;
        this.limit = limit;
        this.formatter = (sel) -> sel
                .ascOrder(Product_.availabilityid)
                .descOrder(Product_.ratingPercent)
                .descOrder(Product_.views);
    }

    @Override
    public List<Product> get() {
        return new FetchItemsByAge(puContext, Product.class, formatter, 0, limit).apply(
                Product_.datecreated.getName(), TimeUnit.HOURS.toMillis(maxAgeHours)
        );
    }
}
