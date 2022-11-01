package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocacaoRepository implements Repositorio<Integer, LocacaoEntity> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_LOCACAO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public LocacaoEntity create(LocacaoEntity locacaoEntity) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            locacaoEntity.setIdLocacao(proximoId);

            String sql = "INSERT INTO LOCACAO\n" +
                    "(id_locacao, data_locacao, data_devolucao, valor_locacao_total, id_cliente, id_veiculo, id_funcionario, id_cartao)\n" +
                    "VALUES(?,?,?,?,?,?,?,?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, locacaoEntity.getIdLocacao());
            stmt.setDate(2, Date.valueOf(locacaoEntity.getDataLocacao()));
            stmt.setDate(3, Date.valueOf(locacaoEntity.getDataDevolucao()));
            stmt.setDouble(4, locacaoEntity.getValorLocacao());
            stmt.setInt(5, locacaoEntity.getCliente().getIdCliente());
            stmt.setInt(6, locacaoEntity.getVeiculo().getIdVeiculo());
            stmt.setInt(7, locacaoEntity.getFuncionario().getIdFuncionario());
            stmt.setInt(8, locacaoEntity.getCartaoCreditoEntity().getIdCartaoCredito());

            int res = stmt.executeUpdate();

            return locacaoEntity;
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

            String sql = "DELETE FROM LOCACAO WHERE id_locacao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int resultado = stmt.executeUpdate();

            return resultado > 0;
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
    public LocacaoEntity update(Integer id, LocacaoEntity locacaoEntity) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE LOCACAO SET \n");

            if (locacaoEntity.getDataLocacao() != null) {
                sql.append(" data_locacao = ?,");
            }
            if (locacaoEntity.getDataDevolucao() != null) {
                sql.append(" data_devolucao = ?,");
            }
            if (locacaoEntity.getValorLocacao() != null) {
                sql.append(" valor_locacao_total = ?,");
            }
            Cliente cliente = locacaoEntity.getCliente();
            if (cliente != null) {
                sql.append("id_cliente = ?,");
            }
            Veiculo veiculo = locacaoEntity.getVeiculo();
            if (veiculo.getIdVeiculo() != null) {
                sql.append(" id_veiculo = ?,");
            }
            Funcionario funcionario = locacaoEntity.getFuncionario();
            if (funcionario.getIdFuncionario() != null) {
                sql.append(" id_funcionario = ?,");
            }
            CartaoCreditoEntity cartaoCreditoEntity = locacaoEntity.getCartaoCreditoEntity();
            if (cartaoCreditoEntity != null) {
                sql.append(" id_cartao = ?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_locacao = ? ");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (locacaoEntity.getDataLocacao() != null) {
                stmt.setDate(index++, Date.valueOf(locacaoEntity.getDataLocacao()));
            }
            if (locacaoEntity.getDataDevolucao() != null) {
                stmt.setDate(index++, Date.valueOf(locacaoEntity.getDataDevolucao()));
            }
            if (locacaoEntity.getValorLocacao() != null) {
                stmt.setDouble(index++, locacaoEntity.getValorLocacao());
            }
            if (cliente != null) {
                stmt.setInt(index++, cliente.getIdCliente());
            }
            if (veiculo.getIdVeiculo() != null) {
                stmt.setInt(index++, veiculo.getIdVeiculo());
            }
            if (funcionario.getIdFuncionario() != null) {
                stmt.setInt(index++, funcionario.getIdFuncionario());
            }
            if (cartaoCreditoEntity != null) {
                stmt.setInt(index++, cartaoCreditoEntity.getIdCartaoCredito());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return locacaoEntity;
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
    public List<LocacaoEntity> list() throws BancoDeDadosException {
        List<LocacaoEntity> locacoes = new ArrayList<>();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "select * from LOCACAO L\n" +
                    "left join CLIENTE C2 on C2.ID_CLIENTE = L.ID_CLIENTE\n" +
                    "left join VEICULO V on V.ID_VEICULO = L.ID_VEICULO\n" +
                    "left join CONTATO C3 on C3.ID_CLIENTE = C2.ID_CLIENTE\n" +
                    "left join FUNCIONARIO F on F.ID_FUNCIONARIO = L.ID_FUNCIONARIO\n" +
                    "left join ENDERECO_CLIENTE EC on EC.ID_CLIENTE = C2.ID_CLIENTE\n" +
                    "left join CARTAO_CREDITO CC on L.ID_CARTAO = CC.ID_CARTAO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                LocacaoEntity locacaoEntity = getLocacaoFromResultset(res);
                locacoes.add(locacaoEntity);
            }
            return locacoes;
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

    private LocacaoEntity getLocacaoFromResultset(ResultSet res) throws SQLException {
        LocacaoEntity locacaoEntity = new LocacaoEntity();
        locacaoEntity.setIdLocacao(res.getInt("id_locacao"));
        locacaoEntity.setDataLocacao(res.getDate("data_locacao").toLocalDate());
        locacaoEntity.setDataDevolucao(res.getDate("data_devolucao").toLocalDate());
        locacaoEntity.setValorLocacao(res.getDouble("valor_locacao_total"));
        locacaoEntity.setCliente(getClientFromResultSet(res));
        locacaoEntity.setVeiculo(getVeiculoFromResultSet(res));
        locacaoEntity.setFuncionario(getFuncionarioResultSet(res));
        locacaoEntity.setCartaoCreditoEntity(getFromResultSetCartaoCredito(res));
        return locacaoEntity;
    }

    private Funcionario getFuncionarioResultSet(ResultSet res) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(res.getInt("id_funcionario"));
        funcionario.setNome(res.getString("nome_funcionario"));
        funcionario.setCpf(res.getString("cpf_funcionario"));
        funcionario.setEmail(res.getString("email_funcionario"));
        funcionario.setMatricula(res.getInt("matricula"));
        return funcionario;
    }

    private CartaoCreditoEntity getFromResultSetCartaoCredito(ResultSet res) throws SQLException {
        CartaoCreditoEntity cartaoCreditoEntity = new CartaoCreditoEntity();
        cartaoCreditoEntity.setIdCartaoCredito(res.getInt("id_cartao"));
        cartaoCreditoEntity.setNumero(res.getString("numero_cartao"));
        cartaoCreditoEntity.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
        cartaoCreditoEntity.setValidade(res.getString("validade"));
        cartaoCreditoEntity.setLimite(res.getDouble("limite"));
        return cartaoCreditoEntity;
    }

    private Cliente getClientFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("id_cliente"));
        cliente.setNome(res.getString("nome_cliente"));
        cliente.setCpf(res.getString("cpf_cliente"));
        return cliente;
    }

    private Veiculo getVeiculoFromResultSet(ResultSet res) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(res.getInt("id_veiculo"));
        veiculo.setMarca(res.getString("marca"));
        veiculo.setModelo(res.getString("modelo"));
        veiculo.setCor(res.getString("cor"));
        veiculo.setAno(res.getInt("ano"));
        veiculo.setQuilometragem(res.getDouble("quilometragem"));
        veiculo.setValorLocacao(res.getDouble("valor_locacao_diario"));
        veiculo.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.valueOf(res.getString("disponibilidade")));
        veiculo.setPlaca(res.getString("placa"));
        return veiculo;
    }

    public LocacaoEntity findById(Integer chave) throws BancoDeDadosException {
        LocacaoEntity locacaoEntity = new LocacaoEntity();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM Locacao L " +
                    "JOIN CLIENTE C2 on C2.ID_CLIENTE = L.ID_CLIENTE\n" +
                    "JOIN VEICULO V2 on V2.ID_VEICULO = L.ID_VEICULO " +
                    "JOIN FUNCIONARIO F2 ON F2.ID_FUNCIONARIO = L.ID_FUNCIONARIO " +
                    "JOIN CARTAO_CREDITO CC on CC.ID_CARTAO = L.ID_CARTAO " +
                    "WHERE id_locacao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, chave);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                locacaoEntity.setIdLocacao(res.getInt("id_locacao"));
                locacaoEntity.setDataLocacao(res.getDate("data_locacao").toLocalDate());
                locacaoEntity.setDataDevolucao(res.getDate("data_devolucao").toLocalDate());
                locacaoEntity.setValorLocacao(res.getDouble("valor_locacao_total"));
                locacaoEntity.setCliente(getClientFromResultSet(res));
                locacaoEntity.setVeiculo(getVeiculoFromResultSet(res));
                locacaoEntity.setFuncionario(getFuncionarioResultSet(res));
                locacaoEntity.setCartaoCreditoEntity(getFromResultSetCartaoCredito(res));
            }

            return locacaoEntity;
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
}
