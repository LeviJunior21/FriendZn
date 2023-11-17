package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioPostPutRequestDTO {
    @JsonProperty("comentario")
    @NotBlank(message = "Comentario invalido.")
    private String comentario;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private Integer codigoAcesso;

    @JsonProperty("idPublicacao")
    private Long idPublicacao;

    @JsonProperty("idUsuario")
    private Long idUsuario;

    public ComentarioPostPutRequestDTO(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ComentarioPostPutRequestDTO requestDTO = objectMapper.readValue(json, ComentarioPostPutRequestDTO.class);
        this.comentario = requestDTO.getComentario();
        this.codigoAcesso = requestDTO.getCodigoAcesso();
        this.idPublicacao = requestDTO.getIdPublicacao();
        this.idUsuario = requestDTO.getIdUsuario();
    }
}
