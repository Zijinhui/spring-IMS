package com.Zi.spring_IMS.model.dto;

import com.Zi.spring_IMS.model.entity.Account_Status;
import com.Zi.spring_IMS.model.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    // use for existing user who wanna update uer profile
    @NotBlank(message = "Name is required.")
    private String name;

    // Username is read-only but not excluded from validation
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password; // Valid password: Password@123

    @NotNull(message = "Role is required.")
    private Role role;

    @NotNull(message = "Account status is required.")
    private Account_Status status;
}
