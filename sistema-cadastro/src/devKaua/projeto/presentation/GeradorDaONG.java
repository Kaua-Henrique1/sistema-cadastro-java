package devKaua.projeto.presentation;

import devKaua.projeto.application.InterfaceDeUsario;
import devKaua.projeto.application.InterfaceUsarioCLI;
import devKaua.projeto.application.MenuPrincipal;
import devKaua.projeto.application.PetService;
import devKaua.projeto.domain.PetRepository;
import devKaua.projeto.infrastructure.PetRepositoryTXT;

/**
 * Este é o ponto de entrada da aplicação, responsável por:
 * 1. Instanciar as implementações concretas (adapters).
 * 2. Injetar as dependências nos serviços via construtor (Constructor Injection).
 * <p>
 * Melhorias aplicadas:
 * <p>
 * 1. VARIÁVEIS DECLARADAS COMO INTERFACES (DIP — SOLID / SonarQube):
 *    Antes: declarava variáveis com tipos concretos (InterfaceUsarioCLI, PersistenciaDadosTXT).
 *    Agora: declara com interfaces (InterfaceDeUsario, PetRepository, PetService).
 *    SonarQube: "Declare this local variable with an interface type instead of an implementation type."
 *    Isso torna o código flexível — para trocar a persistência de TXT para banco de dados,
 *    basta alterar apenas esta linha de instanciação.
 * <p>
 * 2. A COMPOSIÇÃO É FEITA AQUI, NÃO NAS CAMADAS INTERNAS:
 *    Em DDD, as camadas internas (domínio e aplicação) nunca conhecem as implementações concretas.
 *    Apenas o ponto de entrada (ou um container de DI) faz a "costura" entre interfaces e implementações.
 */
public class GeradorDaONG {

    public static void main(String[] args) {
        InterfaceDeUsario ui = new InterfaceUsarioCLI();
        PetRepository repository = new PetRepositoryTXT("petsCadastrados");
        PetService service = new PetService(ui, repository);
        MenuPrincipal menu = new MenuPrincipal(ui, service);

        repository.carregarDados();
        menu.run();
    }

}