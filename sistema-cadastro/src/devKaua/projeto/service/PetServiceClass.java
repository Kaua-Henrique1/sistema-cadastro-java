package devKaua.projeto.service;

import devKaua.projeto.PetService;
import devKaua.projeto.domain.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PetServiceClass implements PetService {
    private AlteracoesPet alteracoesPet;
    private List<Pet> listaPet = new ArrayList<>();

    @Override
    public void cadastrar(Pet pet) {
        // implementar ja usando o metodo InterfaceUsarioCLI
    }

    @Override
    public void alterar(Pet pet) {

    }

    @Override
    public void remover(Pet pet) {

    }

    @Override
    public List<Pet> listarPets() {
        return List.of();
    }
}
