package devKaua.projeto.application;

import devKaua.projeto.domain.Endereco;
import devKaua.projeto.domain.Pessoa;
import devKaua.projeto.infrastructure.PessoaRepository;

import java.util.*;
import java.util.stream.Collectors;

public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaFiltro pessoaFiltro;
    private List<Pessoa> listaFiltrada;
    private final Map<CriterioFiltroPessoa, String> criteriosAtivos;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaFiltro = new PessoaFiltro();
        this.listaFiltrada = new ArrayList<>();
        this.criteriosAtivos = new LinkedHashMap<>();
    }

    // --- CADASTRAR / REGISTRAR ---
    public String registrarPessoa(String nome, String cpf, String rua, String numero, String cidade, String telefone, String email) {
        Endereco endereco = new Endereco(rua, numero, cidade);
        Pessoa novaPessoa = Pessoa.criar(nome, cpf, telefone, email, endereco);
        pessoaRepository.salvar(novaPessoa);
        return "Pessoa cadastrada com sucesso! ID: " + novaPessoa.getId();
    }

    // --- GERENCIAMENTO DE FILTROS / CRITÉRIOS ---
    public void limparCriterios() {
        this.criteriosAtivos.clear();
        this.listaFiltrada.clear();
    }

    public Map<String, String> obterCriteriosParaExibicao() {
        Map<String, String> exibicao = new LinkedHashMap<>();
        for (Map.Entry<CriterioFiltroPessoa, String> entry : criteriosAtivos.entrySet()) {
            exibicao.put(entry.getKey().descricao(), entry.getValue());
        }
        return exibicao;
    }

    public void adicionarCriterio(int opcao, String valor) {
        CriterioFiltroPessoa criterio = CriterioFiltroPessoa.fromValor(opcao);
        if (criterio != null && valor != null && !valor.isBlank()) {
            criteriosAtivos.put(criterio, valor);
        }
    }

    public List<String> obterDescricoesCriteriosAtivos() {
        return criteriosAtivos.keySet().stream()
                .map(CriterioFiltroPessoa::descricao)
                .collect(Collectors.toList());
    }

    public void removerCriterioPorIndice(int indiceBaseUm) {
        List<CriterioFiltroPessoa> chaves = new ArrayList<>(criteriosAtivos.keySet());
        if (indiceBaseUm > 0 && indiceBaseUm <= chaves.size()) {
            criteriosAtivos.remove(chaves.get(indiceBaseUm - 1));
        }
    }

    public String executarBuscaComCriteriosAtuais() {
        List<Pessoa> todas = pessoaRepository.listarTodos();
        this.listaFiltrada = pessoaFiltro.filtrar(todas, criteriosAtivos);
        return formatarListaParaTexto(this.listaFiltrada);
    }

    // --- CONSULTAS E LISTAGENS ---
    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.buscarPorId(id);
    }

    public String listarTodasPessoas(PetService petService) {
        List<Pessoa> todas = pessoaRepository.listarTodos();
        this.listaFiltrada = new ArrayList<>(todas);
        return formatarListaParaTexto(this.listaFiltrada);
    }

    public String listarTodosTutores(PetService petService) {
        List<Pessoa> todas = pessoaRepository.listarTodos();
        this.listaFiltrada = todas.stream()
                .filter(p -> p.isTutor(petService))
                .collect(Collectors.toList());
        return formatarListaParaTexto(this.listaFiltrada);
    }

    public String executarBuscaTutoresComCriterios(PetService petService) {
        List<Pessoa> todas = pessoaRepository.listarTodos();
        List<Pessoa> tutores = todas.stream()
                .filter(p -> p.isTutor(petService))
                .collect(Collectors.toList());

        this.listaFiltrada = pessoaFiltro.filtrar(tutores, criteriosAtivos);
        return formatarListaParaTexto(this.listaFiltrada);
    }

    // --- ALTERAÇÕES E REMOÇÕES ---
    public String alterarCampoPessoa(int numeroPessoa, int opcaoCampo, String novoValor) {
        Pessoa pessoa = obterPessoaPorIndiceFiltrado(numeroPessoa);
        if (pessoa == null) {
            return "Pessoa não encontrada na lista atual.";
        }

        switch (opcaoCampo) {
            case 1:
                pessoa.alterarNome(novoValor);
                break;
            case 2:
                pessoa.alterarTelefone(novoValor);
                break;
            case 3:
                pessoa.alterarEmail(novoValor);
                break;
            default:
                return "Opção de alteração inválida.";
        }

        pessoaRepository.atualizar(pessoa, null); // Atualiza no arquivo TXT
        return "Dados da pessoa atualizados com sucesso!";
    }

    public void removerPessoa(int numeroPessoa) {
        Pessoa pessoa = obterPessoaPorIndiceFiltrado(numeroPessoa);
        if (pessoa != null) {
            pessoaRepository.deletar(pessoa);
            this.listaFiltrada.remove(pessoa);
        }
    }

    public Long removerTutorEObterId(int numeroTutor) {
        Pessoa pessoa = obterPessoaPorIndiceFiltrado(numeroTutor);
        if (pessoa != null) {
            Long id = pessoa.getId();
            pessoaRepository.deletar(pessoa);
            this.listaFiltrada.remove(pessoa);
            return id;
        }
        return null;
    }

    public String executarAlteracaoTutor(int numeroTutor, int opcaoCampo, String novoValor, PetService petService) {
        return alterarCampoPessoa(numeroTutor, opcaoCampo, novoValor);
    }

    // --- MÉTODOS AUXILIARES ---
    public String obterNomePessoa(int numeroPessoa) {
        Pessoa p = obterPessoaPorIndiceFiltrado(numeroPessoa);
        return p != null ? p.getNome() : "";
    }

    public String obterNomePessoaPorIndiceFiltrado(int numeroTutor) {
        return obterNomePessoa(numeroTutor);
    }

    public Long obterIdPessoaPorIndiceFiltrado(int numeroTutor) {
        Pessoa p = obterPessoaPorIndiceFiltrado(numeroTutor);
        return p != null ? p.getId() : null;
    }

    private Pessoa obterPessoaPorIndiceFiltrado(int numeroPessoa) {
        int index = numeroPessoa - 1;
        if (index >= 0 && index < listaFiltrada.size()) {
            return listaFiltrada.get(index);
        }
        return null;
    }

    private String formatarListaParaTexto(List<Pessoa> lista) {
        if (lista == null || lista.isEmpty()) {
            return "Nenhuma pessoa encontrada.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            Pessoa p = lista.get(i);
            sb.append(i + 1).append(") ID: ").append(p.getId())
                    .append(" - Nome: ").append(p.getNome())
                    .append(" - CPF: ").append(p.getCpf())
                    .append(" - Tel: ").append(p.getTelefone())
                    .append(" - Email: ").append(p.getEmail())
                    .append(" - Cidade: ").append(p.getEndereco().getCidade())
                    .append("\n");
        }
        return sb.toString();
    }
}