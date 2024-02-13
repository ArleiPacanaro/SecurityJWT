package com.springsecutiry.fiap.modules.user.request;

// Para recuperar o Token.

public record UserAuthRequest(String login, String password) {
}
