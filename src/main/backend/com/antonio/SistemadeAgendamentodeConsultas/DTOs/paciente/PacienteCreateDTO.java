package com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados para cadastro ou atualização de um paciente")
public class PacienteCreateDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do paciente", example = "Maria Souza")
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    @Schema(description = "CPF contendo 11 dígitos numéricos, sem pontuação", example = "12345678901")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "\\d{10,11}", message = "Telefone inválido")
    @Schema(description = "Telefone com DDD, apenas números", example = "11987654321")
    private String telefone;

    @Email(message = "E-mail inválido")
    @Schema(description = "E-mail de contato", example = "maria.souza@email.com")
    private String email;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "15/03/1990", type = "string")
    private LocalDate dataNascimento;

    @Valid
    private EnderecoCreateDTO endereco;
}