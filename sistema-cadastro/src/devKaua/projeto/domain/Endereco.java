package devKaua.projeto.domain;

/**
 * Endereco é um Value Object porque:
 * 1. É definido apenas pelos seus atributos (rua, numero, cidade), sem identidade própria.
 * 2. Dois endereços com os mesmos valores são considerados iguais.
 * 3. É imutável — uma vez criado, não pode ser alterado (campos final, sem setters).
 * <p>
 * Melhorias aplicadas:
 * - Adicionado validação no construtor (self-validation pattern do DDD).
 *   O próprio Value Object garante que nunca existirá em estado inválido.
 * - Implementado equals() e hashCode() baseados nos atributos (identidade por valor).
 *   Isso é obrigatório para Value Objects segundo Vernon e também é uma boa prática SonarQube.
 * - Constante SEM_DADOS movida para cá e mantida como fallback para numero vazio,
 *   pois é responsabilidade do próprio Value Object definir seus valores padrão.
 */
public class Endereco {

    private static final String SEM_DADOS = "NÃO INFORMADO";

    private final String rua;
    private final String numero;
    private final String cidade;

    public Endereco(String rua, String numero, String cidade) {
        if (rua == null || rua.isBlank()) {
            throw new IllegalArgumentException("Rua é obrigatória.");
        }
        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Cidade é obrigatória.");
        }
        this.rua = rua;
        this.cidade = cidade;
        this.numero = (numero == null || numero.isEmpty()) ? SEM_DADOS : numero;
    }

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

    /**
     * equals/hashCode baseados nos atributos — padrão de Value Object (Vernon, Cap. 6).
     * SonarQube também recomenda sobrescrever ambos quando um é sobrescrito.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return rua.equals(endereco.rua)
                && numero.equals(endereco.numero)
                && cidade.equals(endereco.cidade);
    }

    @Override
    public int hashCode() {
        int result = rua.hashCode();
        result = 31 * result + numero.hashCode();
        result = 31 * result + cidade.hashCode();
        return result;
    }
}
