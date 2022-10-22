package br.com.dbc.vemser.sistemaaluguelveiculos.model;

public class Cliente {

    private int id_cliente;
    private Contato contato;
    private Endereco endereco;
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf, Contato contato, Endereco endereco){
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.endereco = endereco;
    }
    public Cliente(int id_cliente){
        this.id_cliente = id_cliente;
    }


    public Cliente(){

    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nome=" + this.getNome() +
                ", cpf=" + this.getCpf() +
                ", contato=" + contato +
                ", endereco=" + endereco +
                '}';
    }
}
