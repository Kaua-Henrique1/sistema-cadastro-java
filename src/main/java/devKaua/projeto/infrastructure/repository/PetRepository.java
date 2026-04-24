package devKaua.projeto.infrastructure.repository;

import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;
import devKaua.projeto.infrastructure.entity.PetEntity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findByNomeContaining(String nome);
    List<PetEntity> findByRaca(String raca);
    List<PetEntity> findBySexo(Sexo sexo);
    List<PetEntity> findByTipoAnimal(TipoAnimal tipoAnimal);
    List<PetEntity> findByDataNascimento(LocalDate dataNascimento);
    List<PetEntity> findByPeso(String peso);
}
