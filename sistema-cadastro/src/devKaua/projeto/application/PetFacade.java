package devKaua.projeto.application;

import java.util.List;
import java.util.Map;

public class PetFacade {
    private final InterfaceDeUsuario ui;
    private final PetService petService;
    private final PessoaService pessoaService;

    public PetFacade(InterfaceDeUsuario ui, PetService petService, PessoaService pessoaService) {
        this.ui = ui;
        this.petService = petService;
        this.pessoaService = pessoaService;
    }

    public void executarAcaoPet(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarPet();
            case 2 -> listarPetsPorCriterio();
            case 3 -> alterarPet();
            case 4 -> removerPet();
            case 5 -> listarPetsCompleta();
        }
    }

    public void executarAcaoPessoa(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarAdotante();
            case 2 -> alterarAdotante();
            case 3 -> removerAdotante();
            case 4 -> listarTodosAdotantesPuros();
            case 5 -> listarTodosTutores();
            case 6 -> buscarTutoresPorCriterio();
            case 7 -> removerTutor();
            case 8 -> desvincularTutorPet();
            case 9 -> alterarTutor();
        }
    }

    public void removerTutor() {
        int numeroTutor = localizarTutorEObterIndice("PASSO 1: LOCALIZAR O TUTOR");
        if (numeroTutor == -1) return;

        String nomeTutor = pessoaService.obterNomePessoaPorIndiceFiltrado(numeroTutor);
        if ("INVALIDO".equals(nomeTutor) || nomeTutor.isBlank()) {
            ui.errorExibir("Número do tutor inválido.");
            return;
        }

        String confirmacao = ui.confirmacaoDeletarTutor(nomeTutor);
        if (confirmacao.equalsIgnoreCase("SIM")) {
            // CORRIGIDO: removerTutorEObterId
            Long idTutorDeletado = pessoaService.removerTutorEObterId(numeroTutor);

            if (idTutorDeletado != null) {
                petService.desvincularPetsDoTutor(idTutorDeletado);
                ui.mensagemDeletarTutorSucesso();
            } else {
                ui.errorExibir("Erro ao processar a deleção do tutor.");
            }
        } else {
            ui.exibirMensagemOperacaoCancelada();
        }
    }

    public void alterarTutor() {
        int numeroTutor = localizarTutorEObterIndice("PASSO 1: LOCALIZAR O TUTOR PARA ALTERAÇÃO");
        if (numeroTutor == -1) return;

        int opcaoCampo = ui.solicitarOpcaoAlterarAdotante();
        String novoValor = "";

        ui.exibirCabecalhoNovoValor();
        switch (opcaoCampo) {
            case 1 -> novoValor = ui.solicitarNomeAdotante();
            case 2 -> novoValor = ui.solicitarTelefoneAdotante();
            case 3 -> novoValor = ui.solicitarEmailAdotante();
            default -> {
                ui.errorExibir("Opção de campo inválida.");
                return;
            }
        }

        // CORRIGIDO: executarAlteracaoTutor
        String resultadoAlteracao = pessoaService.executarAlteracaoTutor(numeroTutor, opcaoCampo, novoValor, petService);

        if (resultadoAlteracao.startsWith("ERRO:")) {
            ui.errorExibir(resultadoAlteracao.substring(5));
        } else {
            ui.exibirSucesso("Dados do tutor atualizados com sucesso!");
            ui.exibirDadosAtualizados(resultadoAlteracao);
        }
    }

    public void desvincularTutorPet() {
        int numeroTutor = localizarTutorEObterIndice("PASSO 1: SELECIONAR O TUTOR");
        if (numeroTutor == -1) return;

        Long idTutor = pessoaService.obterIdPessoaPorIndiceFiltrado(numeroTutor);
        if (idTutor == null) {
            ui.errorExibir("Número do tutor inválido.");
            return;
        }

        int numeroPetDaLista = selecionarPetDoTutorEObterIndice(idTutor, "PASSO 2: SELECIONAR O PET PARA REMOVER");
        if (numeroPetDaLista == -1) return;

        String resultado = petService.executarDesvinculoUnico(idTutor, numeroPetDaLista);

        if ("SUCESSO".equals(resultado)) {
            ui.exibirSucesso("Desvinculação realizada com sucesso!");
        } else {
            ui.errorExibir(resultado);
        }
    }

    private int localizarTutorEObterIndice(String tituloPasso) {
        ui.exibirCabecalhoPasso(tituloPasso);

        if (!gerenciarCriteriosFluxoAdotantes()) return -1;

        String listagem = pessoaService.executarBuscaTutoresComCriterios(petService);
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.exibirMensagemErrorConsulta();
            return -1;
        }
        ui.exibirListaTutores(listagem);

        return ui.numeroAdotanteListFiltrada();
    }

    private int selecionarPetDoTutorEObterIndice(Long idTutor, String tituloPasso) {
        ui.exibirCabecalhoPasso(tituloPasso);

        String listagemPetsDoTutor = petService.listarPetsDoTutor(idTutor);
        if ("VAZIO".equals(listagemPetsDoTutor)) {
            ui.errorExibir("Este tutor não possui pets vinculados na memória.");
            return -1;
        }
        ui.exibirListaPets(listagemPetsDoTutor);

        return ui.numeroPetListFiltrada();
    }

    private void listarTodosAdotantesPuros() {
        // CORRIGIDO: listarTodasPessoas
        String listagem = pessoaService.listarTodasPessoas(petService);
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.errorExibir("Nenhum adotante sem pet cadastrado no sistema.");
        } else {
            ui.exibirListaAdotantes(listagem);
        }
    }

    private void listarTodosTutores() {
        // CORRIGIDO: listarTodosTutores
        String listagem = pessoaService.listarTodosTutores(petService);
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.errorExibir("Nenhum tutor (adotante com pet) registrado no sistema.");
        } else {
            ui.exibirListaTutores(listagem);
        }
    }

    private void buscarTutoresPorCriterio() {
        if (!gerenciarCriteriosFluxoAdotantes()) return;

        String listagem = pessoaService.executarBuscaTutoresComCriterios(petService);
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.exibirMensagemErrorConsulta();
        } else {
            ui.exibirListaTutores(listagem);
        }
    }

    public void cadastrarAdotante() {
        String nome = ui.solicitarNomeAdotante();
        String cpf = ui.solicitarCpfAdotante();
        String rua = ui.solicitarRuaAdotante();
        String numero = ui.solicitarNumeroAdotante();
        String cidade = ui.solicitarCidadeAdotante();
        String telefone = ui.solicitarTelefoneAdotante();
        String email = ui.solicitarEmailAdotante();

        String resposta = pessoaService.registrarPessoa(nome, cpf, rua, numero, cidade, telefone, email);

        if (resposta.startsWith("Pessoa cadastrada com sucesso!")) {
            ui.exibirSucesso("Adotante cadastrado com sucesso!");
        } else {
            ui.errorExibir(resposta);
        }
    }

    public void vincularPetAdotante() {
        ui.exibirCabecalhoPasso("PASSO 1: LOCALIZAR O ADOTANTE DESEJADO");
        if (!gerenciarCriteriosFluxoAdotantes()) {
            ui.exibirMensagemOperacaoCancelada();
            return;
        }

        String listagemAdotantes = pessoaService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagemAdotantes) || listagemAdotantes.contains("Nenhuma pessoa encontrada")) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaAdotantes(listagemAdotantes);
        Long idAdotante = ui.solicitarIdAdotante();

        ui.exibirCabecalhoPasso("PASSO 2: LOCALIZAR O PET DESEJADO");
        if (!gerenciarCriteriosFluxoPets()) {
            ui.exibirMensagemOperacaoCancelada();
            return;
        }

        String listagemPets = petService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagemPets)) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaPets(listagemPets);
        Long idPet = ui.solicitarIdPet();

        String resultado = petService.vincularTutorAoPet(idAdotante, idPet, pessoaService);

        if ("SUCESSO".equals(resultado)) {
            ui.exibirSucesso("Adotante promovido a Tutor e Pet vinculado com sucesso!");
        } else {
            ui.errorExibir(resultado);
        }
    }

    public void alterarAdotante() {
        if (!gerenciarCriteriosFluxoAdotantes()) return;

        String listagem = pessoaService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaAdotantes(listagem);

        int numeroAdotante = ui.numeroAdotanteListFiltrada();
        int opcaoCampo = ui.solicitarOpcaoAlterarAdotante();

        String novoValor = switch (opcaoCampo) {
            case 1 -> ui.solicitarNomeAdotante();
            case 2 -> ui.solicitarTelefoneAdotante();
            case 3 -> ui.solicitarEmailAdotante();
            default -> "";
        };

        String resultado = pessoaService.alterarCampoPessoa(numeroAdotante, opcaoCampo, novoValor);

        if (resultado.contains("sucesso")) {
            ui.exibirMensagemAlteracaoConcluida();
        } else {
            ui.errorExibir(resultado);
        }
    }

    public void removerAdotante() {
        if (!gerenciarCriteriosFluxoAdotantes()) return;

        String listagem = pessoaService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagem) || listagem.contains("Nenhuma pessoa encontrada")) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaAdotantes(listagem);

        int numeroAdotante = ui.numeroAdotanteListFiltrada();
        String nomeAdotante = pessoaService.obterNomePessoa(numeroAdotante);

        if (nomeAdotante.isBlank()) {
            ui.errorExibir("Número do adotante inválido.");
            return;
        }

        String confirmacao = ui.confirmacaoDeletarAdotante(nomeAdotante);

        if (confirmacao.equalsIgnoreCase("SIM")) {
            pessoaService.removerPessoa(numeroAdotante);
            ui.mensagemDeletarAdotante();
        }
    }

    private boolean gerenciarCriteriosFluxoAdotantes() {
        pessoaService.limparCriterios();
        while (true) {
            Map<String, String> dadosExibicao = pessoaService.obterCriteriosParaExibicao();
            int acao = ui.solicitarAcaoGerenciamentoCriterios(dadosExibicao);

            switch (acao) {
                case 1 -> {
                    int opcaoCrit = ui.solicitarCriterioFiltroAdotante();
                    String valor = ui.solicitarTextoBusca();
                    pessoaService.adicionarCriterio(opcaoCrit, valor);
                }
                case 2 -> {
                    List<String> descricoes = pessoaService.obterDescricoesCriteriosAtivos();
                    int indice = ui.solicitarCriterioParaRemover(descricoes);
                    pessoaService.removerCriterioPorIndice(indice);
                }
                case 3 -> { return true; }
                case 4 -> { return false; }
            }
        }
    }

    public void cadastrarPet() {
        int tipo = ui.solicitarTipo();
        int sexo = ui.solicitarSexo();
        String[] endereco = ui.solicitarEndereco();
        String nome = ui.solicitarNome();
        String raca = ui.solicitarRaca();
        String idade = ui.solicitarIdade();
        String peso = ui.solicitarPeso();

        String resposta = petService.cadastrar(tipo, sexo, endereco, nome, raca, idade, peso);

        if (!"SUCESSO".equals(resposta)) {
            ui.erroSalvarObjPet();
        }
    }

    public void listarPetsCompleta() {
        String resultado = petService.listarTodos();
        ui.exibirListaPets(resultado);
    }

    public void alterarPet() {
        if (!gerenciarCriteriosFluxoPets()) return;

        String listagem = petService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagem)) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaPets(listagem);

        int numeroPet = ui.numeroPetListFiltrada();
        int opcaoCampo = ui.solicitarOpcaoAlterar();

        String novoValor = switch (opcaoCampo) {
            case 1 -> ui.solicitarNome();
            case 2 -> ui.solicitarIdade();
            case 3 -> ui.solicitarRaca();
            case 4 -> ui.solicitarPeso();
            default -> "";
        };

        String resultado = petService.alterarCampoPet(numeroPet, opcaoCampo, novoValor);

        if ("SUCESSO".equals(resultado)) {
            ui.exibirMensagemAlteracaoConcluida();
        } else if (resultado.startsWith("ERRO:")) {
            ui.errorExibir(resultado.substring(5));
        }
    }

    public void removerPet() {
        if (!gerenciarCriteriosFluxoPets()) return;

        String listagem = petService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagem)) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaPets(listagem);

        int numeroPet = ui.numeroPetListFiltrada();
        String nomePet = petService.obterNomePet(numeroPet);

        if ("INVALIDO".equals(nomePet)) {
            ui.errorExibir("Número do pet inválido.");
            return;
        }

        String confirmacao = ui.confirmacaoDeletarPet(nomePet);

        if (confirmacao.equalsIgnoreCase("SIM")) {
            petService.removerPet(numeroPet);
            ui.mensagemDeletarPet();
        }
    }

    public void listarPetsPorCriterio() {
        if (!gerenciarCriteriosFluxoPets()) return;

        String listagem = petService.executarBuscaComCriteriosAtuais();
        if ("VAZIO".equals(listagem)) {
            ui.exibirMensagemErrorConsulta();
            return;
        }
        ui.exibirListaPets(listagem);
    }

    private boolean gerenciarCriteriosFluxoPets() {
        petService.limparCriterios();
        while (true) {
            Map<String, String> dadosExibicao = petService.obterCriteriosParaExibicao();
            int acao = ui.solicitarAcaoGerenciamentoCriterios(dadosExibicao);

            switch (acao) {
                case 1 -> {
                    int opcaoCrit = ui.solicitarCriterioFiltro();
                    String valor = switch (opcaoCrit) {
                        case 5 -> String.valueOf(ui.solicitarSexoParaFiltro());
                        case 7 -> String.valueOf(ui.solicitarTipoAnimalParaFiltro());
                        default -> ui.solicitarTextoBusca();
                    };
                    petService.adicionarCriterio(opcaoCrit, valor);
                }
                case 2 -> {
                    List<String> descricoes = petService.obterDescricoesCriteriosAtivos();
                    int indice = ui.solicitarCriterioParaRemover(descricoes);
                    petService.removerCriterioPorIndice(indice);
                }
                case 3 -> { return true; }
                case 4 -> { return false; }
            }
        }
    }
}