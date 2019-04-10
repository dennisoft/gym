package com.win.components.payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class Pay {

    public PayResponse getToken() throws IOException {

        MPESA mpesa = new MPESA(
                "2M4JRJLJJAPuEGELYA2bM0ow9hnDVfNW",
                "bEmR4btIaLbwDrug"
        );

        String response = mpesa.STKPushSimulation(
                "174379",
                "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTkwNDAxMDgxNzQ5",
                "20190401081749",
                "CustomerPayBillOnline",
                "1",
                "254701562852",
                "254701562852",
                "174379",
                "http://ccd702c4.ngrok.io/payment/callback",
                "6",
                "EveGym"
        );


        ObjectMapper mapper = new ObjectMapper();
        PayResponse responseCode = new PayResponse();

        try {

            Map<String, Object> carMap = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});

            responseCode.setResponseCode((String) carMap.get("ResponseCode"));
            responseCode.setResponseDescription((String) carMap.get("ResponseDescription"));
            responseCode.setCustomerMessage((String) carMap.get("CustomerMessage"));
            responseCode.setMerchantRequestID((String) carMap.get("MerchantRequestID"));
            responseCode.setCheckoutRequestID((String) carMap.get("CheckoutRequestID"));

            System.out.println("----------------------");
            System.out.println(carMap.get("ResponseDescription"));
            System.out.println("----------------------");

        } catch (Exception e) {
            responseCode.setResponseCode("5000");
            responseCode.setResponseDescription(e.getMessage());
            responseCode.setCustomerMessage(e.getMessage());
        }

        return responseCode;
    }

}