package com.example.apiuser.Modules.Users.Controllers.Docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Obtener por ID", description = "Busca un usuario específico por su ID único.")
@ApiResponse(responseCode = "200", description = "Encontrado",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Exito", ref = UserApiExamples.GET_SUCCESS)))
@ApiResponse(responseCode = "404", description = "No encontrado",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Not Found", ref = UserApiExamples.GET_NOT_FOUND)))
public @interface DocsGetUserById {}