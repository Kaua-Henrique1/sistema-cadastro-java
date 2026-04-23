package devKaua.projeto.domain.model;

public class Endereco {
    private String rua;
    private String numero;
    private String cidade;
    public static final String SEM_DADOS = "NÃO INFORMADO";

    public Endereco(String rua, String numero, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
        if (numero.isEmpty()) {
            this.numero = SEM_DADOS;
        } else {
            this.numero = numero;
        }
    }

    Endereco(){}

    @Override
    public String toString() {
        return getRua() + ", " + getNumero() + " - " + getCidade();
    }

    public String toFormatado() {
        return getRua() + ", " + getNumero() + ", " + getCidade();
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