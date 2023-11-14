package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mensagens")
public class Mensagem {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("remetente")
    @Column(nullable = false)
    private Long remetente;

    @JsonProperty("receptor")
    @Column(nullable = false)
    private Long receptor;

    @JsonProperty("conteudo")
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @JsonProperty("data")
    @Column(nullable = false)
    private Date data;

    @JsonProperty("conversa")
    @ManyToOne()
    @JoinColumn(name = "conversa_id", referencedColumnName = "id")
    private Conversa conversa;
}
