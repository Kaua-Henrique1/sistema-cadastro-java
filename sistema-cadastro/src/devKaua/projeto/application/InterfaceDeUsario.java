package devKaua.projeto.application;

import devKaua.projeto.domain.CriterioFiltro;
import devKaua.projeto.domain.Pet;
import devKaua.projeto.domain.Sexo;
import devKaua.projeto.domain.TipoAnimal;

import java.util.List;
import java.util.Map;

/**
 * Esta interface define o contrato de comunicação com o usuário.
 * É uma "porta" (port) da arquitetura hexagonal — a camada de aplicação
 * depende desta abstração, e a implementação concreta (CLI, GUI, Web)
 * fica na camada de apresentação/infraestrutura.
 * <p>
 * Melhorias aplicadas nesta versão:
 * <p>
 * 1. BUSCA COM CRITÉRIOS ACUMULADOS:
 *    Novos métodos para gerenciar critérios: exibir critérios aplicados,
 *    solicitar ação (adicionar/remover/buscar/sair), e solicitar remoção.
 * <p>
 * 2. solicitarSexoParaFiltro() retorna Sexo (enum):
 *    Ao filtrar por sexo, a UI exibe as opções do enum diretamente,
 *    eliminando a necessidade de o usuário digitar texto livre.
 * <p>
 * 3. solicitarCriterioFiltro() retorna CriterioFiltro (enum):
 *    A UI é responsável por coletar e validar a entrada do usuário, devolvendo
 *    ao serviço um objeto de domínio já tipado.
 * <p>
 * 4. solicitarTipoAnimalParaConsulta() retorna TipoAnimal ao invés de int.
 * <p>
 * 5. solicitarConfirmacaoAdicionarFiltro() removido — substituído por
 *    solicitarAcaoGerenciamentoCriterios() que oferece um menu completo.
 */
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

    int numeroPetListFiltrada();
    String confirmacaoDeletarPet(Pet pet);
    void mensagemDeletarPet();

    int solicitarOpcaoAlterar();

    void exibirMensagemAlteracaoConcluida();

    void exibirMensagemErrorConsulta();

    void exibirPet(Pet pet);

    void errorExibir(String mensagem);

    /**
     * Solicita ao usuário qual critério de filtro usar e retorna o enum correspondente.
     * A validação de entrada (range, conversão) é responsabilidade da UI.
     */
    CriterioFiltro solicitarCriterioFiltro();

    /**
     * Solicita texto livre para busca (usado para critérios textuais como nome, raça, cidade, idade, peso).
     */
    String solicitarTextoBusca();

    /**
     * Solicita ao usuário qual sexo filtrar, exibindo as opções do enum Sexo diretamente.
     * Elimina a necessidade de digitar texto livre para um campo com valores bem definidos.
     *
     * @return o Sexo escolhido pelo usuário
     */
    Sexo solicitarSexoParaFiltro();

    void exibirListaPets(List<Pet> listaAtual);

    void erroSalvarArquivoPet();

    void erroSalvarObjPet();

    /**
     * Solicita ao usuário qual tipo de animal deseja consultar e retorna o enum correspondente.
     */
    TipoAnimal solicitarTipoAnimalParaConsulta();

    void leituraFormulario();

    /**
     * Exibe a lista de critérios atualmente aplicados e solicita uma ação ao usuário.
     * Ações possíveis:
     *   1 = Adicionar critério
     *   2 = Remover critério
     *   3 = Executar busca
     *   4 = Sair (voltar ao menu principal)
     *
     * @param criterios mapa de critérios acumulados (critério → valor)
     * @return inteiro representando a ação escolhida (1–4)
     */
    int solicitarAcaoGerenciamentoCriterios(Map<CriterioFiltro, String> criterios);

    /**
     * Solicita ao usuário qual critério deseja remover da lista de critérios aplicados.
     *
     * @param criterios mapa de critérios acumulados (para exibir a lista numerada)
     * @return o CriterioFiltro a ser removido
     */
    CriterioFiltro solicitarCriterioParaRemover(Map<CriterioFiltro, String> criterios);

    /**
     * Solicita ao usuário qual tipo de animal deseja usar como filtro (enum).
     * Similar ao solicitarSexoParaFiltro(), mas para TipoAnimal.
     *
     * @return o TipoAnimal escolhido pelo usuário
     */
    TipoAnimal solicitarTipoAnimalParaFiltro();
}
