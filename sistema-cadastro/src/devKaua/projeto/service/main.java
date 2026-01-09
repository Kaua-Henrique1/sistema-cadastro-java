package devKaua.projeto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static void main(String[] args) {
        File formulario = new File("/home/jaua/IdeaProjects/sistema-cadastro--java/sistema-cadastro/src/devKaua/projeto/formulario/formulario.txt");
        try (FileReader fileReader = new FileReader(formulario)) {
            BufferedReader br = new BufferedReader(fileReader);
            String linha;

            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean sairTrue = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Cadastrar um novo pet");
        System.out.println("2: Alterar os dados do pet cadastrado");
        System.out.println("3: Deletar um pet cadastrado");
        System.out.println("4: Listar todos os pets cadastrados");
        System.out.println("5: Listar pets por algum critério (idade, nome, raça)");
        System.out.println("6: Sair");

        System.out.println("Escolha sua opção: (Digite apenas de 1 a 6)");
        String opcao = scanner.nextLine();

        String regex = "[1-6\\$]";
        Pattern regras = Pattern.compile(regex);
        Matcher matcher = regras.matcher(opcao);

        System.out.println("Posicoes encontradas");
        while (matcher.end()) {
            System.out.print(matcher.start()+" "+matcher.group()+"\n");
        }
        if (opcao != regex) {
            int opcaoInt = Integer.parseInt(opcao);
        }
        if (opcaoInt < 1 || opcaoInt > 6) {
            System.out.println("Informe um número entre 1 a 6.");
        }
    }
}
