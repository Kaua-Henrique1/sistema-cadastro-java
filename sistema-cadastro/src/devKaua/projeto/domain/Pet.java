package devKaua.projeto.domain;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pet é uma Entity porque possui identidade própria (ID) que persiste ao longo do tempo.
 * Também atua como Aggregate Root, pois é o ponto de entrada para manipular
 * o Value Object Endereco associado.
 * <p>
 * Melhorias aplicadas:
 * <p>
 * 1. SETTERS PRIVADOS (Vernon, Cap. 5 — "Guard the Aggregate invariants"):
 *    Todos os setters agora são privados. Isso impede que código externo
 *    altere o estado da entidade diretamente, forçando o uso de métodos de
 *    comportamento com nomes expressivos (alterarNome, alterarIdade, etc.).
 *    Isso protege as invariantes do Aggregate e expressa a Ubiquitous Language.
 * <p>
 * 2. MÉTODOS DE COMPORTAMENTO ao invés de setters públicos:
 *    Em DDD, entidades expõem comportamento, não dados. Os métodos alterarNome(),
 *    alterarIdade(), alterarPeso() e alterarRaca() encapsulam a validação e a
 *    mudança de estado, tornando o código mais expressivo e seguro.
 * <p>
 * 3. REMOÇÃO DO CONSTRUTOR VAZIO (SonarQube + DDD):
 *    Um construtor vazio permitia criar um Pet em estado inválido (sem nome, sem tipo, etc.).
 *    Em DDD, entidades devem ser criadas já em estado válido (self-validation pattern).
 * <p>
 * 4. FACTORY METHOD estático "criar" (Vernon, Cap. 11 — Factories):
 *    O método estático Pet.criar(...) é um Factory Method que encapsula a lógica
 *    de criação, incluindo a geração automática de ID. Isso deixa claro que
 *    novos Pets são criados por essa factory, enquanto o construtor com ID
 *    explícito é usado apenas para reconstituição a partir da persistência.
 * <p>
 * 5. CONSTANTE SEM_DADOS como padrão de domínio:
 *    Mantida como constante da entidade para representar campos opcionais
 *    não preenchidos, seguindo a Ubiquitous Language do projeto.
 */
public class Pet {

    private static final AtomicLong idGenerator = new AtomicLong(1);

    public static final String SEM_DADOS = "NÃO INFORMADO";

    private final Long id;
    private String nome;
    private final Endereco endereco;
    private final Sexo sexo;
    private final TipoAnimal tipoAnimal;
    private String idade;
    private String peso;
    private String raca;

    // --- Construtor para RECONSTITUIÇÃO a partir da persistência ---
    public Pet(Long id, String nome, Endereco endereco, Sexo sexo,
               TipoAnimal tipoAnimal, String idade, String peso, String raca) {
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório para reconstituição.");
        }
        this.id = id;
        setNome(nome);
        setIdade(idade);
        setPeso(peso);
        setRaca(raca);
        this.endereco = endereco;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
    }

    // --- Construtor privado para o Factory Method ---
    private Pet(String nome, Endereco endereco, Sexo sexo,
                TipoAnimal tipoAnimal, String idade, String peso, String raca) {
        this.id = idGenerator.getAndIncrement();
        setNome(nome);
        setIdade(idade);
        setPeso(peso);
        setRaca(raca);
        this.endereco = endereco;
        this.sexo = sexo;
        this.tipoAnimal = tipoAnimal;
    }

    /**
     * FACTORY METHOD (Vernon, Cap. 11):
     * Encapsula a criação de novos Pets com geração automática de ID.
     * Garante que todo Pet criado já nasce em estado consistente.
     */
    public static Pet criar(String nome, Endereco endereco, Sexo sexo,
                            TipoAnimal tipoAnimal, String idade, String peso, String raca) {
        return new Pet(nome, endereco, sexo, tipoAnimal, idade, peso, raca);
    }

    // --- Métodos de comportamento (substituem setters públicos) ---

    /**
     * Altera o nome do Pet com validação.
     * Em DDD, métodos de comportamento expressam a Ubiquitous Language
     * e protegem as invariantes da entidade.
     */
    public void alterarNome(String nome) {
        setNome(nome);
    }

    public void alterarIdade(String idade) {
        setIdade(idade);
    }

    public void alterarPeso(String peso) {
        setPeso(peso);
    }

    public void alterarRaca(String raca) {
        setRaca(raca);
    }

    // --- Setters privados com validação (protegem as invariantes) ---

    private void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            this.nome = SEM_DADOS;
            return;
        }
        String regexNome = "[A-Za-z]+(\\s)+[A-Za-z]+(\\s+|$)";
        Pattern regraNome = Pattern.compile(regexNome);
        Matcher condicionalNome = regraNome.matcher(nome);
        if (!condicionalNome.find()) {
            throw new IllegalArgumentException("Nome inválido! Use apenas letras e espaço.");
        }
        this.nome = nome;
    }

    private void setIdade(String idade) {
        if (idade == null || idade.isEmpty()) {
            this.idade = SEM_DADOS;
            return;
        }
        String regexIdadePeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraIdadePeso = Pattern.compile(regexIdadePeso);
        Matcher condicionalIdade = regraIdadePeso.matcher(idade);
        if (Double.parseDouble(idade) > 60 || !condicionalIdade.find()) {
            throw new IllegalArgumentException("Idade inválida! Escreva apenas números entre 0.1 Anos até 60 anos.");
        }
        this.idade = idade;
    }

    private void setPeso(String peso) {
        if (peso == null || peso.isEmpty()) {
            this.peso = SEM_DADOS;
            return;
        }
        String pesoPet = peso.replace(',', '.');
        String regexPeso = "[0-9]+((\\\\.|,)[0-9]+)?";
        Pattern regraPeso = Pattern.compile(regexPeso);
        Matcher condicionalPeso = regraPeso.matcher(pesoPet);
        if (Double.parseDouble(pesoPet) > 60 || Double.parseDouble(pesoPet) < 0.5 || !condicionalPeso.find()) {
            throw new IllegalArgumentException("Peso inválido! Escreva apenas números entre 0.5kl até 60kl.");
        }
        this.peso = peso;
    }

    private void setRaca(String raca) {
        if (raca == null || raca.isEmpty()) {
            this.raca = SEM_DADOS;
            return;
        }
        String regexRaca = "[a-z,A-Z]+";
        Pattern regraRaca = Pattern.compile(regexRaca);
        Matcher condicionalRaca = regraRaca.matcher(raca);
        if (!condicionalRaca.find()) {
            throw new IllegalArgumentException("Raça inválida! Escreva apenas letras.");
        }
        this.raca = raca;
    }

    // --- Acessores (getters) ---

    public Long getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public String getIdade() {
        return idade;
    }

    public String getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    /**
     * Atualiza o gerador de IDs para evitar colisão ao carregar dados persistidos.
     */
    public static void atualizarGerador(Long maiorIdEncontrado) {
        if (maiorIdEncontrado >= idGenerator.get()) {
            idGenerator.set(maiorIdEncontrado + 1);
        }
    }

    @Override
    public String toString() {
        return ". " + getID() + " - " + getNome() + " - " + getEndereco().toString()
                + " - " + getTipoAnimal() + " - " + getSexo() + " - " + getIdade()
                + " anos - " + getPeso() + "kg - " + getRaca();
    }
}
