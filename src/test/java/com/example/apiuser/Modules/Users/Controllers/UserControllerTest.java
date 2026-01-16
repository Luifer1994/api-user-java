package com.example.apiuser.Modules.Users.Controllers;

import com.example.apiuser.Modules.Users.Services.*;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Adiós AutoConfigureRestDocs
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Usamos el builder normal
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
// @AutoConfigureRestDocs <-- ESTO SE BORRA
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CreateUserService createUserService;
    @MockBean private GetAllUsersService getAllUsersService;
    @MockBean private FindUserByIdService findUserByIdService;
    @MockBean private DeleteUserByIdService deleteUserByIdService;
    @MockBean private UpdateUserService updateUserService;

    @Test
    public void getUserByIdTest() throws Exception {
        Long userId = 1000000L;
        UserResponseDTO mockUserResponse = UserResponseDTO.builder()
            .id(userId)
            .name("Usuario Prueba")
            .email("test@test.com")
            .build();

        when(findUserByIdService.execute(anyLong())).thenReturn(mockUserResponse);

        this.mockMvc.perform(get("/users/{id}", userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); 
                // .andDo(document(...)) <-- ESTO SE BORRA, YA NO DOCUMENTAMOS AQUÍ
    }
}