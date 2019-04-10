package com.win.components.membership;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "user_id")
    private int userID;

    @Column (name = "membership_type")
    private String membershipType;

    @Column (name = "starting_date")
    private Date startingDate;

    @Column (name = "expire_date")
    private Date expireDate;

    @Column (name = "membership_status")
    private String membershipStatus;
}
