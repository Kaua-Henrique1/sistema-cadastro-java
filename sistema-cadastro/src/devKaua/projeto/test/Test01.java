package devKaua.projeto.test;

import devKaua.projeto.service.AlteracoesPet;

public class Test01 {
    public static void main(String[] args) {
        AlteracoesPet alteracoesPet = new AlteracoesPet();
        alteracoesPet.cadastroPetLista();
        alteracoesPet.listarPetPorCriterio();
    } // Estudar o basico de jnuit
}
// Mavem
// Conectar a aplicacao com Banco de Dados (postgres)
// Conectar com a interface grafica.