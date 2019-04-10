package com.win.components.userinformation;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "information")
public class Information {

    @Id
    @Column(name = "user_id")
    private int userID;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "home_town")
    private String homeTown;

    @Column (name = "city")
    private String city;

    @Column (name = "mobile_number")
    private String mobileNumber;
}
