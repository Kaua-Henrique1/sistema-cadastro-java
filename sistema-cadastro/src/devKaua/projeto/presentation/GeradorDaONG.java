package devKaua.projeto.presentation;

import devKaua.projeto.application.*;
import devKaua.projeto.infrastructure.PessoaRepository;
import devKaua.projeto.infrastructure.PessoaRepositoryTXT;
import devKaua.projeto.infrastructure.PetRepository;
import devKaua.projeto.infrastructure.PetRepositoryTXT;

public class GeradorDaONG {

    public static void main(String[] args) {
        PetRepository petRepository = new PetRepositoryTXT("petsCadastrados");
        petRepository.carregarDados();

        PessoaRepository pessoaRepository = new PessoaRepositoryTXT("adotantesCadastradosTXT");
        pessoaRepository.carregarDados();

        PetService petService = new PetService(petRepository);
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        InterfaceDeUsuario ui = new InterfaceUsuarioCLI();

        InterfaceFacade facade = new InterfaceFacade(ui, petService, pessoaService);

        ui.iniciarFluxoPrincipal(facade);
    }

}