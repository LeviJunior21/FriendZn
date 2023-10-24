package com.codelephant.friendzone.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostPutRequestDTO {
    @JsonProperty("apelido")
    @NotBlank(message = "Apelido invalido.")
    private String apelido;

    @JsonProperty("email")
    @NotBlank(message = "Email invalido.")
    private String email;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invalido.")
    private Integer codigoAcesso;
}
