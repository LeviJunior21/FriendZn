package com.codelephant.friendzone.dto.usuario;

import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.SexoSelecionado;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostPutRequestDTO {
    @JsonProperty("apelido")
    @NotBlank(message = "Apelido invalido.")
    private String apelido;

    @JsonProperty("idade")
    @NotNull(message = "Idade invalida.")
    private Integer idade;

    @JsonProperty("sexo")
    @NotNull(message = "Sexo invalido.")
    private SexoSelecionado sexo;

    @JsonProperty("email")
    @NotBlank(message = "Email invalido.")
    private String email;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invalido.")
    private Long codigoAcesso;

    @JsonProperty("loginType")
    @NotNull(message = "Tipo de login invalido.")
    private LoginType loginType;

    @JsonProperty("idAuth")
    @NotNull(message = "Id de autenticacao invalido.")
    private Long idAuth;
}
