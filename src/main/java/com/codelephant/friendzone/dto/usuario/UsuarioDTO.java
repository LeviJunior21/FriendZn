package com.codelephant.friendzone.dto.usuario;

import com.codelephant.friendzone.model.Publicacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("apelido")
    private String apelido;

    @JsonProperty("publicacoes")
    private List<Publicacao> publicacoes;
}
