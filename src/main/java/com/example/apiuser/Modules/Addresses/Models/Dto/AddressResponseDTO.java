package com.example.apiuser.Modules.Addresses.Models.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private UUID userId;
}
