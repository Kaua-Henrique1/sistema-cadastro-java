package devKaua.projeto.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuPrincipal {

    private InterfaceUsarioCLI interfaceUsarioCLI;
    public void run() {
        interfaceUsarioCLI.printMenuPrincipal();
        int opcao = interfaceUsarioCLI.selecionarOpcao();

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
}
