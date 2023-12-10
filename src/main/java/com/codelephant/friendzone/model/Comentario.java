package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comentarios")
public class Comentario {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("comentario")
    @Column(nullable = false)
    private String comentario;

    @JsonProperty("usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private Long codigoAcesso;

    @JsonProperty("publicacao")
    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "publicacao_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publicacao publicacao;

    @JsonProperty("gostaram")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Usuario> gostaram = new HashSet<>();

    @JsonProperty("naoGostaram")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Usuario> naoGostaram = new HashSet<>();

    @JsonProperty("timestamp")
    private Date timestamp;
}
