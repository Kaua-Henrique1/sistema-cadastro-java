package devKaua.projeto.infrastructure.entidy.PetEntidy;

import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_pets")
public class PetEntidy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_pet;

    private String name;
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipoAnimal;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private LocalDate dataNascimento;
    private String peso;
    private String raca;

    public UUID getId_pet() {
        return id_pet;
    }

    public void setId_pet(UUID id_pet) {
        this.id_pet = id_pet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(TipoAnimal tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}