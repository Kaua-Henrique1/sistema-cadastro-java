package devKaua.projeto.domain;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pet {
    private String nome;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;
    private Endereco endereco;
    private String idade;
    private String peso;
    private String raca;
    private AlteracoesPet listaPet;
    private Scanner scanner = new Scanner(System.in);
    public static final String SEM_DADOS = "NÃO INFORMADO";

    public Pet(String nome, String idade, Sexo sexo, TipoAnimal tipoAnimal, String raca, String peso, Endereco endereco) {
        this.nome = nome;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
        this.idade = idade;
        this.raca = raca;
        this.endereco = endereco;
        this.peso = peso;
    }

    public Pet() {

    }

    @Override
    public String toString() {
        String petFormatado = (". " + getNome() + " - " + getTipoAnimal()
                + " - " + getSexo() + " - " + endereco.getRua() + ", " + endereco.getNumero()
                + " - " + endereco.getCidade() + " - " + " - " + getIdade() + " anos - "
                + getPeso() + "kg - " + getRaca());
        return petFormatado;
    }

    public String verificacaoNomeRegex() {
        System.out.print("Nome e Sobrenome: ");
        String nomePet = this.scanner.nextLine();
        if (nomePet == "") {
            return this.SEM_DADOS;
        }

        String regexNome = "[A-Za-z]+(\\s)+[A-Za-z]+(\\s+|$)";
        Pattern regraNome = Pattern.compile(regexNome);
        Matcher condicionalNome = regraNome.matcher(nomePet);
        if (!condicionalNome.find()) {
            throw new IllegalArgumentException("Digite apenas o nome e sobrenome.");
        }
        return nomePet;
    }

    public String verificacaoRacaRegex() {
        System.out.print("Raça do Pet: ");
        String racaPet = this.scanner.nextLine();
        if (racaPet == "") {
            return this.SEM_DADOS;
        }

        String regexRaca = "[a-z,A-Z]";
        Pattern regraRaca = Pattern.compile(regexRaca);
        Matcher condicionalRaca = regraRaca.matcher(racaPet);
        if (!condicionalRaca.find()) {
            throw new IllegalArgumentException("Digite apenas letras.");
        }
        return racaPet;
    }

    public TipoAnimal verificacaoTipoRegex() {
        System.out.print("Tipo do Pet(1=Cachorro/2=Gato): ");
        int tipoPetInt = this.scanner.nextInt();
        TipoAnimal tipoPet = TipoAnimal.values()[tipoPetInt - 1];

        return tipoPet;
    }

    public Sexo verificacaoSexoRegex() {
        System.out.print("Sexo do Pet(1=Macho/2=Fêmea): ");
        int sexoPetStr = this.scanner.nextInt();
        scanner.nextLine();
        Sexo sexoPet = Sexo.values()[sexoPetStr - 1];

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
        System.out.print("Peso do Pet: ");
        String pesoPetStr = this.scanner.nextLine();
        if (pesoPetStr == "") {
            return this.SEM_DADOS;
        }


        String regexPeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraPeso = Pattern.compile(regexPeso);
        Matcher condicionalPeso = regraPeso.matcher(pesoPetStr);

        if (!condicionalPeso.find()) {
            throw new IllegalArgumentException("Digite apenas numero e ',' ou '.'.");
        }
        String pesoPet = pesoPetStr.replace(',', '.');

        if (Double.parseDouble(pesoPet) > 60 || Double.parseDouble(pesoPet) < 0.5) {
            throw new IllegalArgumentException("Peso ireal. Digite entre 0.5kl a 60kl");
        }
        return pesoPet;
    }

    public String verificacaoIdadeRegex() {
        System.out.print("Idade do Pet: ");
        String idadePetStr = this.scanner.nextLine();
        if (idadePetStr == "") {
            return this.SEM_DADOS;
        }

        String regexIdadePeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraIdadePeso = Pattern.compile(regexIdadePeso);
        Matcher condicionalIdade = regraIdadePeso.matcher(idadePetStr);

        if (!condicionalIdade.find()) {
            throw new IllegalArgumentException("Digite apenas numero e ',' ou '.'.");
        }
        String idadePet = idadePetStr.replace(',', '.');

        if (Double.parseDouble(idadePet) > 20) {
            throw new IllegalArgumentException("Digite uma idade máxima de 20 anos.");
        }
        return idadePet;
    }


    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(TipoAnimal tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
