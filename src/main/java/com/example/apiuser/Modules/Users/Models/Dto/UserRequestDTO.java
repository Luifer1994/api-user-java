package com.example.apiuser.Modules.Users.Models.Dto;

import com.example.apiuser.Modules.Users.Models.User;
import com.example.apiuser.Rules.UniqueValue;
import com.example.apiuser.Rules.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

        @NotBlank(message = "The name is mandatory", groups = { ValidationGroups.OnCreate.class,
                        ValidationGroups.OnUpdate.class })
        private String name;

        @NotBlank(message = "The password is mandatory", groups = { ValidationGroups.OnCreate.class })
        private String password;

        @NotBlank(message = "The email is mandatory", groups = { ValidationGroups.OnCreate.class,
                        ValidationGroups.OnUpdate.class })
        @Email(message = "Invalid email format", groups = { ValidationGroups.OnCreate.class,
                        ValidationGroups.OnUpdate.class })
        @UniqueValue(entityClass = User.class, field = "email", message = "The email is already in use", groups = ValidationGroups.OnCreate.class)
        private String email;

        public User toEntity() {
                return User.builder()
                                .name(this.name)
                                .email(this.email)
                                .build();
        }
}