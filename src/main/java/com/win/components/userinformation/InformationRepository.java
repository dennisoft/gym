package com.win.components.userinformation;

import com.win.components.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Integer> {
    Information findByUserID(Integer ID);
}

