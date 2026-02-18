package devKaua.projeto.service;

import devKaua.projeto.domain.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlteracoesPet {
    private List<Pet> listaPet = new ArrayList<>();
    private final Pet pet;
    private Endereco endereco;
    private PersistenciaDadosTXT persistenciaDadosTXT;
    public static final String SEM_DADOS = "NÃO INFORMADO";
    Scanner scanner = new Scanner(System.in);

    public AlteracoesPet() {
        this.pet = new Pet();
        this.persistenciaDadosTXT.salvar();
    }

    public void cadastroPetLista() {
        String nomePet = verificacaoNomeRegex();
        String racaPet = verificacaoRacaRegex();
        TipoAnimal tipoPet = verificacaoTipoRegex();
        Sexo sexoPet = verificacaoSexoRegex();

        Endereco enderecoPet = verificacaoEnderecoRegex();
        String enderecoPetStr = enderecoPet.toFormatado();

        String idadePet = verificacaoIdadeRegex();
        String pesoPet = vereficacaoPesoRegex();

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

            AtributosPet atributosPet = new AtributosPet(sexoPet, tipoPet, idadePet, pesoPet, racaPet);

            Pet novoPet = new Pet(nomePet, enderecoPet, atributosPet);
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
                resposta = endereco.consultaEndereco(listaRecebida, consulta);
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
                    alterarEnderecoPet = verificacaoEnderecoRegex();
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
                    this.persistenciaDadosTXT.deletar(pet);
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

    public void alterarNomePet(Pet petAlterar) {
        String nome = verificacaoNomeRegex();
        String novoNomeTxt = "1 - " + nome;
        this.persistenciaDadosTXT.atualizar(petAlterar, novoNomeTxt);
        petAlterar.setNome(nome);
        System.out.println("Nome Alterado: " + petAlterar.toString());
    }

    public void alterarIdadePet(Pet petAlterar) {
        String idade = verificacaoIdadeRegex();
        String novoIdadeTxt = "5 - " + idade;
        this.persistenciaDadosTXT.atualizar(petAlterar, novoIdadeTxt);
        petAlterar.setIdade(idade);
        System.out.println("Idade Alterada: " + petAlterar.toString());
    }

    public void alterarRacaPet(Pet petAlterar) {
        String raca = verificacaoRacaRegex();
        String novoRacaTxt = "7 - " + raca;
        this.persistenciaDadosTXT.atualizar(petAlterar, novoRacaTxt);
        petAlterar.setRaca(raca);
        System.out.println("Raça Alterada: " + petAlterar.toString());
    }

    public void alterarPesoPet(Pet petAlterar) {
        String peso = vereficacaoPesoRegex();
        String novoPesoTxt = "6 - " + peso;
        this.persistenciaDadosTXT.atualizar(petAlterar, novoPesoTxt);
        petAlterar.setPeso(peso);
        System.out.println("Peso Alterado: " + petAlterar.toString());
    }

    public void alterarEnderecoPet(Pet petAlterar, Endereco endereco) {
        String novoEnderecoTxt = "4 - " + endereco;
        this.persistenciaDadosTXT.atualizar(petAlterar, novoEnderecoTxt);
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

    private List<Pet> consultaSexo(List<Pet> listaAtual, String sexo)  {
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


    public String verificacaoNomeRegex() {
        Matcher condicionalNome;
        String nomePet;
        do {
            System.out.print("Nome e Sobrenome: ");
            nomePet = this.scanner.nextLine();
            if (nomePet == "") {
                return this.SEM_DADOS;
            }

            String regexNome = "[A-Za-z]+(\\s)+[A-Za-z]+(\\s+|$)";
            Pattern regraNome = Pattern.compile(regexNome);
            condicionalNome = regraNome.matcher(nomePet);
        } while (!condicionalNome.find());
        return nomePet;
    }

    public String verificacaoRacaRegex() {
        String racaPet;
        Matcher condicionalRaca;
        do {
            System.out.print("Raça do Pet: ");
            racaPet = this.scanner.nextLine();
            if (racaPet == "") {
                return this.SEM_DADOS;
            }

            String regexRaca = "[a-z,A-Z]";
            Pattern regraRaca = Pattern.compile(regexRaca);
            condicionalRaca = regraRaca.matcher(racaPet);
        } while (!condicionalRaca.find());
        return racaPet;
    }

    public TipoAnimal verificacaoTipoRegex() {
        int tipoPetInt;
        TipoAnimal tipoPet;
        do {
            System.out.print("Tipo do Pet(1=Cachorro/2=Gato): ");
            tipoPetInt = this.scanner.nextInt();
            this.scanner.nextLine();
            tipoPet = TipoAnimal.values()[tipoPetInt - 1];
        } while (tipoPetInt < 1 || tipoPetInt > 2);
        return tipoPet;
    }

    public Sexo verificacaoSexoRegex() {
        int sexoPetStr;
        Sexo sexoPet;

        do {
            System.out.print("Sexo do Pet(1=Macho/2=Fêmea): ");
            sexoPetStr = this.scanner.nextInt();
            scanner.nextLine();
            sexoPet = Sexo.values()[sexoPetStr - 1];
        } while (sexoPetStr < 1 || sexoPetStr > 2);
        return sexoPet;
    }

    public Endereco verificacaoEnderecoRegex() {
        System.out.println("Endereço pet encontrado: ");
        System.out.print("Rua: ");
        String rua = this.scanner.nextLine();

        System.out.print("Número da casa: ");
        String numero = this.scanner.nextLine();
        if (numero == "") {
            numero = this.SEM_DADOS;
        }

        System.out.print("Cidade: ");
        String cidade = this.scanner.nextLine();

        Endereco endereco = new Endereco(rua, numero, cidade);
        return endereco;
    }

    public String vereficacaoPesoRegex() {
        Matcher condicionalPeso;
        String pesoPet;
        do {
            System.out.print("Peso do Pet: ");
            String pesoPetStr = this.scanner.nextLine();
            if (pesoPetStr == "") {
                return this.SEM_DADOS;
            }


            String regexPeso = "[0-9]+((\\\\.|,)[0-9]+)?";
            Pattern regraPeso = Pattern.compile(regexPeso);
            condicionalPeso = regraPeso.matcher(pesoPetStr);

            pesoPet = pesoPetStr.replace(',', '.');
        } while (Double.parseDouble(pesoPet) > 60 || Double.parseDouble(pesoPet) < 0.5 || !condicionalPeso.find());
        return pesoPet;
    }

    public String verificacaoIdadeRegex() {
        Matcher condicionalIdade;
        String idadePet;
        do {
            System.out.print("Idade do Pet: ");
            String idadePetStr = this.scanner.nextLine();
            if (idadePetStr == "") {
                return this.SEM_DADOS;
            }
            String regexIdadePeso = "[0-9]+((\\\\.|,)[0-9]+)?";
            Pattern regraIdadePeso = Pattern.compile(regexIdadePeso);
            condicionalIdade = regraIdadePeso.matcher(idadePetStr);

            idadePet = idadePetStr.replace(',', '.');
        } while (Double.parseDouble(idadePet) > 20 || !condicionalIdade.find());
        return idadePet;
    }

}