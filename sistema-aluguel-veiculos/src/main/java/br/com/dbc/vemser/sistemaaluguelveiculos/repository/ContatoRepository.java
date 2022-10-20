package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Contato;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContatoRepository implements Repositorio<Integer, Contato> {
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

    public Contato adicionar(Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            contato.setId_contato(proximoId);

            String sql = "INSERT INTO CONTATO\n" +
                    "(ID_CONTATO, TELEFONE, EMAIL)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, contato.getId_contato());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());

            int res = stmt.executeUpdate();
            System.out.println("adicionarPessoa.res=" + res);
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CONTATO WHERE ID_CONTATO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerContatoPorId.res=" + res);

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
    public boolean editar(Integer id, Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CONTATO SET ");
            sql.append(" telefone = ?,");
            sql.append(" email = ?");
            sql.append(" WHERE id_contato = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, contato.getTelefone());
            stmt.setString(2, contato.getEmail());
            stmt.setInt(3, id);

            int res = stmt.executeUpdate();
            System.out.println("editarContato.res=" + res);

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
    public List<Contato> listar() throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CONTATO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contato contato = new Contato();
                contato.setId_contato(res.getInt("id_contato"));
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
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT C.ID_CONTATO  FROM CONTATO C\n" +
                    "WHERE ID_CONTATO = (SELECT MAX(C.ID_CONTATO) FROM CONTATO C)";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                contato.setId_contato(res.getInt("id_contato"));
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
        return contato.getId_contato();
    }

    public List<Contato> listarContatoSemVinculo() throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CONTATO C\n" +
                    "LEFT JOIN CLIENTE L\n" +
                    "ON L.ID_CONTATO = C.ID_CONTATO\n" +
                    "WHERE L.ID_CONTATO  IS NULL ";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contato contato = new Contato();
                contato.setId_contato(res.getInt("id_contato"));
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
}
