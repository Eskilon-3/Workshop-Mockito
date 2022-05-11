package br.com.workshop.secretaria.service;

import br.com.workshop.secretaria.AplicationConfigTest;
import br.com.workshop.secretaria.domain.Aluno;
import br.com.workshop.secretaria.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest extends AplicationConfigTest {

    @Mock
    private AlunoRepository repository;

    @InjectMocks
    private AlunoService service;

    @Captor
    private ArgumentCaptor<Aluno> captor;

    @Test
    void testCreateAluno(){
        Aluno aluno = new Aluno();
        aluno.setMatricula(1L);
        aluno.setNome("Daiana");
        aluno.setEscola("Alub");
        aluno.setDataNascimento(LocalDate.of(2002,2,20));
        aluno.setSerie("3A - EM");

        service.createAluno(aluno);
        Mockito.verify(repository).save(captor.capture());

        Aluno alunoCreated = captor.getValue();
        assertEquals(aluno.getMatricula(), alunoCreated.getMatricula());
        assertEquals(aluno.getNome(), alunoCreated.getNome());
        assertEquals(aluno.getEscola(), alunoCreated.getEscola());
        assertEquals(aluno.getSerie(), alunoCreated.getSerie());
        assertEquals(aluno.getDataNascimento(), alunoCreated.getDataNascimento());
    }
}