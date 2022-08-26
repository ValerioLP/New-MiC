package it.ProjectMiC.culturalheritagedigitalization.runner;


import it.ProjectMiC.culturalheritagedigitalization.repository.ValidazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class Populator implements CommandLineRunner {

    @Autowired
    ValidazioneRepository validazioneRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
