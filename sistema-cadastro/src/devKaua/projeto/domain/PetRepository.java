package devKaua.projeto.domain;

import java.util.List;

/**
 * A interface do Repository é definida na camada de DOMÍNIO, não na camada de aplicação
 * ou infraestrutura. Isso é um princípio fundamental do DDD:
 * <p>
 * 1. O domínio define O QUE precisa para persistir e recuperar Aggregates.
 * 2. A infraestrutura define COMO isso é feito (arquivos TXT, banco de dados, etc.).
 * 3. Isso segue o Dependency Inversion Principle (SOLID/SonarQube) — camadas superiores
 *    não dependem de camadas inferiores; ambas dependem de abstrações.
 * <p>
 * Antes: PersistenceUnit ficava na camada application e recebia String de endereço,
 * misturando detalhes de infraestrutura. Agora o Repository trabalha apenas com
 * objetos de domínio (Pet), mantendo a camada de domínio pura.
 * <p>
 * Renomeado de PersistenceUnit para PetRepository — a nomenclatura "Repository"
 * é parte da Ubiquitous Language do DDD e expressa claramente a intenção.
 */
public interface PetRepository {

    void carregarDados();

    void salvar(Pet pet);

    boolean atualizar(Pet pet, String linhaNova);

    void deletar(Pet pet);

    List<Pet> listarTodos();
}
