package devKaua.projeto.infrastructure.repository;

import devKaua.projeto.domain.model.Pet;

public interface PersistenceUnit {
    boolean salvar(Pet pet, String enderecoPetStr);
    boolean atualizar(Pet pet);
    boolean deletar(Pet pet);
}
