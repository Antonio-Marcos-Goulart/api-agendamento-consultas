package com.antonio.SistemadeAgendamentodeConsultas.DTOs.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    @NotNull
    @NotBlank
    private String destinatario;

    @NotNull
    @NotBlank
    private String assunto;

    @NotNull
    @NotBlank
    private String conteudo;

    private String anexo;
}
