package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Data
@Entity
@Getter
@Setter
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

    @JsonProperty("idade")
    @Column(nullable = false)
    private Integer idade;

    @JsonProperty("sexo")
    @Column(nullable = false)
    private SexoSelecionado sexo;

    @JsonProperty("email")
    private String email;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private Long codigoAcesso;

    @JsonProperty("publicacoes")
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @Builder.Default
    private List<Publicacao> publicacoes = new ArrayList<>();

    @JsonProperty("gostaram")
    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Comentario> gostaram = new HashSet<>();

    @JsonProperty("naoGostaram")
    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Comentario> naoGostaram = new HashSet<>();

    @JsonProperty("publicacoesSeguidas")
    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Publicacao> publicacoesSeguidas = new HashSet<>();

    @JsonProperty("descricao")
    @Builder.Default
    private String descricao = "";

    @JsonProperty("tipoLogin")
    @Column(nullable = false)
    private LoginType loginType;

    @JsonProperty("date")
    @Column(nullable = false)
    @Builder.Default
    private Date date = new Date();

    @JsonProperty("emoji")
    @Builder.Default
    private String emoji = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicacao that = (Publicacao) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
