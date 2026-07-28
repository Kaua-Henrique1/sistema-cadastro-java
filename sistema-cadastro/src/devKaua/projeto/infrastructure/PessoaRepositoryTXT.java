package devKaua.projeto.infrastructure;

import devKaua.projeto.domain.Endereco;
import devKaua.projeto.domain.Pessoa;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PessoaRepositoryTXT implements PessoaRepository {

    private final String diretorioCaminho;
    private final List<Pessoa> listaPessoa = new ArrayList<>();

    public PessoaRepositoryTXT(String diretorioCaminho) {
        this.diretorioCaminho = diretorioCaminho;
        File dir = new File(diretorioCaminho);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public Optional<Pessoa> buscarPorId(Long id) {
        return listaPessoa.stream()
                .filter(pessoa -> pessoa.getId().equals(id))
                .findFirst();
    }

    @Override
    public void carregarDados() {
        File dir = new File(getDiretorioCaminho());
        File[] arquivos = dir.listFiles();

        if (arquivos == null) return;

        long maiorIdEncontrado = 0L;
        for (File filePessoa : arquivos) {
            if (filePessoa.isFile() && filePessoa.getName().endsWith(".txt")) {
                try (BufferedReader br = new BufferedReader(new FileReader(filePessoa))) {
                    String linhaID = br.readLine();
                    if (linhaID == null || !linhaID.startsWith("ID - ")) continue;
                    long idPessoa = Long.parseLong(linhaID.split(" - ")[1]);

                    String nome = br.readLine().split(" - ")[1];
                    String cpf = br.readLine().split(" - ")[1];
                    String telefone = br.readLine().split(" - ")[1];
                    String email = br.readLine().split(" - ")[1];
                    Endereco endereco = extractEnderecoFromLine(br.readLine());

                    if (idPessoa > maiorIdEncontrado) {
                        maiorIdEncontrado = idPessoa;
                    }

                    Pessoa novaPessoa = new Pessoa(idPessoa, nome, cpf, telefone, email, endereco);
                    this.listaPessoa.add(novaPessoa);

                } catch (Exception e) {
                    System.out.println("Erro ao ler arquivo: " + filePessoa.getName() + " - " + e.getMessage());
                }
            }
        }
        Pessoa.atualizarGerador(maiorIdEncontrado);
    }

    @Override
    public void salvar(Pessoa pessoa) {
        DateTimeFormatter formatada = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatadaMin = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(formatada);
        String dataFormatadaMin = agora.format(formatadaMin);

        String nomePessoaFile = pessoa.getNome().toUpperCase().trim().replace(" ", "");
        String nomeFile = dataFormatada + "T" + dataFormatadaMin + "-" + nomePessoaFile + pessoa.getId();

        File fileDir = new File(getDiretorioCaminho());
        if (!fileDir.exists()) fileDir.mkdir();

        File filePessoa = new File(fileDir, nomeFile + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePessoa))) {
            bw.write("ID - " + pessoa.getId()); bw.newLine();
            bw.write("1 - " + pessoa.getNome()); bw.newLine();
            bw.write("2 - " + pessoa.getCpf()); bw.newLine();
            bw.write("3 - " + pessoa.getTelefone()); bw.newLine();
            bw.write("4 - " + pessoa.getEmail()); bw.newLine();

            // Grava o endereço no formato "5 - rua, numero, cidade"
            String enderecoStr = pessoa.getEndereco() != null
                    ? pessoa.getEndereco().getRua() + ", " + pessoa.getEndereco().getNumero() + ", " + pessoa.getEndereco().getCidade()
                    : Pessoa.SEM_DADOS + ", " + Pessoa.SEM_DADOS + ", " + Pessoa.SEM_DADOS;

            bw.write("5 - " + enderecoStr); bw.newLine();

            bw.flush();
            this.listaPessoa.add(pessoa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Pessoa pessoaAntiga, Pessoa pessoaNova) {
        File dir = new File(getDiretorioCaminho());
        File[] arquivos = dir.listFiles();

        if (arquivos == null) return;

        Pessoa pessoaAtualizada = (pessoaNova != null) ? pessoaNova : pessoaAntiga;

        for (File filePessoa : arquivos) {
            if (filePessoa.isFile() && filePessoa.getName().endsWith(".txt")) {
                if (isPessoaInFile(pessoaAntiga, filePessoa)) {

                    // Sobrescreve o arquivo inteiro com os novos dados
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePessoa))) {
                        bw.write("ID - " + pessoaAtualizada.getId()); bw.newLine();
                        bw.write("1 - " + pessoaAtualizada.getNome()); bw.newLine();
                        bw.write("2 - " + pessoaAtualizada.getCpf()); bw.newLine();
                        bw.write("3 - " + pessoaAtualizada.getTelefone()); bw.newLine();
                        bw.write("4 - " + pessoaAtualizada.getEmail()); bw.newLine();

                        String enderecoStr = pessoaAtualizada.getEndereco() != null
                                ? pessoaAtualizada.getEndereco().getRua() + ", " + pessoaAtualizada.getEndereco().getNumero() + ", " + pessoaAtualizada.getEndereco().getCidade()
                                : Pessoa.SEM_DADOS + ", " + Pessoa.SEM_DADOS + ", " + Pessoa.SEM_DADOS;

                        bw.write("5 - " + enderecoStr); bw.newLine();
                        bw.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    int index = this.listaPessoa.indexOf(pessoaAntiga);
                    if (index != -1) {
                        this.listaPessoa.set(index, pessoaAtualizada);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void deletar(Pessoa pessoa) {
        File dir = new File(getDiretorioCaminho());
        File[] arquivos = dir.listFiles();

        if (arquivos == null) return;

        for (File filePessoa : arquivos) {
            if (filePessoa.isFile() && filePessoa.getName().endsWith(".txt")) {
                if (isPessoaInFile(pessoa, filePessoa)) {
                    this.listaPessoa.remove(pessoa);
                    filePessoa.delete();
                    return;
                }
            }
        }
    }

    @Override
    public List<Pessoa> listarTodos() {
        return Collections.unmodifiableList(listaPessoa);
    }

    private static boolean isPessoaInFile(Pessoa pessoa, File filePessoa) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePessoa))) {
            String linhaID = br.readLine();
            if (linhaID != null && linhaID.startsWith("ID - ")) {
                String idNoArquivo = linhaID.split(" - ")[1];
                return idNoArquivo.equals(String.valueOf(pessoa.getId()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static Endereco extractEnderecoFromLine(String linhaEndereco) {
        if (linhaEndereco == null || !linhaEndereco.startsWith("5 - ")) {
            return new Endereco(Pessoa.SEM_DADOS, Pessoa.SEM_DADOS, Pessoa.SEM_DADOS);
        }

        String dadosEndereco = linhaEndereco.substring(4);
        String[] partesEndereco = dadosEndereco.split(", ");

        if (partesEndereco.length < 3) {
            return new Endereco(dadosEndereco, Pessoa.SEM_DADOS, Pessoa.SEM_DADOS);
        }

        String rua = partesEndereco[0].trim();
        String numero = partesEndereco[1].trim();
        String cidade = partesEndereco[2].trim();

        if (numero.isEmpty()) {
            numero = Pessoa.SEM_DADOS;
        }

        return new Endereco(rua, numero, cidade);
    }

    private String getDiretorioCaminho() {
        return diretorioCaminho;
    }
}