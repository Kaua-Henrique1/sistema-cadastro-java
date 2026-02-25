package devKaua.projeto.domain;

/**
 * Melhorias aplicadas:
 * Adicionado método estático fromValor() para busca segura por valor numérico.
 *    Antes, o código usava TipoAnimal.values()[index - 1] diretamente, o que é
 *    frágil e pode causar ArrayIndexOutOfBoundsException (alerta SonarQube).
 *    O método fromValor() encapsula essa lógica e lança exceção com mensagem clara.
 */
public enum TipoAnimal {
    CACHORRO(1, "Cachorro"),
    GATO(2, "Gato");

    private final int valor;
    private final String animal;

    TipoAnimal(int valor, String animal) {
        this.animal = animal;
        this.valor = valor;
    }

    public int valor() {
        return valor;
    }

    public String animal() {
        return animal;
    }

    /**
     * Busca segura por valor numérico — evita acesso direto por índice do array.
     */
    public static TipoAnimal fromValor(int valor) {
        for (TipoAnimal t : values()) {
            if (t.valor == valor) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de animal inválido: " + valor);
    }
}
