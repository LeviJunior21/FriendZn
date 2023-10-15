package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("apelido")
    @Column(nullable = false)
    private String apelido;

    @JsonProperty("email")
    @Column(nullable = false)
    private String email;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private Integer codigoAcesso;

    @JsonProperty("publicacoes")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Publicacao> publicacoes;
}
