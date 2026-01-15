package devKaua.projeto.domain;

public class Endereco {
    private String rua;
    private String numero;
    private String cidade;

    Endereco(String rua, String numero, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return this.rua + ", " + this.numero + " - " + this.cidade;
    }

    public String toFormatado() {
        return this.rua + ", " + this.numero + ", " + this.cidade;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }
}
