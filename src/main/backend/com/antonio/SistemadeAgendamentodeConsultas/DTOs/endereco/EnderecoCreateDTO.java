package com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCreateDTO {
    @NotBlank private String rua;
    @NotBlank private String numero;
    private String complemento;
    @NotBlank private String bairro;
    @NotBlank private String cidade;
    @NotBlank private String estado;
    @NotBlank private String cep;
}