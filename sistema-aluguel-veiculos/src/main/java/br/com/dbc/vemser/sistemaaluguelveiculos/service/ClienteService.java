package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Cliente;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;
    private final ContatoService contatoService;

    public void create(Cliente cliente) {
        try {
            if(!validarCliente(cliente)){
                throw new RuntimeException("Cliente inválido");
            }
            Cliente clienteAdicionado = clienteRepository.create(cliente);
            System.out.println("Cliente adicinado com sucesso! " + clienteAdicionado);
            enderecoService.removerEnderecosOciosos();
            contatoService.removerContatosOciosos();
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try {
            boolean conseguiuRemover = clienteRepository.delete(id);
            System.out.println("Removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Cliente cliente) {
        try {
            boolean conseguiuEditar = clienteRepository.update(id, cliente);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void list() {
        try {
            clienteRepository.list().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public int retornarIdContato(int id){
        return clienteRepository.retornarIndiceContatoPorIdCliente(id);
    }

    public int retornarIdEndereco(int id){
        return clienteRepository.retornarIndiceEnderecoPorIdCliente(id);
    }

    public boolean validarCliente (Cliente cliente){
        return cliente.getCpf().length() == 11;
    }
}
