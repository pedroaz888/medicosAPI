package com.br.api.domain.medico;


import com.br.api.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Medico {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String email;

        private String telefone;
        private String crm;

        @Enumerated
        private Especialidade especialidade;

        @Embedded
        private Endereco endereco;

        private Boolean ativo;


        public Medico(DadosCadastroMedico dados) {
                this.ativo = true;
                this.nome = dados.nome();
                this.email = dados.email();
                this.telefone = dados.telefone();
                this.crm = dados.crm();
                this.especialidade = dados.especialidade();
                this.endereco = new Endereco(dados.endereco());

        }

        public void atualizarInfo(DadosAtualizaMedico dados) {

                if(dados.nome() != null){
                        this.nome = dados.nome();
                }
                if(dados.telefone() != null){
                        this.telefone = dados.telefone();
                }

                if(dados.email() != null){
                        this.email = dados.email();
                }

                if(dados.endereco() != null){
                        this.endereco.atualizarInfo(dados.endereco());
                }


        }

        public void excluir() {
                this.ativo=false;
        }

        public void ativar() {
                this.ativo=true;
        }
}
