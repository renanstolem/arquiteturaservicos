package com.netflix.movies.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MOVIE_WATCHED_INTEREST")
@ApiModel(value = "Modelo Entidade MOVIE WATCHED INTEREST")
public class MovieWatchedInterest {

    @Column(name = "ID_MOVIE_WATCHED_INTEREST")
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificação do filme assistido ou de interesse", required = true, value = "Identificação", name = "id", position = 1)
    private int id;

    @Column(name = "ID_USER")
    @ApiModelProperty(notes = "Identificação do usuário", required = true, value = "Identificação do usuário", name = "idUser", position = 2)
    private Integer idUser;

    @Column(name = "ID_MOVIE")
    @ApiModelProperty(notes = "Identificação do filme", required = true, value = "Identificação do filme", name = "idMovie", position = 3)
    private Integer idMovie;

    @Column(name = "WATCHED")
    @ApiModelProperty(notes = "Filme assistido", required = true, value = "Assistido", name = "watched", position = 4)
    private Boolean watched;

    @Column(name = "INTEREST")
    @ApiModelProperty(notes = "Possui interesse no filme", required = true, value = "Interesse", name = "interest", position = 5)
    private Boolean interest;
}
