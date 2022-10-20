package br.com.dbc.vemser.sistemaaluguelveiculos.model;

public class Cliente extends Pessoa {

    private int id_cliente;
    private Contato contato;
    private Endereco endereco;

    public Cliente(String nome, String cpf, Contato contato, Endereco endereco){
        super(nome, cpf);
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

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nome=" + super.getNome() +
                ", cpf=" + super.getCpf() +
                ", contato=" + contato +
                ", endereco=" + endereco +
                '}';
    }
}
