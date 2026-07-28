package devKaua.projeto.application;

import devKaua.projeto.domain.Pessoa;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PessoaFiltro {

    public List<Pessoa> filtrar(List<Pessoa> lista, Map<CriterioFiltroPessoa, String> criterios) {
        if (criterios == null || criterios.isEmpty()) {
            return lista;
        }

        return lista.stream()
                .filter(pessoa -> criterios.entrySet().stream()
                        .allMatch(entry -> corresponde(entry.getKey(), extrairCampo(pessoa, entry.getKey()), entry.getValue())))
                .collect(Collectors.toList());
    }

    public List<Pessoa> filtrar(List<Pessoa> lista, CriterioFiltroPessoa criterio, String busca) {
        if (criterio == null || busca == null || busca.isBlank()) {
            return lista;
        }

        return lista.stream()
                .filter(pessoa -> corresponde(criterio, extrairCampo(pessoa, criterio), busca))
                .collect(Collectors.toList());
    }

    private boolean corresponde(CriterioFiltroPessoa criterio, String valorDoCampo, String busca) {
        if (valorDoCampo == null || busca == null) {
            return false;
        }
        return valorDoCampo.toLowerCase().contains(busca.toLowerCase().trim());
    }

    private String extrairCampo(Pessoa pessoa, CriterioFiltroPessoa criterio) {
        if (pessoa == null || criterio == null) return "";

        switch (criterio) {
            case NOME:
                return pessoa.getNome();
            case CPF:
                return pessoa.getCpf();
            case CIDADE:
                return pessoa.getEndereco() != null ? pessoa.getEndereco().getCidade() : "";
            case TELEFONE:
                return pessoa.getTelefone();
            case EMAIL:
                return pessoa.getEmail();
            default:
                return "";
        }
    }
}