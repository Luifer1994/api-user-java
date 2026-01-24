package com.example.apiuser.Modules.Addresses.Models.Dto;

import com.example.apiuser.Modules.Addresses.Models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "user.id", target = "userId")
    AddressResponseDTO toDTO(Address address);

    @Mapping(source = "userId", target = "user.id")
    Address toEntity(AddressRequestDTO addressRequestDTO);
}
