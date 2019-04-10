package com.win.components.sms;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ListIterator;

public class Prettify {
//    private void PrettifyResult(JSONObject result) {
//        try {
//            JSONObject body = (JSONObject) result.get("Body"),
//                    callback = (JSONObject) body.get("stkCallback"),
//                    prettyResult = new JSONObject();
//            String merchant = callback.get("MerchantRequestID") + "",
//                    checkoutID = callback.get("CheckoutRequestID") + "";
//            try {
//                prettyResult.put("MerchantRequestID", merchant);
//                prettyResult.put("CheckoutRequestID", checkoutID);
//                prettyResult.put("ResultDesc", callback.get("ResultDesc"));
//                prettyResult.put("ResultCode", callback.get("ResultCode"));
//                if (prettyResult.get("ResultCode").equals("0")) {
//                    JSONObject params = (JSONObject) callback.get("CallbackMetadata");
//                    JSONArray kvmap = (JSONArray) params.get("Item");
//                    ListIterator iterator = kvmap.listIterator();
//                    while (iterator.hasNext()) {
//                        JSONObject next = (JSONObject) iterator.next();
//                        prettyResult.put(next.get("Name"), next.get("Value"));
//                    }
//                }
//            } catch (Exception k) {
//
//            }
//        } catch (Exception ex) {
//
//        }
//    }
}