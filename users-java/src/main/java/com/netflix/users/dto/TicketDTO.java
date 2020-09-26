package com.netflix.users.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    @ApiModelProperty(notes = "Identificação do usuário", required = true, name = "idUser", example = "1", position = 1)
    private Integer idUser;

    @ApiModelProperty(notes = "Categoria do ticket", required = true, name = "categoryId", example = "1", position = 2)
    private Integer categoryId;

    @ApiModelProperty(notes = "Descrição do ticket", required = true, name = "description", example = "Meu pagamento foi cobrado duas vezes", position = 3)
    private Integer description;
}
