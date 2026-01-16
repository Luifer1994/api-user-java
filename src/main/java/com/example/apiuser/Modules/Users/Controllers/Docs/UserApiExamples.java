package com.example.apiuser.Modules.Users.Controllers.Docs;

public class UserApiExamples {

    // La lógica es: [nombre-archivo]-[clave-json]
    
    // Archivo: create-user.json
    public static final String CREATE_SUCCESS    = "create-user-success";
    public static final String CREATE_VALIDATION = "create-user-error-400"; // Nota: en tu json pusiste "error_400"
    public static final String CREATE_CONFLICT   = "create-user-error-409";

    // Archivo: update-user.json
    public static final String UPDATE_SUCCESS    = "update-user-success";
    public static final String UPDATE_VALIDATION = "update-user-error-400";
    public static final String UPDATE_CONFLICT   = "update-user-error-409";

    // Archivo: get-user-by-id.json
    public static final String GET_SUCCESS       = "get-user-by-id-success";
    public static final String GET_NOT_FOUND     = "get-user-by-id-error-404";

    // Archivo: list-users.json
    public static final String LIST_SUCCESS      = "list-users-success";
}