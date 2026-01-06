package devKaua.projeto.domain;

import java.util.ArrayList;
import java.util.List;

public class AlteracoesPet {
    private List<Pet> listaPet = new ArrayList<>();
    private Pet pet;

    public AlteracoesPet() {
        this.listaPet = null;
        this.pet = null;
    }

    public void cadastroPetLista(Pet pet) {
        this.listaPet.add(pet);
    }
    public void deletarPetLista(Pet pet) {
        this.listaPet.remove(pet);
    }


    public void listagemPetLista() {
        for (Pet pet : this.listaPet) {
            System.out.println(pet);
        }
    }
    public void listarPetPorCriterio(Pet pet) {
        boolean encontrado = false;
        for (Pet p : this.listaPet) {
            if (pet == p) {
                encontrado = true;
                break;
            }
        }
        if (encontrado == false) {
            System.out.println("Pet não encontrado na lista.");
            return;
        }


    }

    public void alterarNomePet(String nome) {
        pet.setNome(nome);
    }
    public void alterarSobrenomePet(String sobronome) {
        pet.setSobrenome(sobronome);
    }
    public void alterarSexoPet(Sexo sexo) {
        pet.setSexo(sexo);
    }
    public void alterarTipoAnimalPet(TipoAnimal tipoAnimal) {
        pet.setTipoAnimal(tipoAnimal);
    }


    public List<Pet> getListaPet() {
        return listaPet;
    }
    public void setListaPet(List<Pet> pet) {
        this.listaPet = pet;
    }

    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
