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
@Operation(summary = "Actualizar Usuario", description = "Actualiza los datos de un usuario existente.")
@ApiResponse(responseCode = "200", description = "Actualizado",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Exito", ref = UserApiExamples.UPDATE_SUCCESS)))
@ApiResponse(responseCode = "409", description = "Conflicto",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Email Duplicado", ref = UserApiExamples.UPDATE_CONFLICT)))
@ApiResponse(responseCode = "400", description = "Error Validación",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Datos Inválidos", ref = UserApiExamples.UPDATE_VALIDATION)))
public @interface DocsUpdateUser {}