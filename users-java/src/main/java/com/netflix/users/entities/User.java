package com.netflix.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
@ApiModel(value = "Modelo Entidade User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_USER")
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificação do usuário", required = true, value = "Identificação", name = "id", position = 1)
    private int id;
}
