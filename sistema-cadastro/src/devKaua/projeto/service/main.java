package devKaua.projeto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        File formulario = new File("/home/jaua/IdeaProjects/sistema-cadastro--java/sistema-cadastro/src/devKaua/projeto/formulario/formulario.txt");
        try (FileReader fileReader = new FileReader(formulario)){
            BufferedReader br = new BufferedReader(fileReader);
            String linha;

            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
