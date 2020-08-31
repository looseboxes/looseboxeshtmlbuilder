package com.looseboxes.htmlbuilder;

import com.bc.htmlbuilder.beans.HtmlFormatConfigBean;
import com.bc.jpa.context.PersistenceContext;
import com.bc.jpa.context.eclipselink.PersistenceContextEclipselinkOptimized;
import com.bc.jpa.context.PersistenceUnitContext;
import com.bc.jpa.dao.sql.MySQLDateTimePatterns;
import com.looseboxes.pu.entities.Product;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Dec 2, 2017 10:48:50 PM
 */
public class Main {

    private transient static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String [] args) {
        
        final String persistenceConfigFilepath = System.getProperty("user.home") + 
                "/Documents/NetBeansProjects/looseboxespu/src/test/resources/META-INF/persistence.xml";
        final URI persistenceConfigUri = new File(persistenceConfigFilepath).toURI();
        final String persistenceUnit = "looseboxesPU";
        final String baseUrl = "http://www.buzzwears.com";
        final String contextPath = "";
        final String defaultImageUrl = baseUrl + contextPath + "/images/logo.png";
        final int maxTextLength = 150;
        
        final PersistenceContext persistenceContext = new PersistenceContextEclipselinkOptimized(
                persistenceConfigUri, new MySQLDateTimePatterns());
        
        final PersistenceUnitContext puContext = persistenceContext.getContext(persistenceUnit);
        
        final HtmlFormatConfigBean config = new HtmlFormatConfigBean();
        
        config.setBaseUrl(baseUrl);
        config.setContextPath(contextPath);
        config.setDefaultImageUrl(defaultImageUrl);
        config.setMaxTextLength(maxTextLength);
        
//        final List<Integer> ids = Arrays.asList(218, 539, 725, 744, 776);
//        final List<Product> itemList = new FetchItemsByIds(puContext).apply(Product.class, ids);
        final List<Product> itemList = new ProductListSupplierDevmode(1080, 10).get();
        
        final String html = new ProductListHtmlFormat(config).apply(itemList);
        
        LOG.log(Level.INFO, "Generated HTML:\n{0}", html);
    }
}
