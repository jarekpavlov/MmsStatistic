package com.jschool.configuration;

import com.jschool.beans.ListenerBean;
import com.jschool.beans.UpdatePageBean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class StartUpBean {

    @Inject
    ListenerBean listenerBean;

    @Inject
    UpdatePageBean updatePageBean;

    @PostConstruct
    private void init() throws IOException, TimeoutException {
        updatePageBean.onUpdate("updated");
        listenerBean.init();
    }

}
