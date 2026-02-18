package devKaua.projeto;

import devKaua.projeto.domain.Pet;

public interface PersistenceUnit {
    boolean salvar();
    boolean atualizar(Pet pet, String linhaNova);
    boolean deletar(Pet pet);
}
