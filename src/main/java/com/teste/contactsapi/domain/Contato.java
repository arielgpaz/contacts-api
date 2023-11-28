package com.teste.contactsapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "contatos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String contato;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

}
