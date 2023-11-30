package com.codelephant.friendzone.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioValidarDTO {
    @JsonProperty("email")
    @NotEmpty(message = "Email eh invalido")
    private String email;

    @JsonProperty("idGoogle")
    @NotNull(message = "ID Invalido")
    private Integer idGoogle;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invalido")
    private Long codigoAcesso;
}
