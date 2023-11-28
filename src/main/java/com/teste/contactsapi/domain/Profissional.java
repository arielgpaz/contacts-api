package com.teste.contactsapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "profissionais")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Cargo cargo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nascimento;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @JsonProperty("deleted")
    private boolean deleted;

    @PrePersist
    void prePersist() {
        this.deleted = Boolean.FALSE;
    }

}
