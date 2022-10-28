package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Cliente;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Contato;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository implements Repositorio<Integer, Cliente> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CLIENTE.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Cliente create(Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);

            String sql = "INSERT INTO CLIENTE\n" +
                    "(id_cliente, nome_cliente, cpf_cliente)\n" +
                    " VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpf());

            stmt.executeUpdate();

            return cliente;
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

            String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";

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
    public Cliente update(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CLIENTE SET ");
            sql.append(" nome_cliente = ?,");
            sql.append(" cpf_cliente = ? ");
            sql.append(" WHERE id_cliente = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            cliente.setIdCliente(id);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, id);

            stmt.executeUpdate();

            return cliente;
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
    public List<Cliente> list() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CLIENTE";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(res.getInt("id_cliente"));
                cliente.setNome(res.getString("nome_cliente"));
                cliente.setCpf(res.getString("cpf_cliente"));
                clientes.add(cliente);
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
        return clientes;
    }

    public Cliente getPorId(Integer chave) throws BancoDeDadosException {
        Cliente cliente = new Cliente();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CLIENTE \n" +
                    " WHERE ID_CLIENTE = ?";

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, chave);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                cliente.setIdCliente(res.getInt("id_cliente"));
                cliente.setNome(res.getString("nome_cliente"));
                cliente.setCpf(res.getString("cpf_cliente"));
            }
            return cliente;
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

//    private Contato getContatoFromResultSet(ResultSet res) throws SQLException {
//        Contato contato = new Contato();
//        contato.setIdContato(res.getInt("id_contato"));
//        contato.setTelefone(res.getString("telefone"));
//        contato.setEmail(res.getString("email"));
//        return contato;
//    }

//    private Endereco getEnderecoResultSet(ResultSet res) throws SQLException {
//        Endereco endereco = new Endereco();
//        endereco.setIdEndereco(res.getInt("id_endereco"));
//        endereco.setRua(res.getString("rua"));
//        endereco.setNumero(res.getString("numero"));
//        endereco.setBairro(res.getString("bairro"));
//        endereco.setCidade(res.getString("cidade"));
//        endereco.setEstado(res.getString("estado"));
//        endereco.setCep(res.getString("cep"));
//        endereco.setComplemento(res.getString("complemento"));
//        return endereco;
//    }

//    public int retornarIndiceContatoPorIdCliente(int id) {
//        int idContato = 0;
//        try {
//            Connection con = conexaoBancoDeDados.getConnection();
//
//            String sql = "SELECT C.ID_CONTATO FROM CLIENTE C WHERE C.ID_CLIENTE = ?";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1, id);
//            ResultSet res = stmt.executeQuery();
//            while (res.next()) {
//                idContato = res.getInt("id_contato");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return idContato;
//    }

//    public int retornarIndiceEnderecoPorIdCliente(int id) {
//        int idEndereco = 0;
//        try {
//            Connection con = conexaoBancoDeDados.getConnection();
//
//            String sql = "SELECT C.ID_ENDERECO FROM CLIENTE C WHERE C.ID_CLIENTE = ?";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1, id);
//            ResultSet res = stmt.executeQuery();
//            while (res.next()) {
//                idEndereco = res.getInt("id_endereco");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return idEndereco;
//    }
}
