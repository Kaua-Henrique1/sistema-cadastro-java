package devKaua.projeto.application.service;

import devKaua.projeto.application.dtos.PetDto;
import devKaua.projeto.legacy.InterfaceUsarioCLI;
import devKaua.projeto.infrastructure.repository.PetPersistenciaJPA;
import devKaua.projeto.domain.model.Endereco;
import devKaua.projeto.domain.model.Pet;
import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PetServiceClass implements PetService {
    private AlteracoesPet alteracoesPet;
    private PetPersistenciaJPA persistencia;

    public PetServiceClass(PetPersistenciaJPA petRepository) {
        this.persistencia = petRepository;
        this.alteracoesPet = new AlteracoesPet();
    }

    private PetPersistenciaJPA getPersistencia() {
        return persistencia;
    }

    @Override
    public boolean cadastrar(PetDto petDto) {
        try {
            Pet novoPet = new Pet(petDto.name(), petDto.endereco(), petDto.sexo(), petDto.tipoAnimal(), petDto.idade(), petDto.peso(), petDto.raca());
            String enderecoPetStr = novoPet.getEndereco().toFormatado();
            getPersistencia().salvar(novoPet, enderecoPetStr);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar validar Pet: '{}'", petDto, e);
            return false;
        }
    }

    @Override
    public boolean alterar(PetDto petDto) {
        try {
            Pet novoPet = new Pet(petDto.name(), petDto.endereco(), petDto.sexo(), petDto.tipoAnimal(), petDto.idade(), petDto.peso(), petDto.raca());
            String enderecoPetStr = novoPet.getEndereco().toFormatado();
            getPersistencia().salvar(novoPet, enderecoPetStr);
            return true;
        } catch (Exception e) {
            log.error("Erro de validação ao tentar atualizar dado do Pet: '{}'", petDto, e);
            return false;
        }
    }

    @Override
    public boolean remover(Long id) {
        try {
            getPersistencia().deletar(id);
            return true;
        } catch (Exception e) {
            log.error("Erro ao tentar deletar Pet por ID", e);
        }
        return false;
    }

    @Override
    public List<Pet> listarPetsPorCriterio() {
        getPersistencia().listaPetPorCriterio();
    }

    @Override
    public void listarTodosPets(String consutla, ) {
        alteracoesPet.consultaPet()
        getPersistencia().listaPetPorCriterio();
    }
}