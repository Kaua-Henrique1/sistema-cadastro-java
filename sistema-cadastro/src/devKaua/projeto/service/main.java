package devKaua.projeto.service;

import devKaua.projeto.domain.AlteracoesPet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;;

public class main {
    public static void main(String[] args) {
        File formulario = new File("/home/jaua/IdeaProjects/sistema-cadastro--java/sistema-cadastro/src/devKaua/projeto/formulario/formulario.txt");
        boolean sairTrue = true;
        AlteracoesPet sistema = new AlteracoesPet();
        while (sairTrue) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1: Cadastrar um novo pet");
            System.out.println("2: Listar pets por algum critério");
            System.out.println("3: Alterar os dados do pet cadastrado");
            System.out.println("4: Deletar um pet cadastrado");
            System.out.println("5: Lista todos os pets cadastrados");
            System.out.println("6: Sair");
            System.out.println();

            System.out.println("Escolha sua opção: (Digite apenas de 1 a 6)");
            String opcao = scanner.nextLine();
            try {
                int opcaoInt = Integer.parseInt(opcao);
                if (opcaoInt < 1 || opcaoInt > 6) {
                    System.out.println("Informe um número entre 1 a 6.");
                } else {
                    switch (opcaoInt) {
                        case 1:
                            try (FileReader fileReader = new FileReader(formulario)) {
                                BufferedReader br = new BufferedReader(fileReader);
                                String linha;
                                while ((linha = br.readLine()) != null) {
                                    System.out.println(linha);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            sistema.cadastroPetLista();
                            break;
                        case 2:
                            sistema.listarPetPorCriterio();
                            break;
                        case 3:
                            sistema.alterarDadosPet();
                            break;
                        case 4:
                            sistema.deletarPetLista();
                            break;
                        case 5:
                            sistema.listagemPetLista();
                            break;
                        case 6:
                            sairTrue = false;
                            break;
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("Error Texto: Digite Apenas numero.");
            }

        }
    }
}