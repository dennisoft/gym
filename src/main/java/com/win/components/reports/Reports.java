package com.win.components.reports;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id")
    private int reportID;

    @Column(name = "user_id")
    private int userID;

    @Column (name="date")
    private Date submissionDate;

    @Column(name = "height")
    private int height;

    @Column(name = "fat")
    private int fat;

    @Column(name = "weight")
    private int weight;

    @Column(name = "thigh")
    private int thigh;

    @Column(name = "waist")
    private int waist;

    @Column(name = "arms")
    private int arms;
}
