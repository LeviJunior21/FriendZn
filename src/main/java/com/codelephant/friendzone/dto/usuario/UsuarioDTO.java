package com.codelephant.friendzone.dto.usuario;

import com.codelephant.friendzone.dto.comentario.ComentarioGostarOuNaoPostRequestDTO;
import com.codelephant.friendzone.model.Publicacao;
import com.codelephant.friendzone.model.SexoSelecionado;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
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

    @JsonProperty("idade")
    private Integer idade;

    @JsonProperty("sexo")
    private SexoSelecionado sexo;

    @JsonProperty("descricao")
    private String descricao;
}
