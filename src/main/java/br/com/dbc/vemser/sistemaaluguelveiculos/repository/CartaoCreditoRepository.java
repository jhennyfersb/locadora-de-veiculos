package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartaoCreditoRepository implements Repositorio<Integer, CartaoCreditoEntity> {
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
    public CartaoCreditoEntity create(CartaoCreditoEntity cartaoCreditoEntity) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cartaoCreditoEntity.setIdCartaoCredito(proximoId);

            String sql = "INSERT INTO CARTAO_CREDITO\n" +
                    "(id_cartao, numero_cartao, bandeira_cartao, validade, limite)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cartaoCreditoEntity.getIdCartaoCredito());
            stmt.setString(2, cartaoCreditoEntity.getNumero());
            stmt.setString(3, cartaoCreditoEntity.getBandeiraCartao().toString());
            stmt.setString(4, cartaoCreditoEntity.getValidade());
            stmt.setDouble(5, cartaoCreditoEntity.getLimite());

            stmt.executeUpdate();
            return cartaoCreditoEntity;
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
    public CartaoCreditoEntity update(Integer id, CartaoCreditoEntity cartaoCreditoEntity) throws BancoDeDadosException {
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

            cartaoCreditoEntity.setIdCartaoCredito(id);
            stmt.setString(1, cartaoCreditoEntity.getNumero());
            stmt.setString(2, cartaoCreditoEntity.getBandeiraCartao().toString());
            stmt.setString(3, cartaoCreditoEntity.getValidade());
            stmt.setDouble(4, cartaoCreditoEntity.getLimite());
            stmt.setInt(5, id);

            stmt.executeUpdate();

            return cartaoCreditoEntity;

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
    public List<CartaoCreditoEntity> list() throws BancoDeDadosException {
        List<CartaoCreditoEntity> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CARTAO_CREDITO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                CartaoCreditoEntity cartaoCreditoEntity = new CartaoCreditoEntity();
                cartaoCreditoEntity.setIdCartaoCredito(res.getInt("id_cartao"));
                cartaoCreditoEntity.setNumero(res.getString("numero_cartao"));
                cartaoCreditoEntity.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
                cartaoCreditoEntity.setValidade(res.getString("validade"));
                cartaoCreditoEntity.setLimite(res.getDouble("limite"));
                cartoes.add(cartaoCreditoEntity);
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

    public CartaoCreditoEntity findById(Integer idCartao) throws BancoDeDadosException {
        CartaoCreditoEntity cartaoCreditoEntity = new CartaoCreditoEntity();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CARTAO_CREDITO\n" +
                    "WHERE id_cartao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1,idCartao);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                cartaoCreditoEntity.setIdCartaoCredito(res.getInt("id_cartao"));
                cartaoCreditoEntity.setNumero(res.getString("numero_cartao"));
                cartaoCreditoEntity.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
                cartaoCreditoEntity.setValidade(res.getString("validade"));
                cartaoCreditoEntity.setLimite(res.getDouble("limite"));
            }
            return cartaoCreditoEntity;
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
