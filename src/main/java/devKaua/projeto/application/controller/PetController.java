package devKaua.projeto.application.controller;

import devKaua.projeto.infrastructure.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PetController {
    @Autowired
    private PetRepository petRepository;



    @GetMapping
    public
}
