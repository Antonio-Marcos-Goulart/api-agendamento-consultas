package com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Endereço vinculado a um paciente ou médico")
public class EnderecoCreateDTO {

    @NotBlank
    @Schema(description = "Nome da rua/logradouro", example = "Rua das Flores")
    private String rua;

    @NotBlank
    @Schema(description = "Número do imóvel", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço (opcional)", example = "Apto 45")
    private String complemento;

    @NotBlank
    @Schema(description = "Bairro", example = "Centro")
    private String bairro;

    @NotBlank
    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @NotBlank
    @Schema(description = "Estado (UF)", example = "SP")
    private String estado;

    @NotBlank
    @Schema(description = "CEP", example = "01310-100")
    private String cep;
}