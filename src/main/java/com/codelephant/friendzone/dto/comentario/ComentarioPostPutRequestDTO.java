package com.codelephant.friendzone.dto.comentario;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("comentario")
    @NotBlank(message = "Comentario invalido.")
    private String comentario;
}
