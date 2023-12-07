package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGostarOuNaoPostRequestDTO {
    @JsonProperty("idUsuario")
    private Long idUsuario;

    @JsonProperty("codigoAcesso")
    private Long codigoAcesso;

    @JsonProperty("idPublicacao")
    private Long idPublicacao;

    @JsonProperty("idComentario")
    private Long idComentario;

    @JsonProperty("gostar")
    private Long gostar;

    public ComentarioGostarOuNaoPostRequestDTO(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ComentarioGostarOuNaoPostRequestDTO requestDTO = objectMapper.readValue(json, ComentarioGostarOuNaoPostRequestDTO.class);
        this.idUsuario = requestDTO.getIdUsuario();
        this.codigoAcesso = requestDTO.getCodigoAcesso();
        this.idPublicacao = requestDTO.getIdPublicacao();
        this.idComentario = requestDTO.getIdComentario();
        this.gostar = requestDTO.getGostar();
    }
}
