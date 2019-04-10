package com.win.components.workouts;

import com.win.components.subscription.Subscription;
import com.win.components.userinformation.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOutRepository  extends JpaRepository<WorkOut, Integer> {
    List<WorkOut> findWorkOutByUserIDOrderByWorkID(Integer ID);
    WorkOut findWorkOutByWorkID (Integer ID);
}