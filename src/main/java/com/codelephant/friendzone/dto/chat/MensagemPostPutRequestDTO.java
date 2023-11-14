package com.codelephant.friendzone.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
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
public class MensagemPostPutRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("remetente")
    @NotNull(message = "Id do remetente eh invalido.")
    private Long remetente;

    @JsonProperty("receptor")
    @NotNull(message = "Id do receptor eh invalido.")
    private Long receptor;

    @JsonProperty("conteudo")
    @NotEmpty(message = "Conteudo nao pode estar vazio.")
    private String conteudo;

    @JsonProperty("data")
    @NotNull(message = "Data invalida.")
    private Date data;
}
