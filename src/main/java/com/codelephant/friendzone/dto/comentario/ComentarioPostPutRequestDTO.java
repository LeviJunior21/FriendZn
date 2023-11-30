package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioPostPutRequestDTO {
    @JsonProperty("comentario")
    @NotBlank(message = "Comentario invalido.")
    private String comentario;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "O codigo de acesso nao pode ser null.")
    private Long codigoAcesso;

    @JsonProperty("idUsuario")
    @NotNull(message = "O id do usuario nao pode ser null.")
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
