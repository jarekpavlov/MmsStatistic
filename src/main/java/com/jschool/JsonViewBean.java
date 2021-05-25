package com.jschool;

import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "jsonViewBean")
@SessionScoped
public class JsonViewBean implements Serializable {


    @Inject
    private UpdatePageBean updatePageBean;

    public List<CountByProduct> getProductList() {

        System.out.println("in jsonViewBean");
        List<CountByProduct> productList = updatePageBean.getProductList();
        return productList;
    }
    public void someAction(){
        PrimeFaces.current().ajax().update("j_idt6:products_table");
    }




}
