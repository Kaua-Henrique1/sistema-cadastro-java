package devKaua.projeto.domain;

public class Pet {
    private String nome;
    private String sobrenome;
    private Sexo sexo;
    private TipoAnimal tipoAnimal;

    public Pet(String nome, String sobrenome, Sexo sexo, TipoAnimal tipoAnimal) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
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
}
