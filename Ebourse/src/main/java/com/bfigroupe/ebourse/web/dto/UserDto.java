package com.bfigroupe.ebourse.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bfigroupe.ebourse.validation.PasswordMatches;
import com.bfigroupe.ebourse.validation.ValidEmail;
import com.bfigroupe.ebourse.validation.ValidPassword;

@PasswordMatches
public class UserDto {
    @NotNull
    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;
    
    @NotNull
    @Size(min = 8, max = 8)
    private String cin;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    public String getCin() {
        return cin;
    }

    public void setCin(final String cin) {
        this.cin = cin;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [firstName=").append(firstName).append("]").append("[lastName=").append(lastName).append("]").append("[email").append(email).append("]").append("[cin=").append(cin).append("]").append("[password").append(password).append("]");
        return builder.toString();
    }
}