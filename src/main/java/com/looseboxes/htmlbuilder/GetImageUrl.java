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

import com.looseboxes.pu.entities.Product;
import com.looseboxes.pu.entities.Productvariant;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chinomso Bassey Ikwuagwu on Dec 3, 2017 12:05:53 AM
 */
public class GetImageUrl implements Function<Product, String> {

    @Override
    public String apply(Product item) {
        
        final List<Productvariant> variants = item.getProductvariantList();

        String output = null;
        
        for(Productvariant variant : variants) {
        
            output = variant.getImage1();
            
            if(output != null) {
                break;
            }
        }
        
        return output;
    }
}
