package br.com.dbc.vemser.sistemaaluguelveiculos.model;


public class Funcionario {

    private Integer idFuncionario;
    private Integer matricula;
    private String nome;
    private String cpf;

    public Funcionario(String nome, String cpf, Integer matricula){
        this.nome = nome;
        this.cpf = cpf;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String toString() {
        return "Funcionario{" +
                "idFuncionario=" + this.idFuncionario +
                ", nome=" + this.getNome() +
                ", cpf=" + this.getCpf() +
                ", matricula=" + this.matricula +
                '}';
    }
}
