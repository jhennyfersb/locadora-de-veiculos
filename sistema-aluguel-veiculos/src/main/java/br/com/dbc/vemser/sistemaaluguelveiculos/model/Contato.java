package br.com.dbc.vemser.sistemaaluguelveiculos.model;

public class Contato {

    private int id_contato;
    private String telefone;
    private String email;

    public Contato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    public Contato(int id_contato) {
        this.id_contato = id_contato;
    }

    public Contato(){

    }

    public int getId_contato() {
        return id_contato;
    }

    public void setId_contato(int id_contato) {
        this.id_contato = id_contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id_contato=" + id_contato +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}