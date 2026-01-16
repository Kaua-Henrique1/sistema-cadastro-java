package devKaua.projeto.domain;

import java.io.*;
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
        carregarDados();
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

    public void listarPetPorCriterio() {
        System.out.println("(1 = Consulta Cachorro/ 2 = Consulta por Gato: ");
        int respostaTipoAnimal = this.scanner.nextInt();
        this.scanner.nextLine();

        System.out.println("Pets Cadastrados com base na sua consulta: ");
        int contadorPet = 1;
        for (Pet petOpcoes : this.listaPet) {

            if (respostaTipoAnimal == 1 && petOpcoes.getTipoAnimal() == TipoAnimal.CACHORRO)  {
                contadorPet += 1;
                System.out.println(contadorPet+petOpcoes.toString());
            } else if (respostaTipoAnimal == 2 && petOpcoes.getTipoAnimal() == TipoAnimal.GATO){
                contadorPet += 1;
                System.out.println(contadorPet+petOpcoes.toString());
            }
        }
        System.out.println("Adiciona Pesquisa mais específica:");
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
                this.consultaNome(consulta);
                break;
            case 2:
                this.consultaIdade(consulta);
                break;
            case 3:
                this.consultaRaca(consulta);
                break;
            case 4:
                this.consultaPeso(consulta);
                break;
            case 5:
                this.consultaSexo(consulta);
                break;
            case 6:
                this.consultaEndereco(consulta);
                break;
            default:
                System.out.println("Opção inválida");
        }

        System.out.println("Deseja adicionar mais um critério (1= Sim/2= Não): ");
        int adicionarConsulta = this.scanner.nextInt();
        this.scanner.nextLine();

        if (adicionarConsulta == 2) {
            return;
        }

        System.out.println();
        System.out.println("(1 = Consulta por nome ou sobrenome/ 2 = Consulta por idade/ 3 = Consulta por Raça ): ");
        System.out.println("(4 = Consulta por Peso/ 5 = Consulta por Sexo/ 6 = Consulta por Cidade ): ");
        int respostaConsulta2 = this.scanner.nextInt();
        this.scanner.nextLine();

        System.out.println("Informe dado do Pet: ");
        String consulta2 = this.scanner.nextLine();


        switch (respostaConsulta2) {
            case 1:
                this.consultaNome(consulta);
                break;
            case 2:
                this.consultaIdade(consulta);
                break;
            case 3:
                this.consultaRaca(consulta);
                break;
            case 4:
                this.consultaPeso(consulta);
                break;
            case 5:
                this.consultaSexo(consulta);
                break;
            case 6:
                this.consultaEndereco(consulta);
                break;
            default:
                System.out.println("Opção inválida");

        }





    }

    private void carregarDados() {
        File dir = new File("petsCadastrados");

        if (!dir.exists()) {
            return;
        }

        File[] arquivos = dir.listFiles();

        if (arquivos != null) {
            for (File filePet : arquivos) {
                if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                    try (FileReader fr = new FileReader(filePet)) {
                        BufferedReader br = new BufferedReader(fr);


                        String linhaNome = br.readLine();
                        String nomePet = linhaNome.split(" - ")[1];


                        String linhaTipo = br.readLine();
                        String tipoString = linhaTipo.split(" - ")[1];
                        TipoAnimal tipoPet = TipoAnimal.valueOf(tipoString);


                        String linhaSexo = br.readLine();
                        String sexoString = linhaSexo.split(" - ")[1];
                        Sexo sexoPet = Sexo.valueOf(sexoString);


                        String linhaEndereco = br.readLine();
                        String dadosEndereco = linhaEndereco.substring(4); // Remove o "4 - "

                        String[] partesEndereco = dadosEndereco.split(", ");

                        String rua = partesEndereco[0];
                        String numero;
                        String cidade;

                        if (partesEndereco.length > 1) {
                            numero = partesEndereco[1];
                        } else {
                            numero = "S/N";
                        }

                        if (partesEndereco.length > 2) {
                            cidade = partesEndereco[2];
                        } else {
                            cidade = "Desconhecida";
                        }

                        Endereco enderecoPet = new Endereco(rua, numero, cidade);


                        String linhaIdade = br.readLine();
                        String idadePet = linhaIdade.split(" - ")[1].replace(" anos", "").trim();


                        String linhaPeso = br.readLine();
                        String pesoPet = linhaPeso.split(" - ")[1].replace("kg", "").trim();


                        String linhaRaca = br.readLine();
                        String racaPet = linhaRaca.split(" - ")[1];


                        Pet novoPet = new Pet(nomePet, idadePet, sexoPet, tipoPet, racaPet, pesoPet, enderecoPet);
                        this.listaPet.add(novoPet);

                    } catch (Exception e) {
                        System.out.println("Erro ao ler arquivo: " + filePet.getName());
                    }
                }
            }
        }
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

    private void consultaNome(String nome) {
        boolean encontrou = false;
        for (Pet pet : this.listaPet) {
            if (pet.getNome().toUpperCase().contains(nome.toUpperCase())) {
                System.out.println(pet.toString());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com esse nome.");
        }
    }

    private void consultaIdade(String idade) {
        for (Pet pet : this.listaPet) {
            if (pet.getIdade().equals(idade)) {
                System.out.println(pet.toString());
            }
        }
    }

    private void consultaRaca(String raca) {
        for (Pet pet : this.listaPet) {
            if (pet.getRaca().equalsIgnoreCase(raca)) {
                System.out.println(pet.toString());
            }
        }
    }

    private void consultaPeso(String peso) {
        for (Pet pet : this.listaPet) {
            if (pet.getPeso().equals(peso)) {
                System.out.println(pet.toString());
            }
        }
    }

    private void consultaSexo(String sexo) {
        for (Pet pet : this.listaPet) {
            if (pet.getSexo().toString().equalsIgnoreCase(sexo)) {
                System.out.println(pet.toString());
            }
        }
    }

    private void consultaEndereco(String cidade) {
        for (Pet pet : this.listaPet) {
            if (pet.getEndereco().getCidade().equalsIgnoreCase(cidade)) {
                System.out.println(pet.toString());
            }
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
