package com.jschool;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "cdiBean")
@Singleton
public class CdiBean implements Serializable {
    String field;
    List<CountByProduct> productList = null;

    @EJB
    private ReceiverBean receiverBean;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void someAction() {
        productList = receiverBean.getProductList();
        System.out.println(productList);

    }
}
