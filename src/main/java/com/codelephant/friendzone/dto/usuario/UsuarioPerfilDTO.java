package com.codelephant.friendzone.dto.usuario;

import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.SexoSelecionado;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPerfilDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("apelido")
    private String apelido;

    @JsonProperty("idade")
    private Integer idade;

    @JsonProperty("sexo")
    private SexoSelecionado sexo;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("tipoLogin")
    private LoginType loginType;

    @JsonProperty("date")
    private Date date = new Date();
}
