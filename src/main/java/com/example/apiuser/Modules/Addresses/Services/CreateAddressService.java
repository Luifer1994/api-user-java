package com.example.apiuser.Modules.Addresses.Services;

import com.example.apiuser.Exceptions.ResourceNotFoundException;
import com.example.apiuser.Modules.Addresses.Models.Address;
import com.example.apiuser.Modules.Addresses.Models.Dto.AddressMapper;
import com.example.apiuser.Modules.Addresses.Models.Dto.AddressRequestDTO;
import com.example.apiuser.Modules.Addresses.Models.Dto.AddressResponseDTO;
import com.example.apiuser.Modules.Addresses.Repositories.AddressRepository;
import com.example.apiuser.Modules.Users.Models.User;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public AddressResponseDTO execute(AddressRequestDTO dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        User user = this.userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUserId()));

        Address address = this.addressMapper.toEntity(dto);
        address.setUser(user);

        Address savedAddress = this.addressRepository.save(address);

        return this.addressMapper.toDTO(savedAddress);
    }
}
