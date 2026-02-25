package devKaua.projeto.application;

/**
 * Melhorias aplicadas:
 * <p>
 * 1. DEPENDE DE INTERFACES, NÃO DE IMPLEMENTAÇÕES (DIP — SOLID / SonarQube):
 *    Antes: o construtor recebia InterfaceUsarioCLI e PetServiceClass (classes concretas).
 *    Agora: recebe InterfaceDeUsario e PetService (interfaces).
 *    Isso desacopla o menu de qualquer implementação específica de UI ou serviço.
 * <p>
 * 2. Método leituraFormulario() adicionado à interface InterfaceDeUsario:
 *    Antes: o MenuPrincipal chamava getUi().leituraFormulario() que só existia na
 *    classe concreta InterfaceUsarioCLI, forçando a dependência da implementação.
 *    Agora esse método faz parte do contrato da interface.
 */
public class MenuPrincipal {

    private final InterfaceDeUsario ui;
    private final PetService service;

    public MenuPrincipal(InterfaceDeUsario ui, PetService service) {
        this.ui = ui;
        this.service = service;
    }

    public void run() {
        boolean sairTrue = true;
        while (sairTrue) {
            ui().printMenuPrincipal();
            int opcao = ui().selecionarOpcao();
            switch (opcao) {
                case 1:
                    ui().leituraFormulario();
                    try {
                        service().cadastrar();
                    } catch (RuntimeException e) {
                        ui().errorExibir(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        service().listarPetsPorCriterio();
                    } catch (RuntimeException e) {
                        ui().errorExibir(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        service().alterar();
                    } catch (RuntimeException e) {
                        ui().errorExibir(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        service().remover();
                    } catch (RuntimeException e) {
                        ui().errorExibir(e.getMessage());
                    }
                    break;
                case 5:
                    service().listarPetsCompleta();
                    break;
                case 6:
                    sairTrue = false;
                    break;
            }
        }
    }

    private InterfaceDeUsario ui() {
        return ui;
    }

    private PetService service() {
        return service;
    }
}
