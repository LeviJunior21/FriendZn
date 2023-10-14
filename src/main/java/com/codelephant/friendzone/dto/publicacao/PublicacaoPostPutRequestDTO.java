package com.codelephant.friendzone.dto.publicacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoPostPutRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("publicacao")
    @NotBlank(message = "Publicaaoo invalida.")
    private String publicacao;

    @JsonProperty("date")
    @NotNull(message = "Data inválida.")
    private Date date;
}
