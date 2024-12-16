package com.Zi.spring_IMS.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username is required. Please use email as your username!")
    @Email(message = "Invalid username(email) format.")
    private String username; // Valid email: user@example.com

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password; // Valid password: Password@123

//    //@NotNull(message = "Role is required")
//    private Role role = Role.STAFF; // e.g., ADMIN, MANAGER, STAFF(default)
//
//    //@NotNull(message = "Account status is required.")
//    private Account_Status status = Account_Status.ACTIVE; // Active| Suspended
}
