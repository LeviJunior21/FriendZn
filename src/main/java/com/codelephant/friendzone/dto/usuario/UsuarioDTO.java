package com.codelephant.friendzone.dto.usuario;

import com.codelephant.friendzone.model.Publicacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("apelido")
    private String apelido;

    @JsonProperty("publicacoes")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Publicacao> publicacoes;
}
