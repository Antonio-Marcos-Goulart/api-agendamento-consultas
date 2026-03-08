package com.antonio.SistemadeAgendamentodeConsultas.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable // Anotação para indicar que esta classe pode ser usada como um tipo embutido em outra entidade
public class Endereco {

    @NotBlank(message = "Rua não pode ser vazia")
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotBlank(message = "Número não pode ser vazio")
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotBlank(message = "Bairro não pode ser vazio")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "Cidade não pode ser vazia")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank(message = "Estado não pode ser vazio")
    @Column(name = "estado", nullable = false)
    private String estado;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
    @Column(name = "cep", nullable = false)
    private String cep;
}
