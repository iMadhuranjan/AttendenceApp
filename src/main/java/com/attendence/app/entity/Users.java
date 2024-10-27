package com.attendence.app.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users implements UserDetails{

    @Id
    @NotBlank(message = "Username is Required")
    private String username;

    @NotBlank(message = "Password is Required")
    private String password;

    private Long phoneNumber;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is Required")
    private 
    
    String email;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    List<Attendence> attendence;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roles=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> role= roles.stream().map(
            rolea-> new SimpleGrantedAuthority(rolea)
        ).collect(Collectors.toList());
        return role;

    }
}
