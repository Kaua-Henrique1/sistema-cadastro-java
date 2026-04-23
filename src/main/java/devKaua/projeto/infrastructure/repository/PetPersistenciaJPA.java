package devKaua.projeto.infrastructure.repository;

import devKaua.projeto.domain.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class PetPersistenciaJPA implements PersistenceUnit {
    @Autowired
    private PetRepository repository;

    @Override
    public boolean salvar(Pet pet, String enderecoPetStr) {
        try {
            repository.save(pet);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar pet: '{}'", pet, e);
            return false;
        }
    }

    @Override
    public boolean atualizar(Pet pet) {
        try {
            repository.save(pet);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar atualizar pet: '{}'", pet, e);
            return false;
        }
    }

    @Override
    public boolean deletar(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar deletar pet", e);
            return false;
        }
    }

    public List<Pet> listaTodosPet() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Erro ao tentar listar todos os Pets:", e);
            return null;
        }
    }

    public List<Pet> listaPetPorCriterio() {
        repository.findById();
        repository.fin
    }
}
