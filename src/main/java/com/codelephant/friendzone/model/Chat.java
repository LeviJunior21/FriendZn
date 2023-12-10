package com.codelephant.friendzone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("remetente")
    private Long remetente;

    @JsonProperty("receptor")
    private Long receptor;

    @JsonProperty("mensagem")
    private String mensagem;

    @JsonProperty("timestamp")
    private Date timestamp;
}
