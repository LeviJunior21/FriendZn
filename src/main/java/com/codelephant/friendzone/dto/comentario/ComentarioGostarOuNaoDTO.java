package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGostarOuNaoDTO {
    @JsonProperty("gostou")
    private Integer gostou;

    @JsonProperty("naoGostou")
    private Integer naoGostou;
}
