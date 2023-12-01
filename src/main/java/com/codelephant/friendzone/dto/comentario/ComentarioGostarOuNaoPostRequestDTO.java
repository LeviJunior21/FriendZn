package com.codelephant.friendzone.dto.comentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGostarOuNaoPostRequestDTO {
    @JsonProperty("idUsuario")
    @NotNull(message = "ID do usuario eh invalido.")
    private Long idUsuario;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de Acesso Invalido")
    private Long codigoAcesso;

    public ComentarioGostarOuNaoPostRequestDTO(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ComentarioGostarOuNaoPostRequestDTO requestDTO = objectMapper.readValue(json, ComentarioGostarOuNaoPostRequestDTO.class);
        this.idUsuario = requestDTO.getIdUsuario();
        this.codigoAcesso = requestDTO.getCodigoAcesso();
    }
}
