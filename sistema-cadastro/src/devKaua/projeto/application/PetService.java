package devKaua.projeto.application;

import devKaua.projeto.domain.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Application Service que orquestra o fluxo da aplicação: recebe entrada da UI,
 * delega ao domínio e ao repositório, e devolve resultados.
 * <p>
 * Melhorias aplicadas nesta versão (fluxo de filtragem com critérios acumulados):
 * <p>
 * 1. CRITÉRIOS ACUMULADOS antes de executar a busca:
 *    Antes: cada filtro era aplicado imediatamente e o resultado exibido a cada passo.
 *    Agora: o usuário acumula critérios num Map, visualiza o que já aplicou,
 *    pode remover critérios individuais, e só ao confirmar "Executar busca"
 *    os critérios são aplicados de uma vez. Após a busca, os critérios são limpos
 *    e o fluxo retorna ao menu principal.
 * <p>
 * 2. BUSCA POR SEXO e TIPO exibem enums diretamente:
 *    Quando o critério é SEXO ou TIPO, a UI exibe as opções dos enums (Macho/Fêmea,
 *    Cachorro/Gato) ao invés de pedir texto livre — elimina erros de digitação.
 * <p>
 * 3. BUSCA POR NOME com "contains":
 *    Delegado ao PetFiltro que agora usa contains (case-insensitive)
 *    para campos textuais — não precisa ser exato.
 * <p>
 * 4. MENU DE GERENCIAMENTO DE CRITÉRIOS:
 *    O usuário vê os critérios aplicados e escolhe: adicionar, remover, buscar ou sair.
 *    Isso dá controle total sobre a busca antes de executá-la.
 */
public class PetService {

    private final PetFiltro petFiltro;
    private final InterfaceDeUsario ui;
    private final PetRepository repository;

    public PetService(InterfaceDeUsario ui, PetRepository repository) {
        this.ui = ui;
        this.repository = repository;
        this.petFiltro = new PetFiltro();
    }

    private PetRepository repository() {
        return repository;
    }

    private InterfaceDeUsario ui() {
        return ui;
    }

    public void cadastrar() {
        Pet novoPet = null;

        while (novoPet == null) {
            try {
                int tipoPetInt = ui().solicitarTipo();
                TipoAnimal tipoPet = TipoAnimal.fromValor(tipoPetInt);

                int sexoPetInt = ui().solicitarSexo();
                Sexo sexoPet = Sexo.fromValor(sexoPetInt);

                String[] enderecoPetU = ui().solicitarEndereco();
                Endereco enderecoPet = new Endereco(enderecoPetU[0], enderecoPetU[1], enderecoPetU[2]);

                String nomePet = ui().solicitarNome();
                String racaPet = ui().solicitarRaca();
                String idadePet = ui().solicitarIdade();
                String pesoPet = ui().solicitarPeso();

                novoPet = Pet.criar(nomePet, enderecoPet, sexoPet, tipoPet, idadePet, pesoPet, racaPet);
            } catch (IllegalArgumentException e) {
                ui().erroSalvarObjPet();
            }
        }

        repository().salvar(novoPet);
    }

    public void alterar() {
        List<Pet> listaParaAlteraPet = listarPetsPorCriterio();
        if (listaParaAlteraPet.isEmpty()) {
            return;
        }

        int numeroPet = ui().numeroPetListFiltrada();

        int contador = 0;
        for (Pet pets : listaParaAlteraPet) {
            contador++;
            if (numeroPet == contador) {
                int consultaDesejada;
                do {
                    consultaDesejada = ui().solicitarOpcaoAlterar();
                } while (consultaDesejada < 1 || consultaDesejada > 4);

                switch (consultaDesejada) {
                    case 1:
                        alterarCampoNome(pets);
                        break;
                    case 2:
                        alterarCampoIdade(pets);
                        break;
                    case 3:
                        alterarCampoRaca(pets);
                        break;
                    case 4:
                        alterarCampoPeso(pets);
                        break;
                }
            }
        }
    }

