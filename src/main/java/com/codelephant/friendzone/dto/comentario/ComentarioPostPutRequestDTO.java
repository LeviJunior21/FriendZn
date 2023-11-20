package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @JsonProperty("idUsuario")
    @NotNull(message = "O id do usuario nao pode ser null")
    private Long idUsuario;

    @JsonProperty("timestamp")
    @NotNull(message = "A data esta envalida.")
    private Date timestamp;

    public ComentarioPostPutRequestDTO(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ComentarioPostPutRequestDTO requestDTO = objectMapper.readValue(json, ComentarioPostPutRequestDTO.class);
        this.comentario = requestDTO.getComentario();
        this.codigoAcesso = requestDTO.getCodigoAcesso();
        this.idUsuario = requestDTO.getIdUsuario();
        this.timestamp = requestDTO.getTimestamp();
    }
}
