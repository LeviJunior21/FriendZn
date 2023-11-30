package com.codelephant.friendzone.dto.comentario;

import com.codelephant.friendzone.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("comentario")
    private String comentario;

    @JsonProperty("usuarioId")
    private Long usuarioId;

    @JsonProperty("gostaram")
    private Set<Usuario> gostaram;

    @JsonProperty("naoGostaram")
    private Set<Usuario> naoGostaram;

    @JsonProperty("timestamp")
    private Date timestamp;
}
