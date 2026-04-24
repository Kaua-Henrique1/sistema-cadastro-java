package devKaua.projeto.infrastructure.repository;

import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;
import devKaua.projeto.domain.model.Endereco;
import devKaua.projeto.domain.model.Pet;
import devKaua.projeto.infrastructure.entity.PetEntity.PetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class PetPersistenciaJPA implements PersistenceUnit {
    @Autowired
    private PetRepository repository;

    @Override
    public boolean salvar(Pet pet, String enderecoPetStr) {
        try {
            PetEntity entity = toEntity(pet);
            repository.save(entity);
            // Set the generated id back to pet
            setIdToPet(pet, entity.getId());
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar pet: '{}'", pet, e);
            return false;
        }
    }

    @Override
    public boolean atualizar(Pet pet) {
        try {
            PetEntity entity = toEntity(pet);
            entity.setId(pet.getId_pet());
            repository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar atualizar pet: '{}'", pet, e);
            return false;
        }
    }

    @Override
    public boolean deletar(Pet pet) {
        try {
            repository.deleteById(pet.getId_pet());
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar deletar pet", e);
            return false;
        }
    }

    public List<Pet> listaTodosPet() {
        try {
            return repository.findAll().stream()
                    .map(this::toPet)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao tentar listar todos os Pets:", e);
            return null;
        }
    }

    public List<Pet> listaPetPorCriterio(String criterio, Object valor) {
        try {
            List<PetEntity> entities;
            switch (criterio.toLowerCase()) {
                case "nome":
                    entities = repository.findByNomeContaining((String) valor);
                    break;
                case "raca":
                    entities = repository.findByRaca((String) valor);
                    break;
                case "sexo":
                    entities = repository.findBySexo((Sexo) valor);
                    break;
                case "tipoanimal":
                    entities = repository.findByTipoAnimal((TipoAnimal) valor);
                    break;
                case "datanascimento":
                    entities = repository.findByDataNascimento((LocalDate) valor);
                    break;
                case "peso":
                    entities = repository.findByPeso((String) valor);
                    break;
                // Add more cases as needed
                default:
                    entities = repository.findAll();
            }
            return entities.stream()
                    .map(this::toPet)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao tentar listar pets por criterio:", e);
            return null;
        }
    }

    private PetEntity toEntity(Pet pet) {
        PetEntity entity = new PetEntity();
        entity.setNome(pet.getNome() != null && !pet.getNome().isEmpty() ? pet.getNome() : Pet.SEM_DADOS);
        entity.setRua(pet.getEndereco().getRua() != null && !pet.getEndereco().getRua().isEmpty() ? pet.getEndereco().getRua() : Pet.SEM_DADOS);
        entity.setNumero(pet.getEndereco().getNumero() != null && !pet.getEndereco().getNumero().isEmpty() ? pet.getEndereco().getNumero() : Pet.SEM_DADOS);
        entity.setCidade(pet.getEndereco().getCidade() != null && !pet.getEndereco().getCidade().isEmpty() ? pet.getEndereco().getCidade() : Pet.SEM_DADOS);
        entity.setSexo(pet.getSexo());
        entity.setTipoAnimal(pet.getTipoAnimal());
        entity.setDataNascimento(pet.getDataNascimento());
        entity.setPeso(pet.getPeso() != null && !pet.getPeso().isEmpty() ? pet.getPeso() : Pet.SEM_DADOS);
        entity.setRaca(pet.getRaca() != null && !pet.getRaca().isEmpty() ? pet.getRaca() : Pet.SEM_DADOS);
        return entity;
    }

    private Pet toPet(PetEntity entity) {
        Endereco endereco = new Endereco(entity.getRua(), entity.getNumero(), entity.getCidade());
        LocalDate dataNascimento = Optional.ofNullable(entity.getDataNascimento())
                .orElseThrow(() -> new IllegalArgumentException("A data de nascimento do pet não pode ser nula!"));;

        Pet pet = new Pet(entity.getNome(), endereco, entity.getSexo(), entity.getTipoAnimal(), dataNascimento, entity.getPeso(), entity.getRaca());
        setIdToPet(pet, entity.getId());
        return pet;
    }

    // Como pegar o ID do banco e colocar no pet? O ID é gerado automaticamente pelo banco, então precisamos usar reflexão para setar o ID gerado de volta no objeto Pet.
    private void setIdToPet(Pet pet, Long id) {
        try {
            Field field = Pet.class.getDeclaredField("id_pet");
            field.setAccessible(true);
            field.set(pet, id);
        } catch (Exception e) {
            log.error("Erro ao setar id no pet", e);
        }
    }
}
