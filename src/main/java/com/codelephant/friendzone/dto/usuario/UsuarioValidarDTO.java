package com.codelephant.friendzone.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioValidarDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("idGoogle")
    private Integer idGoogle;

    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;
}
