package br.com.vferneda.gof.service;

import br.com.vferneda.gof.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);

    Cliente atualizarEmail(Long id, String email);
}