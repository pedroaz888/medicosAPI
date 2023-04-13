package com.br.api.domain.medico;

import com.br.api.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizaMedico(
        @NotNull
        Long id,
        String nome,

        String email,
        String telefone,
        DadosEndereco endereco) {
}
