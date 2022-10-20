package br.com.dbc.vemser.sistemaaluguelveiculos.model;

public class Funcionario extends Pessoa {

    private Integer idFuncionario;
    private Integer matricula;

    public Funcionario(String nome, String cpf, Integer matricula){
        super(nome, cpf);
        this.matricula = matricula;
    }

    public Funcionario(){
    }
    public Funcionario(int idFuncionario){
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setCpf(String cpf) {
        super.setCpf(cpf);
    }

    @Override
    public String getCpf() {
        return super.getCpf();
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "idFuncionario=" + this.idFuncionario +
                ", nome=" + super.getNome() +
                ", cpf=" + super.getCpf() +
                ", matricula=" + this.matricula +
                '}';
    }
}
