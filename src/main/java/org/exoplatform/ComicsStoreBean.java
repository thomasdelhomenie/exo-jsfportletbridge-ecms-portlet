package org.exoplatform;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.exoplatform.model.Product;
import org.exoplatform.service.ContentService;
import org.exoplatform.service.ProductService;
import org.exoplatform.service.impl.ContentServiceImpl;
import org.exoplatform.service.impl.ProductServiceImpl;

@ViewScoped
@ManagedBean
public class ComicsStoreBean implements Serializable {

    private static final long serialVersionUID = -6239437588285327644L;
    
    private ContentService contentService;
    private ProductService productService;

    private List<Product> products;
    
    public ComicsStoreBean() {
    	PortletPreferences prefs = ((PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getPreferences();
    	String contentsRootPath = prefs.getValue("contentsRootPath", "");
    	
    	contentService = new ContentServiceImpl(contentsRootPath);
    	productService = new ProductServiceImpl();
    }

    public List<Product> getProducts() {
    	if(products == null) {
	    	products = productService.getProducts();
	    	
	    	for(Product product : products) {
	    		product.setThumbnailPath(contentService.getProductThumbnailPath(product.getId()));
	    		product.setPictures(contentService.getProductPictures(product.getId()));
	    	}
    	}
    	
    	return products;
    }
}