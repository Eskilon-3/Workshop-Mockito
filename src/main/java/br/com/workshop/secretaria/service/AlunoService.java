package br.com.workshop.secretaria.service;

import br.com.workshop.secretaria.domain.Aluno;
import br.com.workshop.secretaria.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public Aluno createAluno(Aluno aluno){
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findByName(String nome) {
       return alunoRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    public List<Aluno> getAll() {
        return alunoRepository.findAll();
    }

    public Aluno findById(Long matricula) {
        Optional<Aluno> alunoId = alunoRepository.findById(matricula);
        return alunoId.get();
    }

    public void deleteAluno(Long matricula){
        alunoRepository.deleteById(matricula);
    }

    public void updateAluno(Long matricula, Aluno aluno){
        Optional<Aluno> alunoExists = alunoRepository.findById(matricula);
        if (alunoExists.isPresent()) {
            alunoRepository.save(aluno);
        }
    }
}
