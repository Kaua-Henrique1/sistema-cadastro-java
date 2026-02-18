package devKaua.projeto.domain;

import java.util.ArrayList;
import java.util.List;

public class Endereco {
    private String rua;
    private String numero;
    private String cidade;

    public Endereco(String rua, String numero, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return this.rua + ", " + this.numero + " - " + this.cidade;
    }

    public String toFormatado() {
        return this.rua + ", " + this.numero + ", " + this.cidade;
    }


    public List<Pet> consultaEndereco(List<Pet> listaAtual, String cidade) {
        List<Pet> listaFiltrada = new ArrayList<>();
        boolean encontrou = false;
        for (Pet pet : listaAtual) {
            if (pet.getEndereco().Cidade().equalsIgnoreCase(cidade)) {
                encontrou = true;
                listaFiltrada.add(pet);
                return listaFiltrada;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pet encontrado nessa cidade.");
        }
        return listaFiltrada;
    }

    String Cidade() {
        return cidade;
    }
}