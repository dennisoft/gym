package com.win.components.workouts;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "workout")
public class WorkOut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_id")
    private int workID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "day")
    private String day;

    @Column(name = "activity")
    private String activity;

    @Column(name = "sets")
    private int sets;

    @Column(name = "reps")
    private int reps;

    @Column(name = "kg")
    private int kg;

    @Column(name = "rest_time")
    private int restTime;
}
