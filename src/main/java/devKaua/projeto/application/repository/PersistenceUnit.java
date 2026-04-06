package devKaua.projeto.application.repository;

import devKaua.projeto.domain.Pet;

public interface PersistenceUnit {
    boolean salvar(Pet pet, String enderecoPetStr);
    boolean atualizar(Pet pet);
    boolean deletar(Pet pet);
}
