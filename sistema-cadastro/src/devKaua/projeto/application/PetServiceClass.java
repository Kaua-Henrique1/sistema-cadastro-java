package devKaua.projeto.application;

import devKaua.projeto.domain.*;

import java.util.ArrayList;
import java.util.List;

public class PetServiceClass implements PetService {
    private AlteracoesPet alteracoesPet;
    private final List<Pet> listaPet = new ArrayList<>();
    private InterfaceUsarioCLI interfaceUsarioCLI;
    private PersistenciaDadosTXT persistenciaDadosTXT;
    public static final String SEM_DADOS = "NÃO INFORMADO";


    @Override
    public void cadastrar() {
        Pet atributos = null;
        Endereco enderecoPet = null;

        String nomePet = "";
        String racaPet = "";
        String idadePet = "";
        String pesoPet = "";
        TipoAnimal tipoPet = null;
        Sexo sexoPet = null;
        while (atributos == null) {
            try {
                int tipoPetInt = interfaceUsarioCLI.solicitarTipo();
                tipoPet = TipoAnimal.values()[tipoPetInt - 1];

                int sexoPetInt = interfaceUsarioCLI.solicitarSexo();
                sexoPet = Sexo.values()[sexoPetInt - 1];

                String[] enderecoPetU = interfaceUsarioCLI.solicitarEndereco();
                enderecoPet = new Endereco(enderecoPetU[0], enderecoPetU[1], enderecoPetU[2]);

                nomePet = interfaceUsarioCLI.solicitarNome();
                racaPet = interfaceUsarioCLI.solicitarRaca();
                idadePet = interfaceUsarioCLI.solicitarIdade();
                pesoPet = interfaceUsarioCLI.solicitarPeso();

                atributos = new Pet(null, nomePet,enderecoPet,sexoPet,tipoPet,idadePet,pesoPet,racaPet);
            } catch (IllegalArgumentException e) {
                interfaceUsarioCLI.erroSalvarObjPet();
            }
        }
        if (nomePet.isEmpty()) {
            nomePet = SEM_DADOS;
        }
        if (racaPet.isEmpty()) {
            racaPet = SEM_DADOS;
        }
        if (idadePet.isEmpty()) {
            idadePet = SEM_DADOS;
        }
        if (pesoPet.isEmpty()) {
            pesoPet = SEM_DADOS;
        }

        Pet novoPet = new Pet(null,nomePet,enderecoPet,sexoPet,tipoPet,idadePet,pesoPet,racaPet);
        String enderecoPetStr = enderecoPet.toFormatado();

        persistenciaDadosTXT.salvar(novoPet, enderecoPetStr);
    }

