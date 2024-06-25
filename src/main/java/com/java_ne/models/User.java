package com.java_ne.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java_ne.enumerations.user.EUserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"account"}), @UniqueConstraint(columnNames = {"username"})})
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Transient
    private String fullName;

    private LocalDate dob;

    private String mobile;

    @Column(name = "account")
    private String accountNumber;

    private double amount;

    @Transient
    private Integer age;
    public Integer getAge(){
        if (dob != null) {
            return Period.between(dob, LocalDate.now()).getYears();
        }
        return null;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @JsonIgnore
    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EUserStatus status = EUserStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Saving> savings;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Withdraw> withdrawals;

    public User(String username, String email, String password, EUserStatus status, boolean verified) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
    }
}
