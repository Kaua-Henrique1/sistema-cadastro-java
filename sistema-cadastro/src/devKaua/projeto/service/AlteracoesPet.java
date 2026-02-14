package devKaua.projeto.service;

import devKaua.projeto.domain.Endereco;
import devKaua.projeto.domain.Pet;
import devKaua.projeto.domain.Sexo;
import devKaua.projeto.domain.TipoAnimal;

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
        String nomePet = this.pet.verificacaoNomeRegex();
        String racaPet = this.pet.verificacaoRacaRegex();
        TipoAnimal tipoPet = this.pet.verificacaoTipoRegex();
        Sexo sexoPet = this.pet.verificacaoSexoRegex();

        Endereco enderecoPet = this.pet.verificacaoEnderecoRegex();
        String enderecoPetStr = enderecoPet.toFormatado();

        String idadePet = this.pet.verificacaoIdadeRegex();
        String pesoPet = this.pet.vereficacaoPesoRegex();

        DateTimeFormatter formatada = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatadaMin = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(formatada);
        String dataFormatadaMin = agora.format(formatadaMin);
        String nomePetFile = nomePet.toUpperCase().trim().replace(" ", "");

        String nomeFile = dataFormatada + "T" + dataFormatadaMin + "-" + nomePetFile;
        File fileDir = new File("petsCadastrados");

        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File filePet = new File(fileDir, nomeFile + ".txt");

        fileDir.mkdir();
        try (FileWriter fw = new FileWriter(filePet)) {
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

            Pet novoPet = new Pet(nomePet, idadePet, sexoPet, tipoPet, racaPet, pesoPet, enderecoPet);
            this.listaPet.add(novoPet);
        } catch (IOException e) {
            System.out.println("Erro ao tentar salvar o arquivo.");
            throw new RuntimeException(e);
        }
    }

    public List<Pet> listarPetPorCriterio() {
        List<Pet> listaAtual = consultaTipoPet();
        if (listaAtual == null) {
            System.out.println("Nenhum animal desse tipo encontrado.");
            return listaAtual;
        }
        int contador = 1;
        for (Pet pet : listaAtual) {
            System.out.println(contador + pet.toString());
            contador++;
        }

        listaAtual = consultaPet(listaAtual);

        contador = 1;
        for (Pet pet : listaAtual) {
            System.out.println(contador + pet.toString());
            contador++;
        }

        int adicionarConsulta;
        do {
            System.out.println("Deseja adicionar mais um critério (1= Sim/2= Não): ");
            adicionarConsulta = this.scanner.nextInt();
            this.scanner.nextLine();
        } while (adicionarConsulta < 1 || adicionarConsulta > 2);

        if (adicionarConsulta != 1) {
            return listaAtual;
        }
        listaAtual = consultaPet(listaAtual);
        contador = 1;
        for (Pet pet : listaAtual) {
            System.out.println(contador + pet.toString());
            contador++;
        }
        return listaAtual;
    }

    public List<Pet> consultaTipoPet() {
        List<Pet> listaFiltrada = new ArrayList<>();

        int respostaTipoAnimal;
        do {
            System.out.println("Digite apenas '1' e '2'.");
            System.out.println("(1 = Consulta Cachorro/ 2 = Consulta por Gato: ");
            respostaTipoAnimal = this.scanner.nextInt();
            this.scanner.nextLine();
        } while (respostaTipoAnimal < 1 || respostaTipoAnimal > 2);


        System.out.println("Pets Cadastrados com base na sua consulta: ");
        for (Pet petOpcoes : this.listaPet) {
            if (respostaTipoAnimal == 1 && petOpcoes.getTipoAnimal() == TipoAnimal.CACHORRO) {
                listaFiltrada.add(petOpcoes);
            } else if (respostaTipoAnimal == 2 && petOpcoes.getTipoAnimal() == TipoAnimal.GATO) {
                listaFiltrada.add(petOpcoes);
            }
        }
        return listaFiltrada;
    }

    public List<Pet> consultaPet(List<Pet> listaRecebida) {
        int consultaDesejada;
        do {
            System.out.println();
            System.out.println("Digite apenas de '1' a '6'.");
            System.out.println("(1 = nome ou sobrenome/ 2 = idade/ 3 = Raça ): ");
            System.out.println("(4 = Peso/ 5 = Sexo/ 6 = Cidade ): ");
            consultaDesejada = this.scanner.nextInt();
            this.scanner.nextLine();
        } while (consultaDesejada < 1 && consultaDesejada > 6);

        System.out.println("Informe dado do Pet: ");
        String consulta = this.scanner.nextLine();

        List<Pet> resposta = new ArrayList<>();
        switch (consultaDesejada) {
            case 1:
                resposta = this.consultaNome(listaRecebida, consulta);
                break;
            case 2:
                resposta = this.consultaIdade(listaRecebida, consulta);
                break;
            case 3:
                resposta = this.consultaRaca(listaRecebida, consulta);
                break;
            case 4:
                resposta = this.consultaPeso(listaRecebida, consulta);
                break;
            case 5:
                resposta = this.consultaSexo(listaRecebida, consulta);
                break;
            case 6:
                resposta = this.consultaEndereco(listaRecebida, consulta);
                break;
            default:
                System.out.println("Opção inválida");
        }
        return resposta;
    }

    public void alterarDadosPet() {
        List<Pet> listaParaAlteraPet = listarPetPorCriterio();
        System.out.println("Informe o número do pet que deseja alterar: ");
        int numeroPet = this.scanner.nextInt();
        this.scanner.nextLine();

        int contador = 0;
        for (Pet pets : listaParaAlteraPet) {
            contador++;
            if (numeroPet == contador) {
                int consultaDesejada;
                do {
                    System.out.println();
                    System.out.println("Digite apenas de '1' a '5'.");
                    System.out.println("(1 = nome ou sobrenome/ 2 = idade/ 3 = Raça ): ");
                    System.out.println("(4 = Peso/ 5 = Endereco ): ");
                    consultaDesejada = this.scanner.nextInt();
                    this.scanner.nextLine();
                } while (consultaDesejada < 1 && consultaDesejada > 5);

                Endereco alterarEnderecoPet = null;
                if (consultaDesejada == 5) {
                    System.out.println("Informe o novo endereço: ");
                    alterarEnderecoPet = this.pet.verificacaoEnderecoRegex();
                }

                switch (consultaDesejada) {
                    case 1:
                        this.alterarNomePet(pets);
                        break;
                    case 2:
                        this.alterarIdadePet(pets);
                        break;
                    case 3:
                        this.alterarRacaPet(pets);
                        break;
                    case 4:
                        this.alterarPesoPet(pets);
                        break;
                    case 5:
                        this.alterarEnderecoPet(pets, alterarEnderecoPet);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        }


    }

    public void deletarPetLista() {
        List<Pet> listaParaDeletarPet = listarPetPorCriterio();
        System.out.println("Informe o número do pet que deseja deletar: ");
        int numeroPet = this.scanner.nextInt();
        this.scanner.nextLine();

        int contador = 0;
        String respostaDeletarPet;
        for (Pet pet : listaParaDeletarPet) {
            contador++;
            if (numeroPet == contador) {
                do {
                    System.out.println("Digite apenas 'SIM ou NÃO'");
                    System.out.println("Tem certeza que deseja deletar '" + pet.getNome() + "' do sistema (SIM ou NÃO)? ");
                    respostaDeletarPet = this.scanner.nextLine();
                } while (!respostaDeletarPet.equalsIgnoreCase("SIM") && !respostaDeletarPet.equalsIgnoreCase("NÃO"));

                if (respostaDeletarPet.equalsIgnoreCase("SIM")) {
                    removePetTxt(pet);
                    this.listaPet.remove(pet);
                    System.out.println("Pet removido com sucesso!");
                } else {
                    return;
                }
            }
        }
    }

    public void listagemPetLista() {
        int contador = 1;
        for (Pet pet : this.listaPet) {
            System.out.println(contador + pet.toString());
            contador++;
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
                        String dadosEndereco = linhaEndereco.substring(4);

                        String[] partesEndereco = dadosEndereco.split(", ");

                        String rua = partesEndereco[0];
                        String numero;
                        String cidade = partesEndereco[2];

                        if (partesEndereco.length > 1) {
                            numero = partesEndereco[1];
                        } else {
                            numero = this.pet.SEM_DADOS;
                        }

                        Endereco enderecoPet = new Endereco(rua, numero, cidade);

                        String linhaIdade = br.readLine();
                        String idadePet = linhaIdade.split(" - ")[1].replace(" anos", "");

                        String linhaPeso = br.readLine();
                        String pesoPet = linhaPeso.split(" - ")[1].replace("kg", "");

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




    private void removePetTxt(Pet pet) {
        File dir = new File("petsCadastrados");

        if (!dir.exists()) {
            return;
        }

        File[] arquivos = dir.listFiles();

        if (arquivos != null) {
            for (File filePet : arquivos) {
                if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                    boolean arquivoEncontrado = false;
                    try (FileReader fr = new FileReader(filePet)) {
                        BufferedReader br = new BufferedReader(fr);

                        String linhaNome = br.readLine();
                        if (linhaNome.contains(pet.getNome())) {
                            arquivoEncontrado = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (arquivoEncontrado) {
                        filePet.delete();
                        break;
                    }
                }
            }
        }
    }

    private void addInformacoesPetTxt(Pet pet, String linhaNova) {
        File dir = new File("petsCadastrados");

        if (!dir.exists()) {
            return;
        }

        File[] arquivos = dir.listFiles();

        if (arquivos != null) {
            for (File filePet : arquivos) {
                if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                    String linhaNome = "";
                    String linhaTipo = "";
                    String linhaSexo = "";
                    String linhaEndereco = "";
                    String linhaIdade = "";
                    String linhaPeso = "";
                    String linhaRaca = "";
                    try (FileReader fr = new FileReader(filePet)) {
                        BufferedReader br = new BufferedReader(fr);

                        linhaNome = br.readLine();
                        if (!linhaNome.contains(pet.getNome())) {
                            continue;
                        }

                        linhaTipo = br.readLine();
                        linhaSexo = br.readLine();
                        linhaEndereco = br.readLine();
                        linhaIdade = br.readLine();
                        linhaPeso = br.readLine();
                        linhaRaca = br.readLine();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    String[] linhasArquivo = {linhaNome, linhaTipo, linhaSexo, linhaEndereco, linhaIdade, linhaPeso, linhaRaca};
                    try (FileWriter fw = new FileWriter(filePet)) {
                        BufferedWriter bw = new BufferedWriter(fw);

                        String linhaNovaCortada = linhaNova.substring(0, 3);

                        for (String linha : linhasArquivo) {
                            String linhaForCortada = linha.substring(0, 3);
                            if (linhaForCortada.equals(linhaNovaCortada)) {
                                bw.write(linhaNova);
                                bw.newLine();
                            } else {
                                bw.write(linha);
                                bw.newLine();
                            }
                        }
                        bw.flush();
                        return;

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }



    public void alterarNomePet(Pet petAlterar) {
        String nome = this.pet.verificacaoNomeRegex();
        String novoNomeTxt = "1 - " + nome;
        addInformacoesPetTxt(petAlterar, novoNomeTxt);
        petAlterar.setNome(nome);
        System.out.println("Nome Alterado: " + petAlterar.toString());

    }

    public void alterarIdadePet(Pet petAlterar) {
        String idade = this.pet.verificacaoIdadeRegex();
        String novoIdadeTxt = "5 - " + idade;
        addInformacoesPetTxt(petAlterar, novoIdadeTxt);
        petAlterar.setIdade(idade);
        System.out.println("Idade Alterada: " + petAlterar.toString());
    }

    public void alterarRacaPet(Pet petAlterar) {
        String raca = this.pet.verificacaoRacaRegex();
        String novoRacaTxt = "7 - " + raca;
        addInformacoesPetTxt(petAlterar, novoRacaTxt);
        petAlterar.setRaca(raca);
        System.out.println("Raça Alterada: " + petAlterar.toString());
    }

    public void alterarPesoPet(Pet petAlterar) {
        String peso = this.pet.vereficacaoPesoRegex();
        String novoPesoTxt = "6 - " + peso;
        addInformacoesPetTxt(petAlterar, novoPesoTxt);
        petAlterar.setPeso(peso);
        System.out.println("Peso Alterado: " + petAlterar.toString());
    }

    public void alterarEnderecoPet(Pet petAlterar, Endereco endereco) {
        String novoEnderecoTxt = "4 - " + endereco;
        addInformacoesPetTxt(petAlterar, novoEnderecoTxt);
        petAlterar.setEndereco(endereco);
        System.out.println("Endereço Alterado: " + petAlterar.toString());
    }

    private List<Pet> consultaNome(List<Pet> listaAtual, String nome) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            String nomeDoPet = pet.getNome().toUpperCase();
            String termoBusca = nome.toUpperCase();

            if (nomeDoPet.contains(termoBusca)) {
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com esse nome.");
        }
        return listaFiltrada;
    }

    private List<Pet> consultaIdade(List<Pet> listaAtual, String idade) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getIdade().equals(idade)) {
                encontrou = true;
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com essa idade.");
        }
        return listaFiltrada;
    }

    private List<Pet> consultaRaca(List<Pet> listaAtual, String raca) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getRaca().equalsIgnoreCase(raca)) {
                encontrou = true;
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com essa raça.");
        }
        return listaFiltrada;
    }

    private List<Pet> consultaPeso(List<Pet> listaAtual, String peso) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getPeso().equals(peso)) {
                encontrou = true;
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com esse peso.");
        }
        return listaFiltrada;
    }

    private List<Pet> consultaSexo(List<Pet> listaAtual, String sexo) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getSexo().toString().equalsIgnoreCase(sexo)) {
                encontrou = true;
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado com esse sexo.");
        }
        return listaFiltrada;
    }

    private List<Pet> consultaEndereco(List<Pet> listaAtual, String cidade) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getEndereco().getCidade().equalsIgnoreCase(cidade)) {
                encontrou = true;
                listaFiltrada.add(pet);
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado nessa cidade.");
        }
        return listaFiltrada;
    }
}