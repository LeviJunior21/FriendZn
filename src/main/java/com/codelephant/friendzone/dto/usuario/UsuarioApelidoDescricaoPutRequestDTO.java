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
public class UsuarioApelidoDescricaoPutRequestDTO {
    @JsonProperty("apelido")
    @NotEmpty(message = "Apelido invalido, ele nao pode ser vazio.")
    private String apelido;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invalido, nao pode ser nulo.")
    private Long codigoAcesso;
}
