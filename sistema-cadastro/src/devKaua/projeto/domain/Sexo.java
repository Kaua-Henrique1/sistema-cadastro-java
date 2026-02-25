package devKaua.projeto.domain;

/**
 * Enums em DDD são tratados como Value Objects — são imutáveis e identificados por valor.
 * <p>
 * Melhorias aplicadas:
 * adicionado método estático fromValor() para busca segura por valor numérico.
 *    Antes, o código usava TipoAnimal.values()[index - 1] diretamente, o que é
 *    frágil e pode causar ArrayIndexOutOfBoundsException (alerta SonarQube).
 *    O método fromValor() encapsula essa lógica e lança exceção com mensagem clara.
 */
public enum Sexo {
    MACHO(1, "Macho"),
    FEMEA(2, "Fêmea");

    private final int valor;
    private final String tipo;

    Sexo(int valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public int valor() {
        return valor;
    }

    public String tipo() {
        return tipo;
    }

    /**
     * Busca segura por valor numérico — evita acesso direto por índice do array
     */
    public static Sexo fromValor(int valor) {
        for (Sexo s : values()) {
            if (s.valor == valor) {
                return s;
            }
        }
        throw new IllegalArgumentException("Sexo inválido: " + valor);
    }
}
