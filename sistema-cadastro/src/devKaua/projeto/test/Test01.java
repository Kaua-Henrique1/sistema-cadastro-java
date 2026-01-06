package devKaua.projeto.test;

import devKaua.projeto.domain.AlteracoesPet;
import devKaua.projeto.domain.Pet;
import devKaua.projeto.domain.Sexo;
import devKaua.projeto.domain.TipoAnimal;

public class Test01 {
    public static void main(String[] args) {
        Pet pingo = new Pet("Pingo","Ligeiro", Sexo.MACHO, TipoAnimal.GATO);
        Pet carlito = new Pet("Carlito","Esperto", Sexo.MACHO, TipoAnimal.CACHORRO);

        AlteracoesPet listaPet = new AlteracoesPet();
        listaPet.cadastroPetLista(pingo);
        listaPet.cadastroPetLista(carlito);

        listaPet.listarPetPorCriterio(pingo.getNome());
    }
}
