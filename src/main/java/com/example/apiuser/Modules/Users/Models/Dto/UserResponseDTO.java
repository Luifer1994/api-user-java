package com.example.apiuser.Modules.Users.Models.Dto;

import com.example.apiuser.Modules.Addresses.Models.Dto.AddressResponseDTO;
import com.example.apiuser.Modules.Users.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String email;
    @Builder.Default
    private List<AddressResponseDTO> addresses = new ArrayList<>();
}