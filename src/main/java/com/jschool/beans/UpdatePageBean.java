package com.jschool.beans;

import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UpdatePageBean implements Serializable {

    Logger logger = Logger.getLogger(this.getClass());

    @Inject
    @Push(channel = "channel1")
    private PushContext pushContext;

    @Inject
    private JsonReceiverBean jsonReceiverBean;

    private List<CountByProduct> productList = null;

    public void onUpdate(@Observes String message) {

        productList = jsonReceiverBean.getStatistic("30");
        logger.info("in Update page bean");
        pushContext.send("updated");
    }

    public List<CountByProduct> getProductList() {
        return productList;
    }
}
