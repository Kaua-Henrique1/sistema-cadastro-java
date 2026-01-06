package devKaua.projeto.domain;

public enum TipoAnimal {
    GATO(1,"Gato"),
    CACHORRO(2,"Cachorro");

    private int valor;
    private String animal;

    TipoAnimal(int valor, String animal) {
        this.animal = animal;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public String getAnimal() {
        return animal;
    }
}
