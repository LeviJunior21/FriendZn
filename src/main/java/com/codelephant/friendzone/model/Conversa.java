package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conversas")
public class Conversa {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("idUsuario1")
    private Long idUsuario1;

    @JsonProperty("idUsuario2")
    private Long idUsuario2;

    @JsonProperty("mensagens")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Mensagem> mensagens;
}
