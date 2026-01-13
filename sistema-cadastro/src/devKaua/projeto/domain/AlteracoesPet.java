package devKaua.projeto.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlteracoesPet {
    private List<Pet> listaPet = new ArrayList<>();
    private Pet pet;
    Scanner scanner = new Scanner(System.in);

    public AlteracoesPet() {
        this.pet = new Pet();
    }

    public void cadastroPetLista() {
        String nomePet = pet.verificacaoNomeRegex();
        String racaPet = pet.verificacaoRacaRegex();
        TipoAnimal tipoPet = pet.verificacaoTipoRegex();
        Sexo sexoPet = pet.verificacaoSexoRegex();
        String[] enderecoPet = pet.verificacaoEnderecoRegex();
        String idadePet = pet.verificacaoIdadeRegex();
        String pesoPet = pet.vereficacaoPesoRegex();

        DateTimeFormatter formatada = DateTimeFormatter.ofPattern("yyyyMMDDTHHmm");
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(formatada);
        String nomePetFile = nomePet.toUpperCase().trim();

        File filePet = new File(dataFormatada+"-"+nomePetFile);
        try (FileWriter fw = new FileWriter(filePet)){
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("1 - "+nomePet);
            bw.write("2 - "+tipoPet);
            bw.write("3 - "+sexoPet);
            bw.write("4 - "+enderecoPet);
            bw.write("5 - "+idadePet + " anos");
            bw.write("6 - "+pesoPet+"kg");
            bw.write("7 - "+racaPet);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
