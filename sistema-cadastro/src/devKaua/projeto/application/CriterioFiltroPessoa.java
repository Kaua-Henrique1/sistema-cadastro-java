package devKaua.projeto.application;

public enum CriterioFiltroPessoa {
    NOME(1, "Nome"),
    CPF(2, "CPF"),
    CIDADE(3, "Cidade"),
    TELEFONE(4, "Telefone"),
    EMAIL(5, "E-mail");

    private final int valor;
    private final String descricao;

    CriterioFiltroPessoa(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int valor() {
        return valor;
    }

    public String descricao() {
        return descricao;
    }

    public static CriterioFiltroPessoa fromValor(int valor) {
        for (CriterioFiltroPessoa criterio : values()) {
            if (criterio.valor == valor) {
                return criterio;
            }
        }
        return null;
    }
}