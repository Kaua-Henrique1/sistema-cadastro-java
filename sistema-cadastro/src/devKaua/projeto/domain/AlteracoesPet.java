package devKaua.projeto.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        Endereco enderecoPet = pet.verificacaoEnderecoRegex();
        String enderecoPetStr = enderecoPet.toFormatado();

        String idadePet = pet.verificacaoIdadeRegex();
        String pesoPet = pet.vereficacaoPesoRegex();

        DateTimeFormatter formatada = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatadaMin = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(formatada);
        String dataFormatadaMin = agora.format(formatadaMin);
        String nomePetFile = nomePet.toUpperCase().trim().replace(" ", "");

        String nomeFile = dataFormatada + "T" + dataFormatadaMin + "-" + nomePetFile;
        File fileDir = new File("petsCadastrados");
        File filePet = new File(fileDir, nomeFile + ".txt");

        fileDir.mkdir();
        try (FileWriter fw = new FileWriter(filePet)) {
            filePet.createNewFile();
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("1 - " + nomePet);
            bw.newLine();
            bw.write("2 - " + tipoPet);
            bw.newLine();
            bw.write("3 - " + sexoPet);
            bw.newLine();
            bw.write("4 - " + enderecoPetStr);
            bw.newLine();
            bw.write("5 - " + idadePet + " anos");
            bw.newLine();
            bw.write("6 - " + pesoPet + "kg");
            bw.newLine();
            bw.write("7 - " + racaPet);
            bw.flush();
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

    public void listarPetPorCriterio() {
        System.out.println("(1 = Consulta Cachorro/ 2 = Consulta por Gato: ");
        int respostaTipoAnimal = this.scanner.nextInt();
        this.scanner.nextLine();

        int contadorPet = 0;
        if (respostaTipoAnimal == 1) {
            for (Pet petOpcoes : this.listaPet) {
                if (petOpcoes.getTipoAnimal() != TipoAnimal.CACHORRO) {
                    continue;
                }
                contadorPet += 1;
                Endereco endereco = petOpcoes.getEndereco();
                System.out.println(contadorPet + ". " + petOpcoes.getNome() + " - " + petOpcoes.getTipoAnimal()
                        + " - " + petOpcoes.getSexo() + " - " + endereco.getRua() + ", " + endereco.getNumero()
                        + " - " + endereco.getCidade() + " - " + " - " + petOpcoes.getIdade() + " anos - "
                        + petOpcoes.getPeso() + "kg - " + petOpcoes.getRaca());
            }
        } else {
            for (Pet petOpcoes : this.listaPet) {
                if (petOpcoes.getTipoAnimal() != TipoAnimal.GATO) {
                    continue;
                }
                contadorPet += 1;
                Endereco endereco = petOpcoes.getEndereco();
                System.out.println(contadorPet + ". " + petOpcoes.getNome() + " - " + petOpcoes.getTipoAnimal()
                        + " - " + petOpcoes.getSexo() + " - " + endereco.getRua() + ", " + endereco.getNumero()
                        + " - " + endereco.getCidade() + " - " + " - " + petOpcoes.getIdade() + " anos - "
                        + petOpcoes.getPeso() + "kg - " + petOpcoes.getRaca());
            }
        }
        System.out.println();
        System.out.println("(1 = Consulta por nome ou sobrenome/ 2 = Consulta por idade/ 3 = Consulta por Raça ): ");
        System.out.println("(4 = Consulta por Peso/ 5 = Consulta por Sexo/ 6 = Consulta por Cidade ): ");
        int respostaConsulta1 = this.scanner.nextInt();
        this.scanner.nextLine();

        System.out.println("Informe dado do Pet: ");
        String consulta = this.scanner.nextLine();

        switch (respostaConsulta1) {
            //Realizar essa regra 1:
            // Caso o usuário escolha por exemplo, NOME, os resultados da
            // busca devem trazer PARTES do nome, por exemplo, caso ele pesquise por
            // FLOR, deverá trazer o caso 2 citado anteriormente.

            //Realizar essa regra 2:
            // usuário poderá combinar de 1 a 2 critérios de busca
            case 1:
                this.pet.consultaNome(consulta);
            case 2:
                this.pet.consultaIdade(consulta);
            case 3:
                this.pet.consultaRaca(consulta);
            case 4:
                this.pet.consultaPeso(consulta);
            case 5:
                this.pet.consultaSexo(consulta);
            case 6:
                this.pet.consultaEndereco(consulta);
        }
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
