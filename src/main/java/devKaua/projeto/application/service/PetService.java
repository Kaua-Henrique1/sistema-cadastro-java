package devKaua.projeto.application.service;

import devKaua.projeto.application.dtos.PetDto;
import devKaua.projeto.domain.model.Pet;

import java.util.List;
import java.util.UUID;

public interface PetService {
    boolean cadastrar(PetDto petDto);
    boolean alterar(PetDto petDto);
    boolean remover(Long id);
    boolean listarPetsCompleta();
    List<Pet> listarPetsPorCriterio();
    void listarTodosPets();
}