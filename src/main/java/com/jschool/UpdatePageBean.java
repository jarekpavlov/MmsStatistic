package com.jschool;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UpdatePageBean implements Serializable {

    @Inject
    @Push
    private PushContext push;

    @Inject
    private JsonReceiverBean jsonReceiverBean;

    private List<CountByProduct> productList = null;

    public void onUpdate(@Observes String message) {

        productList = jsonReceiverBean.getStatistic("30");
        System.out.println("in Update page bean");
        push.send("updated");
    }

    public List<CountByProduct> getProductList() {
        return productList;
    }
}
