package devKaua.projeto.service;

import devKaua.projeto.PersistenceUnit;
import devKaua.projeto.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDadosTXT implements PersistenceUnit {
    private List<Pet> listaPet = new ArrayList<>();
    public static final String SEM_DADOS = "NÃO INFORMADO";

    @Override
    public boolean salvar() {
        File dir = new File("petsCadastrados");
        File[] arquivos = dir.listFiles();

        if (arquivos == null) {
            return false;
        }

        for (File filePet : arquivos) {
            if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                try (FileReader fr = new FileReader(filePet)) {
                    BufferedReader br = new BufferedReader(fr);

                    String linhaNome = br.readLine();
                    String nomePet = linhaNome.split(" - ")[1];

                    String linhaTipo = br.readLine();
                    String tipoString = linhaTipo.split(" - ")[1];
                    TipoAnimal tipoPet = TipoAnimal.valueOf(tipoString);

                    String linhaSexo = br.readLine();
                    String sexoString = linhaSexo.split(" - ")[1];
                    Sexo sexoPet = Sexo.valueOf(sexoString);

                    String linhaEndereco = br.readLine();
                    String dadosEndereco = linhaEndereco.substring(4);

                    String[] partesEndereco = dadosEndereco.split(", ");

                    String rua = partesEndereco[0];
                    String numero;
                    String cidade = partesEndereco[2];

                    if (partesEndereco.length > 1) {
                        numero = partesEndereco[1];
                    } else {
                        numero = SEM_DADOS;
                    }

                    Endereco enderecoPet = new Endereco(rua, numero, cidade);

                    String linhaIdade = br.readLine();
                    String idadePet = linhaIdade.split(" - ")[1].replace(" anos", "");

                    String linhaPeso = br.readLine();
                    String pesoPet = linhaPeso.split(" - ")[1].replace("kg", "");

                    String linhaRaca = br.readLine();
                    String racaPet = linhaRaca.split(" - ")[1];

                    AtributosPet atributosPet = new AtributosPet(sexoPet, tipoPet, idadePet, pesoPet, racaPet);
                    Pet novoPet = new Pet(nomePet, enderecoPet, atributosPet);

                    this.listaPet.add(novoPet);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean atualizar(Pet pet, String linhaNova) {
        File dir = new File("petsCadastrados");
        File[] arquivos = dir.listFiles();

        if (arquivos == null) {
            return false;
        }

        for (File filePet : arquivos) {
            if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                String linhaNome = "";
                String linhaTipo = "";
                String linhaSexo = "";
                String linhaEndereco = "";
                String linhaIdade = "";
                String linhaPeso = "";
                String linhaRaca = "";
                try (FileReader fr = new FileReader(filePet)) {
                    BufferedReader br = new BufferedReader(fr);

                    linhaNome = br.readLine();
                    if (!linhaNome.contains(pet.getNome())) {
                        continue;
                    }

                    linhaTipo = br.readLine();
                    linhaSexo = br.readLine();
                    linhaEndereco = br.readLine();
                    linhaIdade = br.readLine();
                    linhaPeso = br.readLine();
                    linhaRaca = br.readLine();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String[] linhasArquivo = {linhaNome, linhaTipo, linhaSexo, linhaEndereco, linhaIdade, linhaPeso, linhaRaca};
                try (FileWriter fw = new FileWriter(filePet)) {
                    BufferedWriter bw = new BufferedWriter(fw);

                    String linhaNovaCortada = linhaNova.substring(0, 3);

                    for (String linha : linhasArquivo) {
                        String linhaForCortada = linha.substring(0, 3);
                        if (linhaForCortada.equals(linhaNovaCortada)) {
                            bw.write(linhaNova);
                            bw.newLine();
                        } else {
                            bw.write(linha);
                            bw.newLine();
                        }
                    }
                    bw.flush();
                    return true;

                } catch (IOException e) {
                    System.err.println("Erro ao atualizar arquivo " + filePet.getName());
                }
            }
        }
        return false;
    }

    @Override
    public boolean deletar(Pet pet) {
        File dir = new File("petsCadastrados");
        File[] arquivos = dir.listFiles();

        if (arquivos == null) {
            return false;
        }

        for (File filePet : arquivos) {
            if (filePet.isFile() && filePet.getName().endsWith(".txt")) {
                try (FileReader fr = new FileReader(filePet)) {
                    BufferedReader br = new BufferedReader(fr);

                    String linhaNome = br.readLine();
                    if (linhaNome != null && linhaNome.contains(" - ")) {
                        String nomeNoArquivo = linhaNome.split(" - ")[1];

                        if (nomeNoArquivo.equals(pet.getNome())) {
                            br.close();
                            return filePet.delete();
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao ler arquivo: " + filePet.getName());
                }
            }
        }
        return false;
    }
}
