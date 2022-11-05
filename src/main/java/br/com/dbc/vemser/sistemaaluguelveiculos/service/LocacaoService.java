package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final VeiculoRepository veiculoRepository;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final CartaoCreditoService cartaoCreditoService;

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public LocacaoDTO create(LocacaoCreateDTO locacaoDTO) throws RegraDeNegocioException {
//        try {
//            LocacaoEntity locacaoEntityAdicionada = locacaoRepository.create(converterEmLocacao(locacaoDTO));
//            FuncionarioEntity funcionarioEntity = funcionarioRepository.findById(locacaoEntityAdicionada.getFuncionarioEntity().getIdFuncionario());
//            locacaoEntityAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
//            veiculoRepository.update(locacaoEntityAdicionada.getVeiculo().getIdVeiculo(), locacaoEntityAdicionada.getVeiculo());
//
//            emailService.sendEmail(locacaoEntityAdicionada, "locacao-template.ftl", funcionarioEntity.getEmail());
//            return converterEmDTO(locacaoEntityAdicionada);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
//        }
        LocacaoEntity locacaoEntity = criarLocacaoAPartirDeIds(locacaoDTO);
        veiculoService.alterarDisponibilidadeVeiculo(locacaoEntity.getVeiculoEntity());
        return converterEmDTO(locacaoRepository.save(locacaoEntity));
    }

    public void delete(Integer id) throws RegraDeNegocioException {
//        try {
//            LocacaoEntity locacaoEntityDeletada = converterDTOEmLocacao(findById(id));
//            locacaoRepository.deleteById(id);
//            locacaoEntityDeletada.getVeiculo().alterarDisponibilidadeVeiculo();
//            veiculoRepository.update(locacaoEntityDeletada.getVeiculo().getIdVeiculo(), locacaoEntityDeletada.getVeiculo());
//            emailService.sendEmail(locacaoEntityDeletada,
//                    "locacao-template-delete.ftl",
//                    locacaoEntityDeletada.getFuncionarioEntity().getEmail());
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
//        }
    }

    public LocacaoDTO findById(Integer idLocacao) throws RegraDeNegocioException {
//        try {
//            LocacaoEntity locacaoEntityRecuperada = locacaoRepository.findById(idLocacao);
//
//            if (locacaoEntityRecuperada.getIdLocacao() != null) {
//                return converterEmDTO(locacaoEntityRecuperada);
//            } else {
//                throw new RegraDeNegocioException("Locação não encontrada");
//            }
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
//        }
        return null;
    }

