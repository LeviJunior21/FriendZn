package com.codelephant.friendzone.dto.publicacao;

import com.codelephant.friendzone.model.Comentario;
import com.codelephant.friendzone.model.Usuario;
import com.codelephant.friendzone.utils.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
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

    @JsonProperty("usuario")
    private Usuario usuario;

    @JsonProperty("categoria")
    private Categoria categoria;
}
