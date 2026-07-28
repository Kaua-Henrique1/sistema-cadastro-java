package devKaua.projeto.infrastructure;

import devKaua.projeto.domain.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository {
    void carregarDados();
    void salvar(Pessoa pessoa);
    Optional<Pessoa> buscarPorId(Long id);
    List<Pessoa> listarTodos();
    void atualizar(Pessoa pessoaAntiga, Pessoa pessoaNova);
    void deletar(Pessoa pessoa);
}