package com.jschool.beans;

import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "jsonViewBean")
@SessionScoped
public class JsonViewBean implements Serializable {

    Logger logger = Logger.getLogger(this.getClass());

    @Inject
    private UpdatePageBean updatePageBean;

    public List<CountByProduct> getProductList() {

        logger.info("in jsonViewBean");
        return updatePageBean.getProductList();
    }
}
