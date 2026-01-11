package devKaua.projeto.domain;

import java.util.List;

public class Pet {
    private String nome;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;
    private String[] endereco;
    private double idade;
    private double peso;
    private String raca;
    private AlteracoesPet listaPet;

    public Pet(String nome,double idade, Sexo sexo, TipoAnimal tipoAnimal, String raca, double peso, String[] endereco) {
        this.nome = nome;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
        this.idade = idade;
        this.raca = raca;
        this.endereco = endereco;
        this.peso = peso;
    }

    public Pet consultaNome(String nome) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            if (pet.getNome().equalsIgnoreCase(nome)) {
                return pet;
            }
        }
        return null;
    }

    public Pet consultaIdade(String idade) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            if (pet.getNome().equalsIgnoreCase(idade)) {
                return pet;
            }
        }
        return null;
    }

    public Pet consultaRaca(String raca) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            if (pet.getNome().equalsIgnoreCase(raca)) {
                return pet;
            }
        }
        return null;
    }

    public String[] getEndereco() {
        return endereco;
    }

    public void setEndereco(String[] endereco) {
        this.endereco = endereco;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
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

    public double getIdade() {
        return idade;
    }

    public void setIdade(double idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
