package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Contato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContatoRepository implements Repositorio<Integer, Contato> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CONTATO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Contato create(Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            contato.setIdContato(proximoId);

            String sql = "INSERT INTO CONTATO\n" +
                    "(id_contato, id_cliente, telefone, email)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, contato.getIdContato());
            stmt.setInt(2, contato.getIdCliente());
            stmt.setString(3, contato.getTelefone());
            stmt.setString(4, contato.getEmail());

            stmt.executeUpdate();

            return contato;
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

            String sql = "DELETE FROM CONTATO WHERE ID_CONTATO = ?";

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
    public Contato update(Integer id, Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CONTATO SET ");
            sql.append(" id_cliente = ?,");
            sql.append(" telefone = ?,");
            sql.append(" email = ?");
            sql.append(" WHERE id_contato = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            contato.setIdContato(id);
            stmt.setInt(1, contato.getIdCliente());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setInt(4, id);

            stmt.executeUpdate();

            return contato;
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
    public List<Contato> list() throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CONTATO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contato contato = new Contato();
                contato.setIdContato(res.getInt("id_contato"));
                contato.setIdCliente(res.getInt("id_cliente"));
                contato.setTelefone(res.getString("telefone"));
                contato.setEmail(res.getString("email"));
                contatos.add(contato);
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
        return contatos;
    }

    public int retornarUltimoIdRegistrado() throws BancoDeDadosException {
        Contato contato = new Contato();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT C.ID_CONTATO  FROM CONTATO C\n" +
                    "WHERE ID_CONTATO = (SELECT MAX(C.ID_CONTATO) FROM CONTATO C)";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                contato.setIdContato(res.getInt("id_contato"));
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
        return contato.getIdContato();
    }

    public List<Contato> listarContatoSemVinculo() throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CONTATO C\n" +
                    "LEFT JOIN CLIENTE CL\n" +
                    "ON C.ID_CLIENTE = CL.ID_CLIENTE\n" +
                    "WHERE C.ID_CONTATO  IS NULL ";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contato contato = new Contato();
                contato.setIdContato(res.getInt("id_contato"));
                contato.setTelefone(res.getString("telefone"));
                contato.setEmail(res.getString("email"));
                contatos.add(contato);
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
        return contatos;
    }

    public Contato findById(Integer chave) throws BancoDeDadosException {
        Contato contato = new Contato();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CONTATO\n" +
                    "WHERE id_contato = ?";

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                contato.setIdContato(res.getInt("id_contato"));
                contato.setIdCliente(res.getInt("id_cliente"));
                contato.setTelefone(res.getString("telefone"));
                contato.setEmail(res.getString("email"));
            }

            return contato;
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
