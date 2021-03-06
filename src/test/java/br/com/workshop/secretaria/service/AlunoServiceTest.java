package br.com.workshop.secretaria.service;

import br.com.workshop.secretaria.domain.Aluno;
import br.com.workshop.secretaria.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    public static final Long MATRICULA   = 1L;
    public static final String ESCOLA    = "Objetivo";
    public static final String NOME      = "Daiane";
    public static final String SERIE     = "3A-EM";
    public static final int INDEX = 0;

    private List<Aluno> listAluno;

    private Aluno aluno;

    @Mock
    private AlunoRepository repository;

    @InjectMocks
    private AlunoService service;

    @Captor
    private ArgumentCaptor<Aluno> captorAluno;

    @Captor
    private ArgumentCaptor<Long> captorMatricula;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startAluno();
    }

    @Test
    void testCreateAluno(){
        when(repository.save(any())).thenReturn(aluno);

        Aluno alunoCreated = service.createAluno(this.aluno);

        assertEquals(MATRICULA, alunoCreated.getMatricula());
        assertEquals(NOME, alunoCreated.getNome());
        assertEquals(ESCOLA, alunoCreated.getEscola());
        assertEquals(SERIE, alunoCreated.getSerie());
    }

    @Test
    void testFindById(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(aluno));

        Aluno response = service.findById(MATRICULA);

        assertNotNull(response);
        assertEquals(Aluno.class, response.getClass());
        assertEquals(MATRICULA, response.getMatricula());
        assertEquals(ESCOLA, response.getEscola());
        assertEquals(NOME, response.getNome());
        assertEquals(SERIE, response.getSerie());
    }

    @Test
    void testDeleteAluno() {
        service.deleteAluno(MATRICULA);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(listAluno);
        List<Aluno> alunoList = service.getAll();
        assertNotNull(alunoList.get(INDEX));
    }

    @Test
    void testeUpdateAluno() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(aluno));
        String novaSerie = "3A - EM";
        aluno.setSerie(novaSerie);
        when(repository.save(any())).thenReturn(aluno);

        service.updateAluno(MATRICULA, aluno);
        Mockito.verify(repository).findById(captorMatricula.capture());
        Mockito.verify(repository).save(captorAluno.capture());

        Aluno alunoUpdated = captorAluno.getValue();
        Long matriculaCaptured = captorMatricula.getValue();
        assertEquals(MATRICULA, matriculaCaptured);
        assertEquals(novaSerie, alunoUpdated.getSerie());
    }

    @Test
    void whenFindByNameThenReturnAnAlunoListByName() {
        when(repository.findAllByNomeContainingIgnoreCase(anyString()))
                .thenReturn(listAluno);
        List<Aluno> response = service.findByName(NOME);

        assertNotNull(response);
        assertEquals(Aluno.class, response.get(INDEX).getClass());
        assertEquals(1, response.size());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(MATRICULA, response.get(INDEX).getMatricula());
        assertEquals(ESCOLA, response.get(INDEX).getEscola());
        assertEquals(SERIE, response.get(INDEX).getSerie());
    }

    private void startAluno() {
        aluno = new Aluno(MATRICULA, ESCOLA,
                NOME, SERIE);
        listAluno = List.of(aluno);
    }
}