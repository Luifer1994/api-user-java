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
@Operation(summary = "Listar Usuarios", description = "Obtiene usuarios paginados y filtrados.")
@ApiResponse(responseCode = "200", description = "Lista OK",
        content = @Content(mediaType = "application/json",
                examples = @ExampleObject(name = "Lista", ref = UserApiExamples.LIST_SUCCESS)))
public @interface DocsListUsers {}