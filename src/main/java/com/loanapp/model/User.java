package com.loanapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/2/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */

@Table(name = "users")
@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name= "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
