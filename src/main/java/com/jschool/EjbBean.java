package com.jschool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.List;

@LocalBean
@Stateless
public class EjbBean {

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
                    .header("user-agent","")
                    .get(ClientResponse.class);
            if (response.getStatus() != 200) {
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
