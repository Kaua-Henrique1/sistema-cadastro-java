package devKaua.projeto;

import devKaua.projeto.domain.Pet;

public interface InterfaceDeUsario {
    int selecionarOpcao();
    void printMenuPrincipal();

    String solicitarNome();
    String solicitarRaca();
    int solicitarTipo();
    int solicitarSexo();
    String solicitarIdade();
    String solicitarPeso();
    String[] solicitarEndereco();

    int solicitarConfirmacao(String mensagem);

    int numeroPetList();
    String confirmacaoDeletarPet(Pet pet);
    void mensagemDeletarPet();

    int solicitarOpcaoEdicao();

    void exibirMensagemAlteracaoConcluida(); // "Informe dado do Pet: "

    void exibirMensagemErrorConsulta();

    int solicitarOpcaoFiltro();

    String solicitarTextoBusca();

}
