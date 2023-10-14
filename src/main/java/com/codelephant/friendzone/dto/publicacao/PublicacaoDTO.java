package com.codelephant.friendzone.dto.publicacao;

import com.codelephant.friendzone.model.Comentario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicacaoDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("publicacao")
    private String publicacao;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("comentarios")
    private List<Comentario> comentarios;
}
