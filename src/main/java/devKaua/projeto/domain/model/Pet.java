package devKaua.projeto.domain.model;

import devKaua.projeto.domain.enums.Sexo;
import devKaua.projeto.domain.enums.TipoAnimal;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pet {
    private Long id_pet;

    private String nome;
    private Endereco endereco;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;

    private LocalDate dataNascimento;
    private String peso;
    private String raca;
    public static final String SEM_DADOS = "NÃO INFORMADO";

    public Pet(String nome, Endereco endereco, Sexo sexo, TipoAnimal tipoAnimal, LocalDate idade, String peso, String raca) {
        setNome(nome);
        setIdade(idade);
        setPeso(peso);
        setRaca(raca);
        this.endereco = endereco;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
    }

    public Pet() {
    }

    @Override
    public String toString() {
        return (". " + getId_pet() + " - " + getNome() + " - " + getEndereco().toString() + " - " + getTipoAnimal() + " - "
                + getSexo() + " - " + getIdade() + " anos - " + getPeso() + "kg - " + getRaca()
        );
    }

    public Sexo getSexo() {
        return sexo;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public String getIdade() {
        if (this.dataNascimento == null) return SEM_DADOS;
        int years = Period.between(this.dataNascimento, LocalDate.now()).getYears();
        return String.valueOf(years);
    }

    public String getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setNome(String nome) {
        if (nome.isEmpty()) {
            this.nome = SEM_DADOS;
            return;
        }

        String regexNome = "[A-Za-z]+(\\s)+[A-Za-z]+(\\s+|$)";
        Pattern regraNome = Pattern.compile(regexNome);
        Matcher condicionalNome = regraNome.matcher(nome);
        if (!condicionalNome.find()) {
            throw new IllegalArgumentException("Nome inválido! Use apenas letras e espaço.");
        }
        this.nome = nome;
    }

    public void setIdade(LocalDate idade) {
        if (idade == null) {
            return;
        }
        int years = Period.between(idade, LocalDate.now()).getYears();
        if (years <= 0 || years >= 60) {
            throw new IllegalArgumentException("Idade inválida! Digite uma data que fique entre 0.1 Anos até 60 anos.");
        }
        this.dataNascimento = idade;
    }

    public void setPeso(String peso) {
        if (peso.isEmpty()) {
            this.peso = SEM_DADOS;
            return;
        }
        String pesoPet = peso.replace(',', '.');
        String regexPeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraPeso = Pattern.compile(regexPeso);
        Matcher condicionalPeso = regraPeso.matcher(pesoPet);
        if (Double.parseDouble(pesoPet) > 60 || Double.parseDouble(pesoPet) < 0.5 || !condicionalPeso.find()) {
            throw new IllegalArgumentException("Peso inválido! Escreva apenas números entre 0.5kl até 60kl.");
        }
        this.peso = peso;
    }

    public void setRaca(String raca) {
        if (raca.isEmpty()) {
            this.raca = SEM_DADOS;
            return;
        }
        String regexRaca = "[a-z,A-Z]+";
        Pattern regraRaca = Pattern.compile(regexRaca);
        Matcher condicionalRaca = regraRaca.matcher(raca);
        if (!condicionalRaca.find()) {
            throw new IllegalArgumentException("Raça inválida! Escreva apenas letras.");
        }
        this.raca = raca;
    }

    public Long getId_pet() {
        return id_pet;
    }
}