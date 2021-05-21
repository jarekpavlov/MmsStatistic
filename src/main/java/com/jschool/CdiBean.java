package com.jschool;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "cdiBean")
@SessionScoped
public class CdiBean implements Serializable {
    String field;

    @EJB
    private EjbBean ejbBean;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void someAction() {
        List<CountByProduct> list = ejbBean.getStatistic("30");
        System.out.println(list);

    }
}
