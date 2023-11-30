package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
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

    @JsonProperty("remetente")
    private Long remetente;

    @JsonProperty("receptor")
    private Long receptor;

    @JsonProperty("mensagens")
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<Mensagem> mensagens = new ArrayList<>();
}
