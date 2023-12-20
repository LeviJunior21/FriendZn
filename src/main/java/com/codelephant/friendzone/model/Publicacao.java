package com.codelephant.friendzone.model;

import com.codelephant.friendzone.utils.Categoria;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publicacoes")
public class Publicacao {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("usuario")
    @ManyToOne(optional = false)
    @JsonBackReference
    private Usuario usuario;

    @JsonProperty("publicacao")
    @Column(nullable = false)
    private String publicacao;

    @JsonProperty("date")
    @Column(nullable = false)
    private Date date;

    @JsonProperty("comentarios")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacao")
    @JsonManagedReference
    @JsonIgnore
    @Builder.Default
    private List<Comentario> comentarios = new ArrayList<>();

    @JsonProperty("categoria")
    @Column(nullable = false)
    private Categoria categoria;

    @JsonProperty("interessados")
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "publicacoesSeguidas")
    @JsonManagedReference
    @JsonIgnore
    @Builder.Default
    private Set<Usuario> interessados = new HashSet<>();
}
