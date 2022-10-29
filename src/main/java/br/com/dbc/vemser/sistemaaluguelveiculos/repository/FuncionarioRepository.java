package br.com.dbc.vemser.sistemaaluguelveiculos.repository;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FuncionarioRepository implements Repositorio<Integer, Funcionario> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_FUNCIONARIO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Funcionario create(Funcionario funcionario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            funcionario.setIdFuncionario(proximoId);

            String sql = "INSERT INTO FUNCIONARIO\n" +
                    "(id_funcionario, nome_funcionario, cpf_funcionario, email_funcionario, matricula)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, funcionario.getIdFuncionario());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getCpf());
            stmt.setString(4, funcionario.getEmail());
            stmt.setInt(5, funcionario.getMatricula());

            stmt.executeUpdate();

            return funcionario;
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

            String sql = "DELETE FROM FUNCIONARIO WHERE id_funcionario = ?";

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
    public Funcionario update(Integer id, Funcionario funcionario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE FUNCIONARIO SET ");
            sql.append(" nome_funcionario = ?,");
            sql.append(" cpf_funcionario = ?,");
            sql.append(" email_funcionario = ?,");
            sql.append(" matricula = ? ");
            sql.append(" WHERE id_funcionario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            funcionario.setIdFuncionario(id);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getEmail());
            stmt.setInt(4, funcionario.getMatricula());
            stmt.setInt(5, id);

            stmt.executeUpdate();

            return funcionario;
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
    public List<Funcionario> list() throws BancoDeDadosException {
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM FUNCIONARIO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(res.getInt("id_funcionario"));
                funcionario.setNome(res.getString("nome_funcionario"));
                funcionario.setCpf(res.getString("cpf_funcionario"));
                funcionario.setEmail(res.getString("email_funcionario"));
                funcionario.setMatricula(res.getInt("matricula"));
                funcionarios.add(funcionario);
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
        return funcionarios;
    }

    public Funcionario findById(Integer chave) throws BancoDeDadosException {
        Funcionario funcionario = new Funcionario();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM FUNCIONARIO\n" +
                    "WHERE id_funcionario = ?";

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                funcionario.setIdFuncionario(res.getInt("id_funcionario"));
                funcionario.setNome(res.getString("nome_funcionario"));
                funcionario.setCpf(res.getString("cpf_funcionario"));
                funcionario.setEmail(res.getString("email_funcionario"));
                funcionario.setMatricula(res.getInt("matricula"));
            }

            return funcionario;
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
