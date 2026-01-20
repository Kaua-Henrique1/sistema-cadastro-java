package devKaua.projeto.test;

import devKaua.projeto.domain.AlteracoesPet;

public class Test01 {
    public static void main(String[] args) {
        AlteracoesPet alteracoesPet = new AlteracoesPet();
        alteracoesPet.cadastroPetLista();
        alteracoesPet.listarPetPorCriterio();
    }
}
