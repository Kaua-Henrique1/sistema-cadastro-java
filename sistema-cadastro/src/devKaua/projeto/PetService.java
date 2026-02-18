package devKaua.projeto;

import devKaua.projeto.domain.Pet;

import java.util.List;

public interface PetService {
    void cadastrar(Pet pet);
    void alterar(Pet pet);
    void remover(Pet pet);
    List<Pet> listarPets();
}