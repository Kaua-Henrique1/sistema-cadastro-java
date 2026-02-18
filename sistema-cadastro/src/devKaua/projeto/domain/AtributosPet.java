package devKaua.projeto.domain;

public class AtributosPet {
    private Sexo sexo;
    private TipoAnimal tipoAnimal;
    private String idade;
    private String peso;
    private String raca;

    public AtributosPet(Sexo sexo, TipoAnimal tipoAnimal, String idade, String peso, String raca) {
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    @Override
    public String toString() {
        return (" - " + getTipoAnimal() + " - " + getSexo() + " - " + " - "
                + getIdade() + " anos - " + getPeso() + "kg - " + getRaca());
    }

    Sexo getSexo() {
        return sexo;
    }

    TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    String getIdade() {
        return idade;
    }

    String getPeso() {
        return peso;
    }

    String getRaca() {
        return raca;
    }
}
