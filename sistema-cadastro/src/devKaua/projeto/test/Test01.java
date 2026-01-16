package devKaua.projeto.test;

import devKaua.projeto.domain.AlteracoesPet;
import devKaua.projeto.domain.Pet;
import devKaua.projeto.domain.Sexo;
import devKaua.projeto.domain.TipoAnimal;

public class Test01 {
    public static void main(String[] args) {
        AlteracoesPet alteracoesPet = new AlteracoesPet();
        alteracoesPet.cadastroPetLista();
        alteracoesPet.listarPetPorCriterio();
    }
}
