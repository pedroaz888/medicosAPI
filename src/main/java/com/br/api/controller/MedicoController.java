package com.br.api.controller;

import com.br.api.domain.medico.DadosListagemMedico;
import com.br.api.domain.medico.Medico;
import com.br.api.domain.medico.MedicoRepository;
import com.br.api.domain.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping //os paramentros(dados estão vindo por DTO, então precisa converter//criando um construtor em Medicos
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){

        var medico = new Medico(dados);
        repository.save(new Medico(dados));

        var uri = uriBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }

//    @GetMapping("/")
//    aqui retorna todos os atributos
//    public List<Medico> lista(){
//       return repository.findAll();
//    }

    @GetMapping("/")
    //o repository devolve a lista de medico então precisa converter em DTO
    public ResponseEntity<Page<DadosListagemMedico>> lista(@PageableDefault(size=10, sort={"nome"}) Pageable pagina){
        var page = repository.findAllByAtivoTrue(pagina).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);

    }


    @PutMapping
    @Transactional
    public ResponseEntity atualiza(@RequestBody @Valid DadosAtualizaMedico dados) {
        Medico medico = new Medico();
        medico = repository.getReferenceById(dados.id());
        medico.atualizarInfo(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity exclui(@PathVariable Long id){
        Medico medico = new Medico();
        medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity ativa(@PathVariable Long id){
        Medico medico = new Medico();
        medico = repository.getReferenceById(id);
        medico.ativar();

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        Medico medico = new Medico();
        medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

}

