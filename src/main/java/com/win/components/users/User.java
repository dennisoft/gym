package com.win.components.users;

import com.win.components.membership.Member;
import com.win.components.userinformation.Information;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.win.components.roles.*;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "member" , joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<Member> membership;

    @OneToOne
    @JoinColumn(name="user_id")
    private Member membership;

    @OneToOne
    @JoinColumn(name="user_id")
    private Information otherInformation;
}