    @Override
    public void alterar() {
        List<Pet> listaParaAlteraPet = listarPetsPorCriterio();
        int numeroPet = interfaceUsarioCLI.numeroPetListFiltrada();

        int contador = 0;
        for (Pet pets : listaParaAlteraPet) {
            contador++;
            if (numeroPet == contador) {
                int consultaDesejada;
                do {
                    consultaDesejada = this.interfaceUsarioCLI.solicitarOpcaoAlterar();
                } while (consultaDesejada < 1 || consultaDesejada > 4);

                switch (consultaDesejada) {
                    case 1:
                        boolean nomeValido = false;
                        while (!nomeValido) {
                            try {
                                String nomeNovo = interfaceUsarioCLI.solicitarNome();

                                pets.setNome(nomeNovo);
                                String novoNomeTxt = "1 - " + nomeNovo;
                                boolean isTrue = this.persistenciaDadosTXT.atualizar(pets, novoNomeTxt);

                                if (isTrue) {
                                    interfaceUsarioCLI.exibirPet(pets);
                                    interfaceUsarioCLI.exibirMensagemAlteracaoConcluida();
                                }
                                nomeValido = true;

                            } catch (IllegalArgumentException e) {
                                interfaceUsarioCLI.errorExibir(e.getMessage());
                            }
                        }
                        break;
                    case 2:
                        boolean idadeValido = false;
                        while (!idadeValido) {
                            try {
                                String idadeNovo = interfaceUsarioCLI.solicitarIdade();

                                pets.setIdade(idadeNovo);
                                String novoIdadeTxt = "5 - " + idadeNovo + " anos";
                                boolean isTrue = this.persistenciaDadosTXT.atualizar(pets, novoIdadeTxt);

                                if (isTrue) {
                                    interfaceUsarioCLI.exibirPet(pets);
                                    interfaceUsarioCLI.exibirMensagemAlteracaoConcluida();
                                }
                                idadeValido = true;

                            } catch (IllegalArgumentException e) {
                                interfaceUsarioCLI.errorExibir(e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        boolean racaValido = false;
                        while (!racaValido) {
                            try {
                                String racaNovo = interfaceUsarioCLI.solicitarRaca();

                                pets.setRaca(racaNovo);
                                String novoRacaTxt = "7 - " + racaNovo;
                                boolean isTrue = this.persistenciaDadosTXT.atualizar(pets, novoRacaTxt);

                                if (isTrue) {
                                    interfaceUsarioCLI.exibirPet(pets);
                                    interfaceUsarioCLI.exibirMensagemAlteracaoConcluida();
                                }
                                racaValido = true;

                            } catch (IllegalArgumentException e) {
                                interfaceUsarioCLI.errorExibir(e.getMessage());
                            }
                        }
                        break;
                    case 4:
                        boolean pesoValido = false;
                        while (!pesoValido) {
                            try {
                                String pesoNovo = interfaceUsarioCLI.solicitarPeso();

                                pets.setPeso(pesoNovo);
                                String novoPesoTxt = "6 - " + pesoNovo+ "kg";
                                boolean isTrue = this.persistenciaDadosTXT.atualizar(pets, novoPesoTxt);

                                if (isTrue) {
                                    interfaceUsarioCLI.exibirPet(pets);
                                    interfaceUsarioCLI.exibirMensagemAlteracaoConcluida();
                                }
                                pesoValido = true;

                            } catch (IllegalArgumentException e) {
                                interfaceUsarioCLI.errorExibir(e.getMessage());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void remover() {
        List<Pet> listaParaDeletarPet = listarPetsPorCriterio();
        int numeroPet = interfaceUsarioCLI.numeroPetListFiltrada();

        int contador = 0;
        String respostaDeletarPet;
        for (Pet pet : listaParaDeletarPet) {
            contador++;
            if (numeroPet == contador) {
                do {
                    respostaDeletarPet = this.interfaceUsarioCLI.confirmacaoDeletarPet(pet);
                } while (!respostaDeletarPet.equalsIgnoreCase("SIM") && !respostaDeletarPet.equalsIgnoreCase("NÃO"));

                if (respostaDeletarPet.equalsIgnoreCase("SIM")) {
                    this.persistenciaDadosTXT.deletar(pet);
                    this.listaPet.remove(pet);
                    this.interfaceUsarioCLI.mensagemDeletarPet();
                } else {
                    return;
                }
            }
        }
    }

    @Override
    public List<Pet> listarPetsPorCriterio() {
        int consultaDesejadaInterface;
        String consultaInterface;

        List<Pet> listaFiltrada = new ArrayList<>();
        int respostaTipoAnimal;
        do {
            respostaTipoAnimal = interfaceUsarioCLI.consultaCachorroOuGato();
        } while (respostaTipoAnimal < 1 || respostaTipoAnimal > 2);

        for (Pet petOpcoes : this.listaPet) {
            if (respostaTipoAnimal == 1 && petOpcoes.getTipoAnimal() == TipoAnimal.CACHORRO) {
                listaFiltrada.add(petOpcoes);
            } else if (respostaTipoAnimal == 2 && petOpcoes.getTipoAnimal() == TipoAnimal.GATO) {
                listaFiltrada.add(petOpcoes);
            }
        }

        if (listaFiltrada.isEmpty()) {
            interfaceUsarioCLI.exibirMensagemErrorConsulta();
            return listaFiltrada;
        }
        interfaceUsarioCLI.exibirListaPets(listaFiltrada);


        do {
            consultaDesejadaInterface = interfaceUsarioCLI.solicitarOpcaoFiltro();
        } while (consultaDesejadaInterface < 1 || consultaDesejadaInterface > 6);
        consultaInterface = interfaceUsarioCLI.solicitarTextoBusca();

        List<Pet> listFiltrada2 = alteracoesPet.consultaPet(listaFiltrada, consultaDesejadaInterface, consultaInterface);

        if (listFiltrada2.isEmpty()) {
            interfaceUsarioCLI.exibirMensagemErrorConsulta();
            return listaFiltrada;
        }
        interfaceUsarioCLI.exibirListaPets(listFiltrada2);

        int adicionarConsulta;
        do {
            adicionarConsulta = interfaceUsarioCLI.solicitarConfirmacaoSimNao();
        } while (adicionarConsulta < 1 || adicionarConsulta > 2);

        if (adicionarConsulta != 1) {
            return listFiltrada2;
        }

        do {
            consultaDesejadaInterface = interfaceUsarioCLI.solicitarOpcaoFiltro();
        } while (consultaDesejadaInterface < 1 || consultaDesejadaInterface > 6);
        consultaInterface = interfaceUsarioCLI.solicitarTextoBusca();

        List<Pet> listFiltrada3 = alteracoesPet.consultaPet(listFiltrada2, consultaDesejadaInterface, consultaInterface);

        if (listFiltrada3.isEmpty()) {
            interfaceUsarioCLI.exibirMensagemErrorConsulta();
            return listaFiltrada;
        }
        interfaceUsarioCLI.exibirListaPets(listFiltrada3);
        return listFiltrada3;
    }

    @Override
    public void listarPetsCompleta() {
        interfaceUsarioCLI.exibirListaPets(this.listaPet);
    }

}
