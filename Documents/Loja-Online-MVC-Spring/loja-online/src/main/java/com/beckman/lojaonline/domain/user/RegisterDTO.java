package com.beckman.lojaonline.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank @NotNull String name, @NotBlank @NotNull String password, @NotBlank @NotNull UserRole role) {

}
