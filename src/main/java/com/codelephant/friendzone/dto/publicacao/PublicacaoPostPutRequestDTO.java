package com.codelephant.friendzone.dto.publicacao;

import com.codelephant.friendzone.utils.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer codigoAcesso;

    @JsonProperty("categoria")
    @NotNull(message = "Categoria invalida.")
    private Categoria categoria;
}
