package devKaua.projeto.application.dtos;

import devKaua.projeto.domain.model.Endereco;
import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;


public record PetDto(UUID id, @NotBlank String name, @NotBlank LocalDate idade, String peso, String raca, @NotNull TipoAnimal tipoAnimal, @NotNull Sexo sexo, @NotNull Endereco endereco) {
}
