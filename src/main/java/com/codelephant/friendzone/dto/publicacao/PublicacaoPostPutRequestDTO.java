package com.codelephant.friendzone.dto.publicacao;

import com.codelephant.friendzone.utils.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoPostPutRequestDTO {
    @JsonProperty("publicacao")
    @NotBlank(message = "Publicacao invalida.")
    private String publicacao;

    @JsonProperty("date")
    @NotNull(message = "Data invalida.")
    private Date date;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invaalido.")
    private Long codigoAcesso;

    @JsonProperty("categoria")
    @NotNull(message = "Categoria invalida.")
    private Categoria categoria;
}
