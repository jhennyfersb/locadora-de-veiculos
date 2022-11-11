package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CargoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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


    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        if (findByLogin(funcionario.getCpf()).isPresent()) {
            throw new RegraDeNegocioException("Já existe uma conta com esse nome de usuário");
        }
        FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
        CargoEntity cargoFuncionario = cargoService.findByIdCargo(funcionario.getIdCargo());
        funcionarioEntity.setCargoEntity(cargoFuncionario);
        String encode = passwordEncoder.encode(funcionarioEntity.getPassword());
        funcionarioEntity.setSenha(encode);
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity));
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        this.findById(id);
        funcionarioRepository.deleteById(id);

    }

    public FuncionarioDTO update(Integer id, FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        this.findById(id);
        FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
        funcionarioEntity.setIdFuncionario(id);
        CargoEntity cargoFuncionario = cargoService.findByIdCargo(funcionario.getIdCargo());
        funcionarioEntity.setCargoEntity(cargoFuncionario);
        String encode = passwordEncoder.encode(funcionarioEntity.getPassword());
        funcionarioEntity.setSenha(encode);
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity));

    }

    public List<FuncionarioDTO> list() throws RegraDeNegocioException {

        List<FuncionarioEntity> listar = funcionarioRepository.findAll();
        return listar
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    public FuncionarioEntity converterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
    }

    public FuncionarioDTO converterEmDTO(FuncionarioEntity funcionarioEntity) {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionarioEntity.getIdFuncionario(),
                funcionarioEntity.getNome(),
                funcionarioEntity.getCpf(),
                funcionarioEntity.getEmail(),funcionarioEntity.getMatricula());
        return funcionarioDTO;
    }

    public FuncionarioDTO findById(Integer id) throws RegraDeNegocioException {

        Optional<FuncionarioEntity> funcionarioEntityRecuperado = funcionarioRepository.findById(id);

        if (funcionarioEntityRecuperado.isEmpty()) {
            throw new RegraDeNegocioException("Funcionario não encontrado");
        }
        return converterEmDTO(funcionarioEntityRecuperado.get());
    }

    public Optional<FuncionarioEntity> findByLogin(String cpf) {
        //não está retornando o cargo do banco de dados
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findByCpf(cpf);
       // CargoEntity cargoEntity = cargoService.findByIdCargo(1);//testando se funciona a injeção
        return funcionarioEntity;
    }

    public String getIdLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public LoginDTO getLoggedUser() throws RegraDeNegocioException{
        Optional<FuncionarioEntity> funcionarioEntity = findByLogin(getIdLoggedUser());
        LoginDTO loginDTO = objectMapper.convertValue(funcionarioEntity.get(), LoginDTO.class);
        loginDTO.setCargoNome(funcionarioEntity.get().getCargoEntity().getNome());
        return loginDTO;
    }

    public FuncionarioDTO setAtivoFuncionario(Integer idFuncionario,char ativo) throws RegraDeNegocioException {
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findById(idFuncionario);
        funcionarioEntity.get().setAtivo(ativo);
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity.get()));
    }

    public FuncionarioDTO atualizarSenhaFuncionario(String cpf,String senha){
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findByCpf(cpf);
        funcionarioEntity.get().setSenha(passwordEncoder.encode(senha));
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity.get()));

    }


}

