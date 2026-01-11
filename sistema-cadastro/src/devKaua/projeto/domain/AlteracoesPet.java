package devKaua.projeto.domain;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlteracoesPet {
    private List<Pet> listaPet;
    private Pet pet;
    public static final String SEM_DADOS = "NÃO INFORMADO";
    Scanner scanner = new Scanner(System.in);

    public AlteracoesPet() {
        this.listaPet = null;
        this.pet = null;
    }

    public void cadastroPetLista() {
        String nomePet = pet.verificacaoNomeRegex();
        String racaPet = pet.verificacaoRacaRegex();
        TipoAnimal tipoPet = pet.verificacaoTipoRegex();
        Sexo sexoPet = pet.verificacaoSexoRegex();
        String[] enderecoPet = pet.verificacaoEnderecoRegex();
        String idadePet = pet.verificacaoIdadeRegex();
        String pesoPet = pet.vereficacaoPesoRegex();

        Pet novoPet = new Pet(nomePet, idadePet, sexoPet, tipoPet, racaPet, pesoPet, enderecoPet);
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
