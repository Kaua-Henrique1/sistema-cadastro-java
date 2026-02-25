package devKaua.projeto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Domain Service responsável por filtrar Pets segundo critérios de busca.
 * <p>
 * Filtrar Pets por critérios é lógica de DOMÍNIO pura (não depende de UI nem de persistência),
 * portanto fica na camada de domínio.
 * <p>
 * Melhorias aplicadas nesta versão:
 * <p>
 * 1. BUSCA POR "CONTAINS" para campos textuais (NOME, RACA, CIDADE):
 *    Antes: usava equalsIgnoreCase — o usuário precisava digitar o valor exato.
 *    Agora: usa contains (case-insensitive) — basta digitar parte do nome/raça/cidade.
 *    Isso torna a busca muito mais prática e intuitiva.
 * <p>
 * 2. BUSCA EXATA para campos numéricos e enumerados (IDADE, PESO, SEXO):
 *    Esses campos têm valores bem definidos (ex: "Macho", "3", "5.2kg"),
 *    então a comparação exata (case-insensitive) faz mais sentido.
 * <p>
 * 3. MÚLTIPLOS CRITÉRIOS ACUMULADOS:
 *    Novo método filtrar(lista, Map) aplica todos os critérios de uma vez.
 *    Cada critério refina a lista progressivamente (AND lógico).
 */
public class PetFiltro {

    /**
     * Filtra a lista de Pets aplicando múltiplos critérios acumulados.
     * Cada critério refina a lista progressivamente (AND lógico entre critérios).
     *
     * @param lista     lista base a ser filtrada
     * @param criterios mapa de critérios acumulados (critério → valor de busca)
     * @return nova lista contendo apenas os Pets que correspondem a TODOS os critérios
     */
    public List<Pet> filtrar(List<Pet> lista, Map<CriterioFiltro, String> criterios) {
        List<Pet> resultado = new ArrayList<>(lista);
        for (Map.Entry<CriterioFiltro, String> entry : criterios.entrySet()) {
            resultado = filtrar(resultado, entry.getKey(), entry.getValue());
        }
        return resultado;
    }

    /**
     * Filtra a lista de Pets por um único critério e texto de busca.
     * <p>
     * Para NOME, RACA e CIDADE usa "contains" (case-insensitive) — não precisa ser exato.
     * Para IDADE, PESO e SEXO usa comparação exata (case-insensitive).
     *
     * @param lista    lista base a ser filtrada
     * @param criterio critério de filtro (enum do domínio)
     * @param busca    texto digitado pelo usuário para comparação
     * @return nova lista contendo apenas os Pets que correspondem ao critério
     */
    public List<Pet> filtrar(List<Pet> lista, CriterioFiltro criterio, String busca) {
        List<Pet> resultado = new ArrayList<>();
        for (Pet pet : lista) {
            String valorDoCampo = extrairCampo(pet, criterio);
            if (corresponde(criterio, valorDoCampo, busca)) {
                resultado.add(pet);
            }
        }
        return resultado;
    }

    /**
     * Verifica se o valor do campo corresponde ao texto de busca.
     * Campos textuais (NOME, RACA, CIDADE) usam contains — mais prático para o usuário.
     * Campos exatos (IDADE, PESO, SEXO) usam comparação exata.
     */
    private boolean corresponde(CriterioFiltro criterio, String valorDoCampo, String busca) {
        return switch (criterio) {
            case NOME, RACA, CIDADE -> valorDoCampo.toLowerCase().contains(busca.toLowerCase());
            case IDADE, PESO, SEXO, TIPO -> valorDoCampo.equalsIgnoreCase(busca);
        };
    }

    /**
     * Extrai o valor do campo do Pet correspondente ao critério.
     * Centraliza o mapeamento critério → campo, eliminando múltiplos métodos duplicados.
     */
    private String extrairCampo(Pet pet, CriterioFiltro criterio) {
        return switch (criterio) {
            case NOME -> pet.getNome();
            case IDADE -> pet.getIdade();
            case RACA -> pet.getRaca();
            case PESO -> pet.getPeso();
            case SEXO -> pet.getSexo().tipo();
            case CIDADE -> pet.getEndereco().getCidade();
            case TIPO -> pet.getTipoAnimal().animal();
        };
    }
}
