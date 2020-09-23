package com.playerteamservice.entity;

import com.model.Status;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Player extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.MERGE, CascadeType.DETACH})
    private Team team;

    private Status status;

    private LocalDate hiringDate;

    private LocalDate birthDate;
}
