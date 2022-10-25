package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.BandeiraCartao;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCredito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartaoCreditoRepository implements Repositorio<Integer, CartaoCredito> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CARTAO_CREDITO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public CartaoCredito create(CartaoCredito cartaoCredito) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cartaoCredito.setIdCartaoCredito(proximoId);

            String sql = "INSERT INTO CARTAO_CREDITO\n" +
                    "(id_cartao, numero_cartao, bandeira_cartao, validade, limite)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cartaoCredito.getIdCartaoCredito());
            stmt.setString(2, cartaoCredito.getNumero());
            stmt.setString(3, cartaoCredito.getBandeiraCartao().toString());
            stmt.setString(4, cartaoCredito.getValidade());
            stmt.setDouble(5, cartaoCredito.getLimite());

            stmt.executeUpdate();
            return cartaoCredito;
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
    public CartaoCredito update(Integer id, CartaoCredito cartaoCredito) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CARTAO_CREDITO SET ");
            sql.append(" numero_cartao = ?,");
            sql.append(" bandeira_cartao = ?,");
            sql.append(" validade = ?,");
            sql.append(" limite = ?");
            sql.append(" WHERE id_cartao = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            cartaoCredito.setIdCartaoCredito(id);
            stmt.setString(1, cartaoCredito.getNumero());
            stmt.setString(2, cartaoCredito.getBandeiraCartao().toString());
            stmt.setString(3, cartaoCredito.getValidade());
            stmt.setDouble(4, cartaoCredito.getLimite());
            stmt.setInt(5, id);

            stmt.executeUpdate();

            return cartaoCredito;

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

            String sql = "DELETE FROM CARTAO_CREDITO WHERE id_cartao = ?";

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
    public List<CartaoCredito> list() throws BancoDeDadosException {
        List<CartaoCredito> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CARTAO_CREDITO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                CartaoCredito cartaoCredito = new CartaoCredito();
                cartaoCredito.setIdCartaoCredito(res.getInt("id_cartao"));
                cartaoCredito.setNumero(res.getString("numero_cartao"));
                cartaoCredito.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
                cartaoCredito.setValidade(res.getString("validade"));
                cartaoCredito.setLimite(res.getDouble("limite"));
                cartoes.add(cartaoCredito);
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
        return cartoes;
    }

    public CartaoCredito getPorId(Integer idCartao) throws BancoDeDadosException {
        CartaoCredito  cartaoCredito = new CartaoCredito();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CARTAO_CREDITO\n" +
                    "WHERE id_cartao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1,idCartao);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                cartaoCredito.setIdCartaoCredito(res.getInt("id_cartao"));
                cartaoCredito.setNumero(res.getString("numero_cartao"));
                cartaoCredito.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
                cartaoCredito.setValidade(res.getString("validade"));
                cartaoCredito.setLimite(res.getDouble("limite"));
            }
            return cartaoCredito;
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