//    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {
//        try {
//            FuncionarioDTO funcionarioDTO = funcionarioService.findById(locacaoCreateDTO.getIdFuncionario());
//            ClienteDTO clienteDTO = clienteService.findById(locacaoCreateDTO.getIdCliente());
//
//            VeiculoDTO veiculoDTO = veiculoService.findById(locacaoCreateDTO.getIdVeiculo());
//
//            if(veiculoDTO.getDisponibilidadeVeiculo().getDisponibilidade() == 1){
//                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
//            }
//            CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findById(locacaoCreateDTO.getIdCartaoCredito());
//            LocacaoEntity locacaoEntity = objectMapper.convertValue(this.findById(id), LocacaoEntity.class);
//
//            locacaoEntity.setVeiculo(objectMapper.convertValue(veiculoDTO, Veiculo.class));
//            locacaoEntity.setClienteEntity(objectMapper.convertValue(clienteDTO, ClienteEntity.class));
//            locacaoEntity.setFuncionarioEntity(objectMapper.convertValue(funcionarioDTO, FuncionarioEntity.class));
//            locacaoEntity.setCartaoCreditoEntity(objectMapper.convertValue(cartaoCreditoDTO, CartaoCreditoEntity.class));
//
//            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(),
//                    locacaoEntity.getDataDevolucao().atStartOfDay());
//            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculo().getValorLocacao());
//
//            locacaoEntity.setDataLocacao(locacaoCreateDTO.getDataLocacao());
//            locacaoEntity.setDataDevolucao(locacaoCreateDTO.getDataDevolucao());
//
//            if(locacaoEntity.getDataDevolucao().isBefore(locacaoEntity.getDataLocacao())) {
//                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
//            }
//
//            LocacaoEntity locacaoEntityAdicionada = this.locacaoRepository.update(locacaoEntity.getIdLocacao(), locacaoEntity);
//            locacaoEntityAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
//            veiculoRepository.update(locacaoEntityAdicionada.getVeiculo().getIdVeiculo(), locacaoEntityAdicionada.getVeiculo());
//
//            emailService.sendEmail(locacaoEntity, "locacao-template-update.ftl", funcionarioDTO.getEmail());
//            return converterEmDTO(locacaoEntityAdicionada);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
//        }
//        return null;
//    }

    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        try {
            List<LocacaoEntity> listar = locacaoRepository.findAll();
            return listar.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public LocacaoEntity criarLocacaoAPartirDeIds(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        try {
            Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findById(locacaoCreateDTO.getIdFuncionario());
            if (funcionarioEntity.isEmpty()) {
                throw new RegraDeNegocioException("Funcionário não encontrado.");
            }
            Optional<ClienteEntity> clienteEntity = clienteRepository.findById(locacaoCreateDTO.getIdCliente());
            if (clienteEntity.isEmpty()) {
                throw new RegraDeNegocioException("Cliente não encontrado.");
            }
            Optional<VeiculoEntity> veiculo = veiculoRepository.findById(locacaoCreateDTO.getIdVeiculo());
            if (veiculo.isEmpty()) {
                throw new RegraDeNegocioException("Veiculo não encontrado.");
            }
            if (veiculo.get().getDisponibilidadeVeiculo().getDisponibilidade() == 1) {
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            Optional<CartaoCreditoEntity> cartaoCreditoEntity = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
            if (cartaoCreditoEntity.isEmpty()) {
                throw new RegraDeNegocioException("Cartão de crédito não encontrado.");
            }
            if (locacaoCreateDTO.getDataDevolucao().isBefore(locacaoCreateDTO.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }

            LocacaoEntity locacaoEntity = new LocacaoEntity(null,
                    locacaoCreateDTO.getDataLocacao(),
                    locacaoCreateDTO.getDataDevolucao(),
                    clienteEntity.get(),
                    veiculo.get(),
                    cartaoCreditoEntity.get(),
                    funcionarioEntity.get());

            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(), locacaoEntity.getDataDevolucao().atStartOfDay());
            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculoEntity().getValorLocacao());

            return locacaoEntity;
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao instanciar locação.");
        }
    }

    public LocacaoDTO converterEmDTO(LocacaoEntity locacaoEntity) {
//        ClienteDTO clienteDTO = clienteService.converterEmDTO(locacaoEntity.getClienteEntity());
//        VeiculoDTO veiculoDTO = veiculoService.converterEmDTO(locacaoEntity.getVeiculo());
//        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.converterEmDTO(locacaoEntity.getCartaoCreditoEntity());
//        FuncionarioDTO funcionarioDTO = funcionarioService.converterEmDTO(locacaoEntity.getFuncionarioEntity());
//
//        new LocacaoDTO(locacaoEntity.getIdLocacao(), locacaoEntity.getDataLocacao(), locacaoEntity.getDataDevolucao(), locacaoEntity.getValorLocacao(),clienteDTO,
//                veiculoDTO,cartaoCreditoDTO,funcionarioDTO);
//        return objectMapper.convertValue(locacaoEntity, LocacaoDTO.class);
        return null;
    }

    public LocacaoEntity converterDTOEmLocacao(LocacaoDTO locacaodto) {
        return objectMapper.convertValue(locacaodto, LocacaoEntity.class);
    }

}

