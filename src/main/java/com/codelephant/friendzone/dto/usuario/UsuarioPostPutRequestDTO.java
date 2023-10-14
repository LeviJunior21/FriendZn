package com.codelephant.friendzone.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostPutRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("apelido")
    @NotBlank(message = "Apelido invalido.")
    private String apelido;
}
