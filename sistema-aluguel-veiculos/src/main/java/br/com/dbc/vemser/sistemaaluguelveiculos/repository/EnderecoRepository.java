package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Endereco;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnderecoRepository implements Repositorio<Integer, Endereco> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENDERECO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Endereco adicionar(Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            endereco.setId_endereco(proximoId);

            String sql = "INSERT INTO ENDERECO_CLIENTE\n" +
                    "(ID_ENDERECO, RUA, NUMERO, BAIRRO, CIDADE, ESTADO, CEP, COMPLEMENTO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, endereco.getId_endereco());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());
            stmt.setString(8, endereco.getComplemento());

            int res = stmt.executeUpdate();
            System.out.println("adicionarEndereco.res=" + res);
            return endereco;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM ENDERECO_CLIENTE WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerEnderecoPorId.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ENDERECO_CLIENTE SET ");
            sql.append(" rua = ?,");
            sql.append(" numero = ?,");
            sql.append(" bairro = ?,");
            sql.append(" cidade = ?,");
            sql.append(" estado = ?,");
            sql.append(" CEP = ?,");
            sql.append(" complemento = ?");
            sql.append(" WHERE id_endereco = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.setString(7, endereco.getComplemento());
            stmt.setInt(8, id);

            int res = stmt.executeUpdate();
            System.out.println("editarEndereco.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Endereco> listar() throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ENDERECO_CLIENTE";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Endereco endereco = new Endereco();
                endereco.setId_endereco(res.getInt("id_endereco"));
                endereco.setRua(res.getString("rua"));
                endereco.setNumero(res.getString("numero"));
                endereco.setBairro(res.getString("bairro"));
                endereco.setCidade(res.getString("cidade"));
                endereco.setEstado(res.getString("estado"));
                endereco.setCep(res.getString("cep"));
                endereco.setComplemento(res.getString("complemento"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return enderecos;
    }


    public int retornarUltimoIdRegistrado() throws BancoDeDadosException {
        Endereco endereco = new Endereco();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT EC.ID_ENDERECO FROM ENDERECO_CLIENTE EC\n" +
                    "WHERE ID_ENDERECO = (SELECT MAX(ec.ID_ENDERECO) FROM ENDERECO_CLIENTE ec)";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                endereco.setId_endereco(res.getInt("id_endereco"));
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return endereco.getId_endereco();
    }

    public List<Endereco> listarEnderecoSemVinculo() throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ENDERECO_CLIENTE E\n" +
                    "LEFT JOIN CLIENTE L\n" +
                    "ON L.ID_ENDERECO = E.ID_ENDERECO\n" +
                    "WHERE L.ID_ENDERECO IS NULL ";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Endereco endereco = new Endereco();
                endereco.setId_endereco(res.getInt("id_endereco"));
                endereco.setRua(res.getString("rua"));
                endereco.setNumero(res.getString("numero"));
                endereco.setBairro(res.getString("bairro"));
                endereco.setCidade(res.getString("cidade"));
                endereco.setEstado(res.getString("estado"));
                endereco.setCep(res.getString("cep"));
                endereco.setComplemento(res.getString("complemento"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return enderecos;
    }
  public Endereco getPorId(Integer chave) throws BancoDeDadosException {
        Endereco endereco = new Endereco();
        Connection con = null;

        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO_CLIENTE\n" +
                    "WHERE id_endereco = ?";
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);
            ResultSet res = stmt.executeQuery();

            while (res.next()){
                endereco.setId_endereco(res.getInt("id_endereco"));
                endereco.setRua(res.getString("rua"));
                endereco.setNumero(res.getString("numero"));
                endereco.setBairro(res.getString("bairro"));
                endereco.setCidade(res.getString("cidade"));
                endereco.setEstado(res.getString("estado"));
                endereco.setCep(res.getString("cep"));
                endereco.setComplemento(res.getString("complemento"));
            }
            System.out.println("buscarEndereco.res="+ res);
            return endereco;
        }catch (SQLException e){
            throw new BancoDeDadosException(e.getCause());
        }finally {
            try {
                if(con != null){
                    con.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}