    /**
     * Métodos extraídos para reduzir a complexidade ciclomática do método alterar()
     * (SonarQube: "Refactor this method to reduce its Cognitive Complexity").
     * Cada método cuida de um único campo, seguindo o Single Responsibility Principle.
     */
    private void alterarCampoNome(Pet pet) {
        boolean valido = false;
        while (!valido) {
            try {
                String nomeNovo = ui().solicitarNome();
                pet.alterarNome(nomeNovo);
                String novoNomeTxt = "1 - " + nomeNovo;
                boolean atualizado = repository().atualizar(pet, novoNomeTxt);
                if (atualizado) {
                    ui().exibirPet(pet);
                    ui().exibirMensagemAlteracaoConcluida();
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                ui().errorExibir(e.getMessage());
            }
        }
    }

    private void alterarCampoIdade(Pet pet) {
        boolean valido = false;
        while (!valido) {
            try {
                String idadeNovo = ui().solicitarIdade();
                pet.alterarIdade(idadeNovo);
                String novoIdadeTxt = "5 - " + idadeNovo + " anos";
                boolean atualizado = repository().atualizar(pet, novoIdadeTxt);
                if (atualizado) {
                    ui().exibirPet(pet);
                    ui().exibirMensagemAlteracaoConcluida();
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                ui().errorExibir(e.getMessage());
            }
        }
    }

    private void alterarCampoRaca(Pet pet) {
        boolean valido = false;
        while (!valido) {
            try {
                String racaNovo = ui().solicitarRaca();
                pet.alterarRaca(racaNovo);
                String novoRacaTxt = "7 - " + racaNovo;
                boolean atualizado = repository().atualizar(pet, novoRacaTxt);
                if (atualizado) {
                    ui().exibirPet(pet);
                    ui().exibirMensagemAlteracaoConcluida();
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                ui().errorExibir(e.getMessage());
            }
        }
    }

    private void alterarCampoPeso(Pet pet) {
        boolean valido = false;
        while (!valido) {
            try {
                String pesoNovo = ui().solicitarPeso();
                pet.alterarPeso(pesoNovo);
                String novoPesoTxt = "6 - " + pesoNovo + "kg";
                boolean atualizado = repository().atualizar(pet, novoPesoTxt);
                if (atualizado) {
                    ui().exibirPet(pet);
                    ui().exibirMensagemAlteracaoConcluida();
                }
                valido = true;
            } catch (IllegalArgumentException e) {
                ui().errorExibir(e.getMessage());
            }
        }
    }

    public void remover() {
        List<Pet> listaParaDeletarPet = listarPetsPorCriterio();
        if (listaParaDeletarPet.isEmpty()) {
            return;
        }

        int numeroPet = ui().numeroPetListFiltrada();

        int contador = 0;
        String respostaDeletarPet;
        for (Pet pet : listaParaDeletarPet) {
            contador++;
            if (numeroPet == contador) {
                do {
                    respostaDeletarPet = ui().confirmacaoDeletarPet(pet);
                } while (!respostaDeletarPet.equalsIgnoreCase("SIM") && !respostaDeletarPet.equalsIgnoreCase("NÃO"));

                if (respostaDeletarPet.equalsIgnoreCase("SIM")) {
                    repository().deletar(pet);
                    ui().mensagemDeletarPet();
                } else {
                    return;
                }
            }
        }
    }

    /**
     * Lista pets por critérios de filtragem acumulados.
     * <p>
     * Fluxo:
     * Fluxo:
     * 1. Entra no menu de gerenciamento de critérios onde pode:
     *    - Adicionar critério (nome, idade, raça, peso, sexo, cidade, tipo)
     *    - Remover critério previamente adicionado
     *    - Executar busca (aplica todos os critérios de uma vez)
     *    - Sair (volta ao menu principal sem buscar)
     * 2. Ao executar busca: aplica todos os critérios, exibe resultado,
     *    limpa critérios e retorna a lista filtrada.
     * <p>
     * Por que é melhor:
     * - O usuário tem controle total sobre os critérios antes de executar.
     * - Pode revisar e remover critérios indesejados.
     * - Busca por sexo usa enum (sem digitação de texto livre).
     * - Busca por nome usa "contains" (não precisa ser exato).
     *
     * @return lista filtrada final, ou lista vazia se nenhum pet corresponder
     */
    public List<Pet> listarPetsPorCriterio() {
        List<Pet> base = repository().listarTodos();

        if (base.isEmpty()) {
            ui().exibirMensagemErrorConsulta();
            return base;
        }

        Map<CriterioFiltro, String> criterios = new LinkedHashMap<>();

        while (true) {
            int acao = ui().solicitarAcaoGerenciamentoCriterios(criterios);

            switch (acao) {
                case 1:
                    adicionarCriterio(criterios);
                    break;
                case 2:
                    removerCriterio(criterios);
                    break;
                case 3:
                    return executarBusca(base, criterios);
                case 4:
                    return base;
                default:
                    break;
            }
        }
    }

    /**
     * Solicita critério e valor ao usuário e adiciona ao mapa de critérios.
     * Se o critério for SEXO, exibe as opções do enum diretamente.
     * Se o critério já existir, o valor é substituído (um critério por tipo).
     */
    private void adicionarCriterio(Map<CriterioFiltro, String> criterios) {
        CriterioFiltro criterio = ui().solicitarCriterioFiltro();

        String valor;
        if (criterio == CriterioFiltro.SEXO) {
            Sexo sexo = ui().solicitarSexoParaFiltro();
            valor = sexo.tipo();
        } else if (criterio == CriterioFiltro.TIPO) {
            TipoAnimal tipo = ui().solicitarTipoAnimalParaFiltro();
            valor = tipo.animal();
        } else {
            valor = ui().solicitarTextoBusca();
        }

        criterios.put(criterio, valor);
    }

    /**
     * Solicita qual critério remover e o remove do mapa.
     */
    private void removerCriterio(Map<CriterioFiltro, String> criterios) {
        CriterioFiltro criterioParaRemover = ui().solicitarCriterioParaRemover(criterios);
        criterios.remove(criterioParaRemover);
    }

    /**
     * Aplica todos os critérios acumulados sobre a lista filtrada por tipo.
     * Se não houver critérios adicionais, apenas exibe e retorna a lista por tipo (após o usuário solicitar a busca).
     * Se não encontrar resultados, exibe mensagem de erro e retorna a lista por tipo.
     */
    private List<Pet> executarBusca(List<Pet> baseLista, Map<CriterioFiltro, String> criterios) {
        if (criterios.isEmpty()) {
            // Sem critérios adicionais: exibe a lista base ao executar a busca explicitamente.
            ui().exibirListaPets(baseLista);
            return baseLista;
        }

        List<Pet> resultado = petFiltro.filtrar(baseLista, criterios);

        if (resultado.isEmpty()) {
            ui().exibirMensagemErrorConsulta();
            return baseLista;
        }

        ui().exibirListaPets(resultado);
        return resultado;
    }

    // Removido: filtrarPorTipo(TipoAnimal) — o tipo agora é um critério como os demais.

    public void listarPetsCompleta() {
        ui().exibirListaPets(repository().listarTodos());
    }
}
