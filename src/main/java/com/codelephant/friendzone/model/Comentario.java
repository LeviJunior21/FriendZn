package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Set;

@Entity
@Data
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
    private Integer codigoAcesso;

    @JsonProperty("publicacao")
    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "publicacao_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publicacao publicacao;

    @JsonProperty("gostaram")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Usuario> gostaram;

    @JsonProperty("naoGostaram")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Usuario> naoGostaram;

    @JsonProperty("timestamp")
    private Date timestamp;
}
