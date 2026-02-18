package devKaua.projeto.domain;

import java.util.concurrent.atomic.AtomicLong;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pet {
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private final Long ID;

    private String nome;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;
    private Endereco endereco;
    private String idade;
    private String peso;
    private String raca;
    private AtributosPet atributosPet;
    private Scanner scanner = new Scanner(System.in);
    public static final String SEM_DADOS = "NÃO INFORMADO";

    public Pet(String nome, Endereco endereco, AtributosPet atributosPet) {
        this.ID = idGenerator.getAndIncrement();
        this.nome = nome;
        this.endereco = endereco;
        this.atributosPet = atributosPet;
    }

    public Pet() {
        this.ID = idGenerator.getAndIncrement();
    }

    @Override
    public String toString() {
        String petFormatado = (". " + getNome() + " - " + getTipoAnimal()
                + " - " + getSexo() + " - " + endereco.toString() + " - " + " - " + getIdade() + " anos - "
                + getPeso() + "kg - " + getRaca());
        return petFormatado;
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
