package com.codelephant.friendzone.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ChatPostPutRequestDTO {
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

    public ChatPostPutRequestDTO(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatPostPutRequestDTO chatPostPutRequestDTO = objectMapper.readValue(jsonString, ChatPostPutRequestDTO.class);
        this.remetente = chatPostPutRequestDTO.getRemetente();
        this.receptor = chatPostPutRequestDTO.getReceptor();
        this.conteudo = chatPostPutRequestDTO.getConteudo();
        this.data = chatPostPutRequestDTO.getData();
    }
}