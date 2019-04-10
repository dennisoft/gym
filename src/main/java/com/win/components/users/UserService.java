package com.win.components.users;

import com.win.components.payment.Pay;
import com.win.components.payment.PayResponse;
import com.win.components.reports.ReportRepository;
import com.win.components.reports.Reports;
import com.win.components.reports.ReportsResponse;
import com.win.components.roles.Role;
import com.win.components.roles.RoleRepository;
import com.win.components.sms.SendSMS;
import com.win.components.subscription.Subscription;
import com.win.components.subscription.SubscriptionRepository;
import com.win.components.subscription.SubscriptionResponse;
import com.win.components.userinformation.Information;
import com.win.components.userinformation.InformationRepository;
import com.win.components.workouts.WorkOut;
import com.win.components.workouts.WorkOutRepository;
import com.win.components.workouts.WorkOutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private InformationRepository informationRepository;
    private SubscriptionRepository subscriptionRepository;
    private WorkOutRepository workOutRepository;
    private ReportRepository reportRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, InformationRepository informationRepository, SubscriptionRepository subscriptionRepository, WorkOutRepository workOutRepository, ReportRepository reportRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.informationRepository = informationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.workOutRepository = workOutRepository;
        this.reportRepository = reportRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserService userService;

    public List<WorkOut> findWorkOutByUserID(Integer ID) {
        return workOutRepository.findWorkOutByUserIDOrderByWorkID(ID);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Information findInformationByUserID(Integer ID) {
        return informationRepository.findByUserID(ID);
    }
    public User findUserByUserID(Integer ID) {
        return userRepository.findById(ID);
    }

    public List<Subscription> findSubscriptionByUserID(Integer ID) {
        return subscriptionRepository.findSubscriptionByUserID(ID);
    }

    public List<Reports> findMyReports (Integer ID) {
        return reportRepository.findReportsByUserID(ID);
    }

    public List<Subscription> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription findSubscriptionByMerchantRequestID(String ID) {
        return subscriptionRepository.findSubscriptionByMerchantRequestID(ID);
    }

    public void addPaymentRecord(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void addNewWorkOut(WorkOut workOut) {
        workOutRepository.save(workOut);
    }

    public UserResponse register(User user) {
        UserResponse modelAndView = new UserResponse();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            modelAndView.setResponseCode("3");
            modelAndView.setResponseDesc("Failed");
            modelAndView.setDetailedResponseDesc("A user with the same email address already exists");
        } else {
            try {
                userService.saveUser(user);
                //UserResponse modelView = new UserResponse("0", "Success", "User registered successfully");
                modelAndView.setResponseCode("0");
                modelAndView.setResponseDesc("Success");
                modelAndView.setDetailedResponseDesc("User registered successfully");
                modelAndView.setUser(new HashSet<User>(Arrays.asList(userRepository.findByEmail(user.getEmail()))));
            } catch (Exception e) {
                modelAndView.setResponseCode("1");
                modelAndView.setResponseDesc("Failed");
                modelAndView.setDetailedResponseDesc(e.getMessage());
            }
        }
        return modelAndView;
    }

    public UserResponse login(String email, String password) {
        User userExists = this.findUserByEmail(email);

        UserResponse modelAndView = new UserResponse();
        if (userExists == null) {
            modelAndView.setResponseCode("3");
            modelAndView.setResponseDesc("Failed");
            modelAndView.setDetailedResponseDesc("No user with this email address exists");
        } else {
            if (bCryptPasswordEncoder.matches(password, userExists.getPassword())) {
                modelAndView.setResponseCode("0");
                modelAndView.setResponseDesc("Success");
                modelAndView.setUserID(Integer.toString(userExists.getId()));
                modelAndView.setDetailedResponseDesc("Valid email and password provided");
            } else {
                modelAndView.setResponseCode("2");
                modelAndView.setResponseDesc("Failed");
                modelAndView.setDetailedResponseDesc("Wrong password");
            }
        }
        return modelAndView;
    }

    public UserResponse changePassword(String oldPassword, String newPassword, String email) {
        User userExists = this.findUserByEmail(email);
        UserResponse modelAndView = new UserResponse();

        if (userExists == null) {
            modelAndView.setResponseCode("3");
            modelAndView.setResponseDesc("Failed");
            modelAndView.setDetailedResponseDesc("No user with this email address exists");
        } else {
            if (bCryptPasswordEncoder.matches(oldPassword, userExists.getPassword())) {

                userExists.setPassword(bCryptPasswordEncoder.encode(newPassword));

                try {
                    userRepository.save(userExists);
                    modelAndView.setResponseCode("0");
                    modelAndView.setResponseDesc("Success");
                    modelAndView.setDetailedResponseDesc("Password changed successfully");
                } catch (Exception e) {
                    modelAndView.setResponseCode("1");
                    modelAndView.setResponseDesc("Failed");
                    modelAndView.setDetailedResponseDesc(e.getMessage());
                }

            } else {
                modelAndView.setResponseCode("2");
                modelAndView.setResponseDesc("Failed");
                modelAndView.setDetailedResponseDesc("The old password is wrong");
            }
        }
        return modelAndView;
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public UserResponse getUser(String userID) {
        UserResponse user = new UserResponse();
        User foundUser = userRepository.findById(Integer.parseInt(userID));

        if (foundUser == null) {
            user.setResponseCode("3");
            user.setResponseDesc("Failed");
            user.setDetailedResponseDesc("User not found");
        } else {
            user.setResponseCode("0");
            user.setResponseDesc("Success");
            user.setDetailedResponseDesc("This user exists");
            user.setUserID(Integer.toString(foundUser.getId()));
            user.setUser(new HashSet<User>(Arrays.asList(foundUser)));
        }
        return user;
    }

    public UserResponse otherInformation(String userID, String dob, String homeTown, String city, String mobileNumber) {

        User foundUser = userService.findUserByUserID(Integer.parseInt(userID));
        Information info = userService.findInformationByUserID(Integer.parseInt(userID));
        UserResponse user = new UserResponse();

        if (foundUser == null || info == null) {
            user.setResponseCode("3");
            user.setResponseDesc("Failed");
            user.setDetailedResponseDesc("User not found");

        } else {
            try {
                info.setUserID(Integer.parseInt(userID));
                Date dobo = new SimpleDateFormat("ddMMyyyy").parse(dob);
                info.setDob(dobo);
                info.setCity(city);
                info.setMobileNumber(mobileNumber);
                info.setHomeTown(homeTown);

                foundUser.setOtherInformation(info);
                //userService.saveUser(foundUser);
                userRepository.save(foundUser);

                user.setResponseCode("0");
                user.setResponseDesc("Success");
                user.setDetailedResponseDesc("Password changed successfully");
            } catch (Exception e) {
                user.setResponseCode("1");
                user.setResponseDesc("Failed");
                user.setDetailedResponseDesc(e.getMessage());
            }
        }
        return user;
    }

    public SubscriptionResponse makePayment(Double amount, Integer accountNumber) throws IOException {
        SubscriptionResponse modelAndView = new SubscriptionResponse();
        Subscription newPayment = new Subscription();
        try {

            Date date = new Date();

            Pay payment = new Pay();
            PayResponse results = payment.getToken();

            if (results.getResponseDescription().equals("Success. Request accepted for processing")) {
                newPayment.setResponseCode("9090");
            } else {
                newPayment.setResponseCode(results.getResponseCode());
            }

            newPayment.setResponseDesc(results.getResponseDescription());
            newPayment.setAmount(amount);
            newPayment.setUserID(accountNumber);
            newPayment.setPaymentStatus("New");
            newPayment.setMpesaReceipt("");
            newPayment.setPaidAmount(amount);
            newPayment.setPaymentDate(date);
            newPayment.setCheckoutRequestID(results.getCheckoutRequestID());
            newPayment.setMerchantRequestID(results.getMerchantRequestID());

            modelAndView.setResponseCode(results.getResponseCode());
            modelAndView.setResponseDesc(results.getResponseDescription());
            modelAndView.setUserID(results.getMerchantRequestID());
            modelAndView.setDetailedResponseDesc(results.getCustomerMessage());
            modelAndView.setSubscription(new HashSet<Subscription>(Arrays.asList(newPayment)));

            userService.addPaymentRecord(newPayment);

        } catch (Exception e) {
            modelAndView.setResponseCode("1");
            modelAndView.setResponseDesc("Failed");
            modelAndView.setDetailedResponseDesc(e.getMessage());
        }

        return modelAndView;
    }

    public SubscriptionResponse getPayments(String userID){
        SubscriptionResponse subscription = new SubscriptionResponse();
        List<Subscription> foundPayment = userService.findSubscriptionByUserID(Integer.parseInt(userID));

        if (foundPayment == null) {
            subscription.setResponseCode("3");
            subscription.setResponseDesc("Failed");
            subscription.setDetailedResponseDesc("User not found");
        } else {
            subscription.setResponseCode("0");
            subscription.setResponseDesc("Success");
            subscription.setDetailedResponseDesc("This user exists");
            //subscription.setUserID(Integer.toString(foundPayment.getUserID()));
            Set<Subscription> mySet = new HashSet(Arrays.asList(foundPayment.toArray()));
            subscription.setSubscription(mySet);
        }
        return subscription;
    }

    public SubscriptionResponse getAllPayments(){
        SubscriptionResponse allSubscription = new SubscriptionResponse();
        List<Subscription> allFoundPayment = userService.findAllSubscriptions();

        if (allFoundPayment == null) {
            allSubscription.setResponseCode("3");
            allSubscription.setResponseDesc("Failed");
            allSubscription.setDetailedResponseDesc("No payments found");
        } else {
            allSubscription.setResponseCode("0");
            allSubscription.setResponseDesc("Success");
            allSubscription.setDetailedResponseDesc("Payements retrieved successfully");
            Set<Subscription> mySet = new HashSet(Arrays.asList(allFoundPayment.toArray()));
            allSubscription.setSubscription(mySet);
        }
        return allSubscription;
    }

    public void receiveCallBack(String responseCode, String responseDesc, String checkOutRequestID, String merchantRequestID, String receipt) {

        Subscription foundPayment = userService.findSubscriptionByMerchantRequestID(merchantRequestID);
        System.out.println(responseCode);

        String SMS = "";
        String status = "";
        if (foundPayment != null) {
            foundPayment.setResponseCode(responseCode);
            foundPayment.setResponseDesc(responseDesc);
            foundPayment.setMpesaReceipt(receipt);

            if (responseCode.equals("2001")) {
                SMS = "Dear customer, you have entered the wrong PIN. Please try again";
                status = "Failed";
            } else if (responseCode.equals("1")) {
                SMS = "Dear customer, your M-PESA balance is insufficient for this transaction. Kindly recharge and try again";
                status = "Failed";
            } else if (responseCode.equals("1032")) {
                SMS = "Failed. You have cancelled the request. Kindly try again.";
                status = "Failed";
            } else if (responseCode.equals("1031")) {
                SMS = "Failed. The request has timed out. Kindly try again.";
                status = "Failed";
            } else if (responseCode.equals("1037")) {
                SMS = "Kindly dial *234*1*6# to upgrade your SIM Card within 24 hours.";
                status = "Failed";
            } else if (responseCode.equals("1036")) {
                SMS = "Failed. Kindly try again after 10 minutes";
                status = "Failed";
            } else if (responseCode.equals("1001")) {
                SMS = "Failed. M-PESA is processing a similar request. Please wait while we complete your initial request.";
                status = "Failed";
            } else if (responseCode.equals("0")) {
                SMS = "Your payment has been received. Thanks for subscribing to Fitness Center.";
                status = "Success";
            } else {
                SMS = "The system is currently unavailable. Please try again later.";
                status = "Failed";
            }

            System.out.println(status + " - " + SMS);
            foundPayment.setPaymentStatus(status);
            subscriptionRepository.save(foundPayment);

//            User userExists = this.findUserByUserID(foundPayment.getUserID());
//            SendSMS notify = new SendSMS();
//            String response = notify.sendSms(SMS,userExists.getOtherInformation().getMobileNumber());
//            System.out.println(response);
        }
    }

    public WorkOutResponse getWorkOuts(String userID){
        WorkOutResponse workout = new WorkOutResponse();
        List<WorkOut> foundWorkOut = userService.findWorkOutByUserID(Integer.parseInt(userID));

        if (foundWorkOut == null) {
            workout.setResponseCode("3");
            workout.setResponseDesc("Failed");
            workout.setDetailedResponseDesc("User not found");
        } else {
            workout.setResponseCode("0");
            workout.setResponseDesc("Success");
            workout.setDetailedResponseDesc("This user exists");
            //subscription.setUserID(Integer.toString(foundPayment.getUserID()));
            Set<WorkOut> mySet = new HashSet(Arrays.asList(foundWorkOut.toArray()));
            workout.setWorkOut(mySet);
        }
        return workout;
    }

    public WorkOutResponse addWorkOut(int userID, String day, String activity, int sets, int reps, int kg, int restTime) {
        WorkOutResponse modelAndView = new WorkOutResponse();
        WorkOut newWorkOut = new WorkOut();
        try {

            newWorkOut.setActivity(activity);
            newWorkOut.setUserID(userID);
            newWorkOut.setDay(day);
            newWorkOut.setKg(kg);
            newWorkOut.setReps(reps);
            newWorkOut.setRestTime(restTime);
            newWorkOut.setSets(sets);
            userService.addNewWorkOut(newWorkOut);

            modelAndView.setResponseCode("0");
            modelAndView.setResponseDesc("Success");
            modelAndView.setDetailedResponseDesc("Request accepted for processing");
            modelAndView.setWorkOut(new HashSet<WorkOut>(Arrays.asList(workOutRepository.findWorkOutByWorkID(newWorkOut.getWorkID()))));

        } catch (Exception e) {
            modelAndView.setResponseCode("1");
            modelAndView.setResponseDesc("Failed");
            modelAndView.setDetailedResponseDesc(e.getMessage());
        }

        return modelAndView;
    }

    public ReportsResponse getReports(String userID){
        ReportsResponse reports = new ReportsResponse();
        List<Reports> foundReports = userService.findMyReports(Integer.parseInt(userID));

        if (foundReports.size() == 0) {
            reports.setResponseCode("3");
            reports.setResponseDesc("Failed");
            reports.setDetailedResponseDesc("Report entries not found");
        } else {
            reports.setResponseCode("0");
            reports.setResponseDesc("Success");
            reports.setDetailedResponseDesc("This user exists");
            //subscription.setUserID(Integer.toString(foundPayment.getUserID()));
            Set<Reports> mySet = new HashSet(Arrays.asList(foundReports.toArray()));
            reports.setReports(mySet);
        }
        return reports;
    }

    public ReportsResponse addReport(String userID, String dos, Integer height, Integer fat, Integer weight, Integer thigh, Integer waist, Integer arms) {

        User foundUser = userService.findUserByUserID(Integer.parseInt(userID));
        ReportsResponse reports = new ReportsResponse();
        Reports report = new Reports();

        if (foundUser == null) {
            reports.setResponseCode("3");
            reports.setResponseDesc("Failed");
            reports.setDetailedResponseDesc("User not found");

        } else {
            try {
                report.setUserID(Integer.parseInt(userID));
                Date doso = new SimpleDateFormat("ddMMyyyy").parse(dos);
                report.setSubmissionDate(doso);
                report.setArms(arms);
                report.setHeight(height);
                report.setFat(fat);
                report.setWeight(weight);
                report.setThigh(thigh);
                report.setWaist(waist);

                reports.setReports(new HashSet<Reports>(Arrays.asList(report)));
                //userService.saveUser(foundUser);
                reportRepository.save(report);

                reports.setResponseCode("0");
                reports.setResponseDesc("Success");
                reports.setDetailedResponseDesc("Report submitted successfully.");
            } catch (Exception e) {
                reports.setResponseCode("1");
                reports.setResponseDesc("Failed");
                reports.setDetailedResponseDesc(e.getMessage());
            }
        }
        return reports;
    }

}