package com.jschool.beans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;

import javax.ejb.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Singleton
public class JsonReceiverBean implements Serializable {

    Logger logger = Logger.getLogger(this.getClass());

    public List<CountByProduct> getStatistic(String daysBefore) {

        Client client = new Client();
        WebResource webResource = client.resource("http://localhost:8080/MmsPr/get-statistic");
        ObjectMapper objectMapper = new ObjectMapper();

        List<CountByProduct> countByProduct = null;
        MultivaluedMap<String, String> paramMap = new MultivaluedMapImpl();
        paramMap.add("days", daysBefore);

        try {
            ClientResponse response = webResource
                    .queryParams(paramMap)
                    .accept("application/json")
                    .header("user-agent", "")
                    .get(ClientResponse.class);
            if (response.getStatus() != 200) {
                logger.warn("can not get the JSON");
                throw new RuntimeException("Failed : HTTP error status : " + response.getStatus());
            }
            String output = response.getEntity(String.class);
            countByProduct = objectMapper.readValue(output, new TypeReference<List<CountByProduct>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countByProduct;

    }

}
