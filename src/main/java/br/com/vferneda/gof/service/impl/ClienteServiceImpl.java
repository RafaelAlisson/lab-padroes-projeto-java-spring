package br.com.vferneda.gof.service.impl;

import br.com.vferneda.gof.model.Cliente;
import br.com.vferneda.gof.repository.ClienteRepository;
import br.com.vferneda.gof.model.Endereco;
import br.com.vferneda.gof.repository.EnderecoRepository;
import br.com.vferneda.gof.service.ClienteService;
import br.com.vferneda.gof.service.ViaCepService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    private EnderecoRepository enderecoRepository;

    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {

        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if (!clienteBd.isPresent()) {
            throw new NotFoundException("Cliente não encontrado");
        }
        salvarClienteComCep(cliente);
    }

    @Override
    public void deletar(Long id) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if (!clienteBd.isPresent()) {
            throw new NotFoundException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente atualizarEmail(Long id, String email) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if (!clienteBd.isPresent()) {
            throw new NotFoundException("Cliente não encontrado");
        }
        clienteBd.get().setEmail(email);
        return clienteRepository.save(clienteBd.get());
    }

    private void salvarClienteComCep(Cliente cliente) {

        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {

            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);

        clienteRepository.save(cliente);
    }
}
