package com.codelephant.friendzone.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEmojiPatchRequestDTO {
    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso invalido")
    private Long codigoAcesso;
}
