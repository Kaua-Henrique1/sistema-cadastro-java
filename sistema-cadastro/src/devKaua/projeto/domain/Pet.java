package devKaua.projeto.domain;

import java.util.List;

public class Pet {
    private String nome;
    private String sobrenome;
    private int idade;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;
    private String raca;
    private AlteracoesPet listaPet;

    public Pet(String nome, String sobrenome, Sexo sexo, TipoAnimal tipoAnimal, int idade, String raca) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
        this.idade = idade;
        this.raca = raca;
    }

    public Pet consultaNome(String nome) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            if (pet.getNome() == nome) {
                return pet;
            }
        }
        return null;
    }

    public Pet consultaIdade(String idade) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            String strIdade = Integer.toString(pet.getIdade());
            if (strIdade == idade) {
                return pet;
            }
        }
        return null;
    }

    public Pet consultaRaca(String raca) {
        List<Pet> lista = listaPet.getListaPet();
        for (Pet pet : lista) {
            if (pet.getRaca() == raca) {
                return pet;
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
