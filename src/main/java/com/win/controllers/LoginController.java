package com.win.controllers;

import javax.validation.Valid;

import com.win.components.reports.ReportsResponse;
import com.win.components.subscription.SubscriptionResponse;
import com.win.components.users.*;
import com.win.components.users.UserService;
import com.win.components.workouts.WorkOutResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/registration")
    public @ResponseBody UserResponse createNewUserAPI(@Valid @RequestBody User user) {
        UserResponse registerResult = userService.register(user);
        return registerResult;
    }

    @PostMapping(value = "/login")
    public @ResponseBody UserResponse login(@Valid @RequestBody Map<String, String> body){
        String password = body.get("password");
        String email = body.get("email");
        UserResponse loginResult = userService.login(email, password);
        return  loginResult;
    }

    @PostMapping("/newpassword")
    public @ResponseBody UserResponse newPassword(@Valid @RequestBody Map<String, String> body){
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        String email = body.get("email");
        UserResponse changeResult = userService.changePassword(oldPassword, newPassword, email);
        return changeResult;
    }

    @PostMapping("/getuser")
    public @ResponseBody UserResponse getUser(@Valid @RequestBody Map<String, String> body){
        String userID = body.get("userid");
        UserResponse foundUser = userService.getUser(userID);
        return foundUser;
    }

    @PostMapping("/payment/new")
    public @ResponseBody SubscriptionResponse makePayment(@Valid @RequestBody Map<String, String> body) throws IOException {
        Double amount = Double.parseDouble(body.get("amount"));
        Integer account = Integer.parseInt(body.get("accountNumber"));
        SubscriptionResponse newPayment = userService.makePayment(amount,account);
        //SubscriptionResponse newPayment = userService.makePayment(600.00,6);
        return newPayment;
    }

    @PostMapping("/payment/callback")
    public @ResponseBody SubscriptionResponse confirmPayment(@Valid @RequestBody String body) {

        JSONParser ps = new JSONParser();
        SubscriptionResponse newPayment = new SubscriptionResponse();

        try {

            JSONObject jsonRequest = (JSONObject) ps.parse(body);

            JSONObject bodied = (JSONObject) jsonRequest.get("Body"), callback = (JSONObject) bodied.get("stkCallback"), prettyResult = new JSONObject();
            String merchant = callback.get("MerchantRequestID") + "", checkoutID = callback.get("CheckoutRequestID") + "";

            prettyResult.put("MerchantRequestID", merchant);
            prettyResult.put("CheckoutRequestID", checkoutID);
            prettyResult.put("ResultDesc", callback.get("ResultDesc"));
            prettyResult.put("ResultCode", callback.get("ResultCode"));
            String MPESAReceipt = "";
            if (callback.get("ResultCode").equals(0)) {
                JSONObject params = (JSONObject) callback.get("CallbackMetadata");
                JSONArray kvmap = (JSONArray) params.get("Item");
                ListIterator iterator = kvmap.listIterator();
                while (iterator.hasNext()) {
                    JSONObject next = (JSONObject) iterator.next();
                    prettyResult.put(next.get("Name"), next.get("Value"));
                }
                MPESAReceipt = prettyResult.get("MpesaReceiptNumber").toString();
            }

            //System.out.println(String.valueOf(prettyResult));

            System.out.println(String.valueOf(body));

            userService.receiveCallBack(
                    prettyResult.get("ResultCode").toString(),
                    prettyResult.get("ResultDesc").toString(),
                    prettyResult.get("CheckoutRequestID").toString(),
                    prettyResult.get("MerchantRequestID").toString(),
                    MPESAReceipt
            );

            newPayment.setResponseCode(prettyResult.get("ResultCode").toString());
            newPayment.setResponseDesc(prettyResult.get("ResultDesc").toString());

        } catch (org.json.simple.parser.ParseException e) {
            newPayment.setResponseCode("50000");
            newPayment.setResponseDesc("A fatal error has occured!");
        }

        return newPayment;
    }

    @GetMapping("/payment/all")
    public @ResponseBody SubscriptionResponse allPayments(){
        SubscriptionResponse newPayment = userService.getAllPayments();
        //SubscriptionResponse newPayment = userService.makePayment(600.00,6);
        return newPayment;
    }

    @PostMapping("/payment/get")
    public @ResponseBody SubscriptionResponse getPayments(@Valid @RequestBody Map<String, String> body){
        String userid = body.get("userid");
        SubscriptionResponse payments = userService.getPayments(userid.toString());
        return payments;
    }

    //addWorkOut(int userID, String day, String activity, int sets, int reps, int kg, int restTime)

    @PostMapping("/workout/new")
    public @ResponseBody WorkOutResponse addWorkOut(@Valid @RequestBody Map<String, String> body){
        Integer userID = Integer.parseInt(body.get("userID"));
        String day = body.get("day");
        String activity = body.get("activity");
        Integer sets = Integer.parseInt(body.get("sets"));
        Integer reps = Integer.parseInt(body.get("reps"));
        Integer kg = Integer.parseInt(body.get("kg"));
        Integer restTime = Integer.parseInt(body.get("restTime"));
        WorkOutResponse addedWorkOut = userService.addWorkOut(userID,day,activity,sets,reps,kg,restTime);
        return addedWorkOut;
    }

    @PostMapping("/workout/get")
    public @ResponseBody WorkOutResponse getWorkOut(@Valid @RequestBody Map<String, String> body){
        Integer userID = Integer.parseInt(body.get("userID"));
        WorkOutResponse foundWorkOut = userService.getWorkOuts(userID.toString());
        return foundWorkOut;
    }

    @PostMapping("/reports/get")
    public @ResponseBody ReportsResponse getReports(@Valid @RequestBody Map<String, String> body){
        Integer userID = Integer.parseInt(body.get("userID"));
        ReportsResponse foundWorkOut = userService.getReports(userID.toString());
        return foundWorkOut;
    }

    @PostMapping("/reports/new")
    public @ResponseBody ReportsResponse addReport(@Valid @RequestBody Map<String, String> body){
        String userID = body.get("userID");
        String dos = body.get("date");
        Integer height = Integer.parseInt(body.get("height"));
        Integer fat = Integer.parseInt(body.get("fat"));
        Integer weight = Integer.parseInt(body.get("weight"));
        Integer thigh = Integer.parseInt(body.get("thigh"));
        Integer waist = Integer.parseInt(body.get("waist"));
        Integer arms = Integer.parseInt(body.get("arms"));

        ReportsResponse addedReport = userService.addReport(userID,dos,height,fat,weight,thigh,waist,arms);
        return addedReport;
    }

    @PostMapping("/user/information")
    public @ResponseBody UserResponse updateInformation(@Valid @RequestBody Map<String, String> body) throws ParseException {
        String userID = body.get("userid");
        String dob = body.get("dob");
        //Date dob = new SimpleDateFormat("ddMMyyyy").parse(body.get("dob"));
        String homeTown = body.get("hometown");
        String city = body.get("city");
        String mobileNumber = body.get("mobile");
        //UserResponse updateInfo = new UserResponse("7889","Success","9","Manze");
        UserResponse updateInfo = userService.otherInformation(userID,dob,homeTown,city,mobileNumber);

        return updateInfo;
    }
}
