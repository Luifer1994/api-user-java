package com.example.apiuser.Modules.Users.Controllers;

import com.example.apiuser.Modules.Users.Controllers.Docs.*;
import com.example.apiuser.Modules.Users.Models.Dto.UserFilter;
import com.example.apiuser.Modules.Users.Models.Dto.UserRequestDTO;
import com.example.apiuser.Modules.Users.Services.*;
import com.example.apiuser.Rules.ValidationGroups;
import com.example.apiuser.Utils.HttpResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Gestión completa de usuarios (CRUD)")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserService createUserService;
    private final GetAllUsersService getAllUsersService;
    private final FindUserByIdService findUserByIdService;
    private final DeleteUserByIdService deleteUserByIdService;
    private final UpdateUserService updateUserService;

    @DocsCreateUser
    @PostMapping
    public ResponseEntity<Object> createUser(
            @RequestBody @Validated(ValidationGroups.OnCreate.class) UserRequestDTO user) {
        return HttpResponse.send("Created", HttpStatus.CREATED, this.createUserService.execute(user));
    }

    @DocsListUsers
    @GetMapping
    public ResponseEntity<Object> getAllUsers(
            @Parameter(hidden = false, required = false) UserFilter filter,
            @PageableDefault(page = 0, size = 10) @Parameter(description = "Paginación") Pageable pageable) {
        return HttpResponse.send("List retrieved", HttpStatus.OK,
                this.getAllUsersService.execute(filter, pageable));
    }

    @DocsGetUserById
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return HttpResponse.send("Found", HttpStatus.OK, this.findUserByIdService.execute(uuid));
        } catch (IllegalArgumentException e) {
            throw new com.example.apiuser.Exceptions.ResourceNotFoundException("user.not.found");
        }
    }

    @DocsDeleteUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable UUID id) {
        this.deleteUserByIdService.execute(id);
        return HttpResponse.send("Deleted", HttpStatus.NO_CONTENT);
    }

    @DocsUpdateUser
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(
            @RequestBody @Validated(ValidationGroups.OnUpdate.class) UserRequestDTO user,
            @PathVariable UUID id) {
        return HttpResponse.send("Updated", HttpStatus.OK, this.updateUserService.execute(user, id));
    }
}