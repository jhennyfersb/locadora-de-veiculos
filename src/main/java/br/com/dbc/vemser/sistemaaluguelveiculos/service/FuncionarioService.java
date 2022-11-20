package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CargoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CargoService cargoService;
    private final LogService logService;


    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        if (findByLogin(funcionario.getCpf()).isPresent()) {
            throw new RegraDeNegocioException("Já existe uma conta com esse nome de usuário");
        }
        FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
        CargoEntity cargoFuncionario = cargoService.findByIdCargo(funcionario.getIdCargo());
        funcionarioEntity.setCargoEntity(cargoFuncionario);
        String encode = passwordEncoder.encode(funcionarioEntity.getPassword());
        funcionarioEntity.setSenha(encode);
        funcionarioEntity.setAtivo('T');
        FuncionarioDTO funcionarioDTO = converterEmDTO(funcionarioRepository.save(funcionarioEntity));
        String cpfFuncionario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpfFuncionario, EntityLog.FUNCIONARIO));
        return funcionarioDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        this.findById(id);
        funcionarioRepository.deleteById(id);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.FUNCIONARIO));

    }

    public FuncionarioDTO update(Integer id, FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        FuncionarioEntity funcionarioEntity1 = funcionarioRepository.getById(id);
        FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
        funcionarioEntity.setIdFuncionario(id);
        CargoEntity cargoFuncionario = cargoService.findByIdCargo(funcionario.getIdCargo());
        funcionarioEntity.setCargoEntity(cargoFuncionario);
        String encode = passwordEncoder.encode(funcionarioEntity.getPassword());
        funcionarioEntity.setSenha(encode);
        funcionarioEntity.setAtivo(funcionarioEntity1.getAtivo());
        FuncionarioDTO funcionarioDTO = converterEmDTO(funcionarioRepository.save(funcionarioEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.FUNCIONARIO));
        return funcionarioDTO;

    }

    public List<FuncionarioDTO> list() throws RegraDeNegocioException {

        List<FuncionarioEntity> listar = funcionarioRepository.findAll();
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.FUNCIONARIO));
        return listar
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    private FuncionarioEntity converterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
    }

    public FuncionarioDTO converterEmDTO(FuncionarioEntity funcionarioEntity) {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionarioEntity.getIdFuncionario(),
                funcionarioEntity.getNome(),
                funcionarioEntity.getCpf(),
                funcionarioEntity.getEmail(), funcionarioEntity.getMatricula(),
                funcionarioEntity.getAtivo(), funcionarioEntity.getCargoEntity().getNome());
        return funcionarioDTO;
    }

    public FuncionarioDTO findDtoById(Integer id) throws RegraDeNegocioException {

        FuncionarioEntity funcionarioEntityRecuperado = findById(id);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.FUNCIONARIO));

        return converterEmDTO(funcionarioEntityRecuperado);
    }

    private FuncionarioEntity findById(Integer id) throws RegraDeNegocioException {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));
    }

    public Optional<FuncionarioEntity> findByLogin(String cpf) {
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findByCpf(cpf);
        return funcionarioEntity;
    }

    public String getIdLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public LoginDTO getLoggedUser() {
        Optional<FuncionarioEntity> funcionarioEntity = findByLogin(getIdLoggedUser());
        LoginDTO loginDTO = objectMapper.convertValue(funcionarioEntity.get(), LoginDTO.class);
        loginDTO.setCargoNome(funcionarioEntity.get().getCargoEntity().getNome());
        return loginDTO;
    }

    public FuncionarioDTO setAtivoFuncionario(Integer idFuncionario, char ativo) {
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findById(idFuncionario);
        funcionarioEntity.get().setAtivo(ativo);
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity.get()));
    }

    public void atualizarSenhaFuncionario(String cpf, String senha) {
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findByCpf(cpf);
        funcionarioEntity.get().setSenha(passwordEncoder.encode(senha));
        funcionarioRepository.save(funcionarioEntity.get());
        String cpfFuncionario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpfFuncionario, EntityLog.FUNCIONARIO));
    }


}

