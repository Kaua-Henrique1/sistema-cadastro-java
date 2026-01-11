package devKaua.projeto.domain;

public enum Endereco {
    RUA(1, "Rua"),
    NUMERO(2, "Numero"),
    CIDADE(3, "Cidade");

    private String tipo;
    private int valor;

    Endereco(int valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }
}
