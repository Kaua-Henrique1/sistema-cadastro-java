package devKaua.projeto.domain;

/**
 * Enum que representa os critérios disponíveis para filtragem de Pets.
 * <p>
 * Substitui os "magic numbers" (1–6) que antes eram usados em PetFiltro e na UI.
 * Em DDD, conceitos do domínio devem ser modelados explicitamente.
 * Um critério de filtro é um conceito do domínio — expressá-lo como enum
 * torna o código mais legível, seguro (erros de compilação vs. runtime) e
 * alinhado à Ubiquitous Language.
 */
public enum CriterioFiltro {
    NOME(1, "Nome ou Sobrenome"),
    IDADE(2, "Idade"),
    RACA(3, "Raça"),
    PESO(4, "Peso"),
    SEXO(5, "Sexo"),
    CIDADE(6, "Cidade"),
    TIPO(7, "Tipo do animal");

    private final int valor;
    private final String descricao;

    CriterioFiltro(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int valor() {
        return valor;
    }

    public String descricao() {
        return descricao;
    }

    /**
     * Busca segura por valor numérico — mesmo padrão de TipoAnimal e Sexo.
     */
    public static CriterioFiltro fromValor(int valor) {
        for (CriterioFiltro c : values()) {
            if (c.valor == valor) {
                return c;
            }
        }
        throw new IllegalArgumentException("Critério de filtro inválido: " + valor);
    }
}
