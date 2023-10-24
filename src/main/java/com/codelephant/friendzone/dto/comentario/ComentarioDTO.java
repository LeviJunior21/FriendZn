package com.codelephant.friendzone.dto.comentario;


import com.codelephant.friendzone.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("comentario")
    private String comentario;

    @JsonProperty("usuario")
    private Usuario usuario;
}
