package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
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
    private List<Comentario> comentarios;
}
