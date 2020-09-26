package com.netflix.support.entities;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_TICKET")
@ApiModel(value = "Modelo Entidade User")
public class Ticket {

    @Column(name = "ID_SUPPORT_TICKET")
    @Id
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificação do ticket", required = true, value = "Identificação", name = "id", position = 1)
    private int id;

    @Column(name = "ID_USER")
    @ApiModelProperty(notes = "Identificação do usuário", required = true, value = "Identificação", name = "idUser", position = 2)
    private Integer idUser;

    @Column(name = "ID_SUPPORT_CATEGORY")
    @ApiModelProperty(notes = "Identificação da categoria", required = true, value = "Identificação", name = "categoryId", position = 3)
    private Integer categoryId;

    @Column(name = "TICKET_DESCRIPTION")
    @ApiModelProperty(notes = "Descrição", required = true, value = "Descrição", name = "description", position = 4)
    private String description;
}
