package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Endereco;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EnderecoRepository implements Repositorio<Integer, Endereco> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENDERECO_CLIENTE.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Endereco create(Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            endereco.setIdEndereco(proximoId);

            String sql = "INSERT INTO ENDERECO_CLIENTE\n" +
                    "(id_endereco, id_cliente,  rua, numero, bairro, cidade, estado, cep, complemento)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, endereco.getIdEndereco());
            stmt.setInt(2, endereco.getIdCliente());
            stmt.setString(3, endereco.getRua());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getCep());
            stmt.setString(9, endereco.getComplemento());

            stmt.executeUpdate();

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
    public boolean delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM ENDERECO_CLIENTE WHERE ID_ENDERECO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();

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
    public Endereco update(Integer id, Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ENDERECO_CLIENTE SET ");
            sql.append(" id_cliente = ?,");
            sql.append(" rua = ?,");
            sql.append(" numero = ?,");
            sql.append(" bairro = ?,");
            sql.append(" cidade = ?,");
            sql.append(" estado = ?,");
            sql.append(" cep = ?,");
            sql.append(" complemento = ?");
            sql.append(" WHERE id_endereco = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            endereco.setIdEndereco(id);
            stmt.setInt(1, endereco.getIdCliente());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCep());
            stmt.setString(8, endereco.getComplemento());
            stmt.setInt(9, id);

            stmt.executeUpdate();

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
    public List<Endereco> list() throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ENDERECO_CLIENTE";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Endereco endereco = new Endereco();
                endereco.setIdEndereco(res.getInt("id_endereco"));
                endereco.setIdCliente(res.getInt("id_cliente"));
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

  public Endereco findById(Integer chave) throws BancoDeDadosException {
        Endereco endereco = new Endereco();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO_CLIENTE\n" +
                    "WHERE id_endereco = ?";
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);
            ResultSet res = stmt.executeQuery();

            while (res.next()){
                endereco.setIdEndereco(res.getInt("id_endereco"));
                endereco.setIdCliente(res.getInt("id_cliente"));
                endereco.setRua(res.getString("rua"));
                endereco.setNumero(res.getString("numero"));
                endereco.setBairro(res.getString("bairro"));
                endereco.setCidade(res.getString("cidade"));
                endereco.setEstado(res.getString("estado"));
                endereco.setCep(res.getString("cep"));
                endereco.setComplemento(res.getString("complemento"));
            }

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
    public List<Endereco> findEnderecoByIdCliente(Integer chave) throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO_CLIENTE\n" +
                    "WHERE id_cliente = ?";
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);
            ResultSet res = stmt.executeQuery();

            while (res.next()){
                Endereco endereco = new Endereco();
                endereco.setIdEndereco(res.getInt("id_endereco"));
                endereco.setIdCliente(res.getInt("id_cliente"));
                endereco.setRua(res.getString("rua"));
                endereco.setNumero(res.getString("numero"));
                endereco.setBairro(res.getString("bairro"));
                endereco.setCidade(res.getString("cidade"));
                endereco.setEstado(res.getString("estado"));
                endereco.setCep(res.getString("cep"));
                endereco.setComplemento(res.getString("complemento"));
                enderecos.add(endereco);
            }

            return enderecos;
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
