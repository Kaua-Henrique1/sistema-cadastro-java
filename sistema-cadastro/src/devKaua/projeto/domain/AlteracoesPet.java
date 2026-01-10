package devKaua.projeto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlteracoesPet {
    private List<Pet> listaPet = new ArrayList<>();
    private Pet pet;
    Scanner scanner = new Scanner(System.in);
    public AlteracoesPet() {
        this.listaPet = null;
        this.pet = null;
    }

    public void cadastroPetLista() {
        System.out.println("Nome do Pet: ");
        String nomePet = this.scanner.nextLine();

        System.out.println("Sobrenome do Pet: ");
        String sobrenomePet = this.scanner.nextLine();

        System.out.println("Sexo do Pet(1=Macho/2=Fêmea): ");
        int sexoPetStr = this.scanner.nextInt();
        Sexo sexoPet = Sexo.values()[sexoPetStr - 1];

        System.out.println("Tipo do Pet(1=Cachorro/2=Gato): ");
        int tipoPetInt = this.scanner.nextInt();
        TipoAnimal tipoPet = TipoAnimal.values()[tipoPetInt - 1];

        System.out.println("Idade do Pet: ");
        int idadePet = this.scanner.nextInt();

        System.out.println("Raça do Pet: ");
        String racaPet = this.scanner.nextLine();

        Pet novoPet = new Pet(nomePet,sobrenomePet,sexoPet,tipoPet,idadePet,racaPet);
        this.listaPet.add(novoPet);
    }

    public void deletarPetLista() {
        System.out.println("Nome do Pet para deletar: ");
        String nomePet = this.scanner.nextLine();
        for (Pet i : listaPet) {
            if (i.getNome() == nomePet) {
                this.listaPet.remove(i);
            }
        }
    }


    public void listagemPetLista() {
        for (Pet pet : this.listaPet) {
            System.out.println(pet);
        }
    }

    public Pet listarPetPorCriterio() {
        System.out.println("(1 = Consulta por nome/ 2 = Consulta por idade/ 3 = Consulta por Raça ): ");
        int resposta = this.scanner.nextInt();

        System.out.println("Informe dado do Pet: ");
        String consulta = this.scanner.nextLine();

        switch (resposta) {
            case 1:
                Pet valorPetNome = this.pet.consultaNome(consulta);
                return valorPetNome;
            case 2:
                Pet valorPetIdade = this.pet.consultaIdade(consulta);
                return valorPetIdade;
            case 3:
                Pet valorPetRaca = this.pet.consultaRaca(consulta);
                return valorPetRaca;
        }
        return null;
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


    public void sairMenu() {

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
