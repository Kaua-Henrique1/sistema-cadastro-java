package devKaua.projeto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlteracoesPet {
    private List<Pet> listaPet;
    private Pet pet;
    Scanner scanner = new Scanner(System.in);

    public AlteracoesPet() {
        this.listaPet = null;
        this.pet = null;
    }
    /*// \d = Todos os digitos
    // \D = Tudo que nao for digito
    // \s = Todos os espacos em branco \f \t \n \r
    // \S = Todos os espacos que NAO ESTA em branco \f \t \n \r
    // \w = a-zA-Z, Digitos, -
    // \W = Tudo que nao for incluso no /w
    // $ = Fim de linha
    // | = Ou

    // []
    // [a-zA-C]
    // ? Zero ou uma
    // * Zero ou mais
    // + Uma ou mais
    // {n, m} De n ate m
    // () Tipo uma condicional ex: (a/b)

    String regex = "0[xX]([0-9a-f-A-F])+(\\s+|$)";
    // Comece do 0 e x ou X.  0 a 9, a ate f, A a F. Adicionando Espaco Branco ou Fim de linha.
    String texto = "12 0x 0X 0xFFaBC 0X10G 0x1";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(texto);

        System.out.println("Texto:  "+texto);
        System.out.println("Indice: 0123456");
        System.out.println("Regex: "+regex);
        System.out.println("Posicoes encontradas");
        while(matcher.find()) {
            System.out.print(matcher.start() + " " + matcher.group() + "\n");
        } */

    public void cadastroPetLista() {
        System.out.print("Nome e Sobrenome: ");
        String nomePet = this.scanner.nextLine();

        String regexNome = "[A-Za-z]+(\\s)+[A-Za-z]+(\\s+|$)";
        Pattern regraNome = Pattern.compile(regexNome);
        Matcher condicionalNome = regraNome.matcher(nomePet);
        if (!condicionalNome.find()) {
            throw new IllegalArgumentException("Digite apenas o nome e sobrenome.");
        }

        System.out.print("Idade do Pet: ");
        String idadePetStr = this.scanner.nextLine();

        System.out.print("Peso do Pet: ");
        String pesoPetStr = this.scanner.nextLine();

        String regexIdadePeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraIdadePeso = Pattern.compile(regexIdadePeso);
        Matcher condicionalIdade = regraIdadePeso.matcher(idadePetStr);
        Matcher condicionalPeso = regraIdadePeso.matcher(pesoPetStr);

        if (!condicionalIdade.find() || !condicionalPeso.find()) {
            throw new IllegalArgumentException("Digite apenas numero e ',' ou '.'.");
        }

        String idadePetStrComPonto = idadePetStr.replace(',','.');
        String PesoPetStrComPonto = pesoPetStr.replace(',','.');

        double idadePet = Double.parseDouble(idadePetStrComPonto);
        double pesoPet = Double.parseDouble(PesoPetStrComPonto);
        if (idadePet > 20) {
            throw new IllegalArgumentException("Digite uma idade máxima de 20 anos.");
        }

        if (pesoPet > 60 || pesoPet < 0.5) {
            throw new IllegalArgumentException("Peso ireal. Digite entre 0.5kl a 60kl");
        }


        System.out.print("Sexo do Pet(1=Macho/2=Fêmea): ");
        int sexoPetStr = this.scanner.nextInt();
        Sexo sexoPet = Sexo.values()[sexoPetStr - 1];

        System.out.print("Tipo do Pet(1=Cachorro/2=Gato): ");
        int tipoPetInt = this.scanner.nextInt();
        TipoAnimal tipoPet = TipoAnimal.values()[tipoPetInt - 1];

        System.out.print("Raça do Pet: ");
        String racaPet = this.scanner.nextLine();
        String regexRaca = "[a-z,A-Z]";
        // Arruamr proxima acao.

        System.out.print("Rua: ");
        String rua = this.scanner.nextLine();
        System.out.print("Número da casa: ");
        String numero = this.scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = this.scanner.nextLine();
        String[] endereco = {rua, numero, cidade};

        Pet novoPet = new Pet(nomePet, idadePet, sexoPet, tipoPet, racaPet, pesoPet, endereco);
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
