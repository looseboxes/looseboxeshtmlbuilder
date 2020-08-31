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
import com.bc.jpa.functions.GetPersistenceUnitContextForEntityClasses;
import com.bc.jpa.dao.sql.MySQLDateTimePatterns;
import com.looseboxes.pu.entities.Product;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author Chinomso Bassey Ikwuagwu on May 1, 2018 2:59:48 AM
 */
public class ProductListSupplierDevmode extends ProductListSupplier {
    
    private static final URI URI = Paths.get(System.getProperty("user.home"), 
            "Documents", "NetBeansProjects", "looseboxespu", "src", "test", 
            "resources", "META-INF", "persistence.xml").toUri();
    
    public ProductListSupplierDevmode(long maxAgeHours, int limit) {
        super(
                new GetPersistenceUnitContextForEntityClasses().apply(
                        new PersistenceContextEclipselinkOptimized(URI, new MySQLDateTimePatterns()), 
                        Collections.singletonList(Product.class)
                ), 
                maxAgeHours,
                limit
        );
    }

    public ProductListSupplierDevmode(PersistenceUnitContext puContext, long maxAgeHours, int limit) {
        super(puContext, maxAgeHours, limit);
    }
}

