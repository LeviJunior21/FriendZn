package com.codelephant.friendzone.dto.comentario;

import com.codelephant.friendzone.dto.usuario.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGostarOuNaoDTO {
    @JsonProperty("gostou")
    private List<UsuarioDTO> gostou;

    @JsonProperty("naoGostou")
    private List<UsuarioDTO> naoGostou;
}
