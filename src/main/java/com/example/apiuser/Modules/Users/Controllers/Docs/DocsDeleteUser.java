package com.example.apiuser.Modules.Users.Controllers.Docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Eliminar Usuario", description = "Borra un usuario del sistema.")
@ApiResponse(responseCode = "204", description = "Eliminado correctamente")
public @interface DocsDeleteUser {}