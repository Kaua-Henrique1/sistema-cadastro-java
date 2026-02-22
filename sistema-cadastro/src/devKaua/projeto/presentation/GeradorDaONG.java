package devKaua.projeto.presentation;

import devKaua.projeto.application.InterfaceUsarioCLI;
import devKaua.projeto.application.PersistenciaDadosTXT;
import devKaua.projeto.application.PetServiceClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;;

public class GeradorDaONG {
    public static void main(String[] args) {
        var ui = new InterfaceUsarioCLI();
        var repo = new PetServiceClass();
        var a = new PersistenciaDadosTXT();

        File formulario = new File("/home/jaua/IdeaProjects/sistema-cadastro--java/sistema-cadastro/formulario/formulario.txt");
        boolean sairTrue = true;
        PetServiceClass sistema = new PetServiceClass();
        while (sairTrue) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1: Cadastrar um novo pet");
            System.out.println("2: Listar pets por algum critério");
            System.out.println("3: Alterar os dados do pet cadastrado");
            System.out.println("4: Deletar um pet cadastrado");
            System.out.println("5: Lista todos os pets cadastrados");
            System.out.println("6: Sair");
            System.out.println();

            try {
                System.out.println("Escolha sua opção: (Digite apenas de 1 a 6)");
                int opcao = scanner.nextInt();

                if (opcao < 1 || opcao > 6) {
                    System.out.println("Informe um número entre 1 a 6.");
                } else {
                    switch (opcao) {
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
                            try {
                                sistema.cadastrar();
                            } catch (RuntimeException e) {
                                System.out.println("Erro. Argumento Invalido.");
                                System.out.println();
                            }
                            break;
                        case 2:
                            try {
                                sistema.listarPetsPorCriterio();
                            } catch (RuntimeException e) {
                                System.out.println("Erro. Argumento Invalido.");
                                scanner.nextLine();
                                System.out.println();
                            }
                            break;
                        case 3:
                            try {
                                sistema.alterar();
                            } catch (RuntimeException e) {
                                System.out.println("Erro. Argumento Invalido.");
                                scanner.nextLine();
                                System.out.println();
                            }
                            break;
                        case 4:
                            try {
                                sistema.remover();
                            } catch (RuntimeException e) {
                                System.out.println("Erro. Argumento Invalido.");
                                scanner.nextLine();
                                System.out.println();
                            }
                            break;
                        case 5:
                            sistema.listarPetsCompleta();
                            break;
                        case 6:
                            sairTrue = false;
                            break;
                    }

                }
            } catch (RuntimeException e) {
                System.out.println("Error Texto: Digite Apenas número.");
            }

        }
    }
}