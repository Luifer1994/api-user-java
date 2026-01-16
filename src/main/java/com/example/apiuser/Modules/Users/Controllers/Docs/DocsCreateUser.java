package com.example.apiuser.Modules.Users.Controllers.Docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Se usa sobre métodos
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Crear Usuario", description = "Registra un nuevo usuario en la base de datos.")
@ApiResponse(responseCode = "201", description = "Creado",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Exito", ref = UserApiExamples.CREATE_SUCCESS)))
@ApiResponse(responseCode = "400", description = "Error Validación",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Datos Inválidos", ref = UserApiExamples.CREATE_VALIDATION)))
@ApiResponse(responseCode = "409", description = "Conflicto",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Email Duplicado", ref = UserApiExamples.CREATE_CONFLICT)))
public @interface DocsCreateUser {
    // Esta interfaz vacía actúa como un "alias" o contenedor
}