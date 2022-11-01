package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Locacao;
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
public class LocacaoRepository implements Repositorio<Integer, Locacao> {
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
    public Locacao create(Locacao locacao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            locacao.setIdLocacao(proximoId);

            String sql = "INSERT INTO LOCACAO\n" +
                    "(id_locacao, data_locacao, data_devolucao, valor_locacao_total, id_cliente, id_veiculo, id_funcionario, id_cartao)\n" +
                    "VALUES(?,?,?,?,?,?,?,?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, locacao.getIdLocacao());
            stmt.setDate(2, Date.valueOf(locacao.getDataLocacao()));
            stmt.setDate(3, Date.valueOf(locacao.getDataDevolucao()));
            stmt.setDouble(4, locacao.getValorLocacao());
            stmt.setInt(5, locacao.getCliente().getIdCliente());
            stmt.setInt(6, locacao.getVeiculo().getIdVeiculo());
            stmt.setInt(7, locacao.getFuncionario().getIdFuncionario());
            stmt.setInt(8, locacao.getCartaoCredito().getIdCartaoCredito());

            int res = stmt.executeUpdate();

            return locacao;
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
    public Locacao update(Integer id, Locacao locacao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE LOCACAO SET \n");

            if (locacao.getDataLocacao() != null) {
                sql.append(" data_locacao = ?,");
            }
            if (locacao.getDataDevolucao() != null) {
                sql.append(" data_devolucao = ?,");
            }
            if (locacao.getValorLocacao() != null) {
                sql.append(" valor_locacao_total = ?,");
            }
            Cliente cliente = locacao.getCliente();
            if (cliente != null) {
                sql.append("id_cliente = ?,");
            }
            Veiculo veiculo = locacao.getVeiculo();
            if (veiculo.getIdVeiculo() != null) {
                sql.append(" id_veiculo = ?,");
            }
            Funcionario funcionario = locacao.getFuncionario();
            if (funcionario.getIdFuncionario() != null) {
                sql.append(" id_funcionario = ?,");
            }
            CartaoCredito cartaoCredito = locacao.getCartaoCredito();
            if (cartaoCredito != null) {
                sql.append(" id_cartao = ?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_locacao = ? ");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (locacao.getDataLocacao() != null) {
                stmt.setDate(index++, Date.valueOf(locacao.getDataLocacao()));
            }
            if (locacao.getDataDevolucao() != null) {
                stmt.setDate(index++, Date.valueOf(locacao.getDataDevolucao()));
            }
            if (locacao.getValorLocacao() != null) {
                stmt.setDouble(index++, locacao.getValorLocacao());
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
            if (cartaoCredito != null) {
                stmt.setInt(index++, cartaoCredito.getIdCartaoCredito());
            }
            stmt.setInt(index++, id);

            stmt.executeUpdate();

            return locacao;
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
    public List<Locacao> list() throws BancoDeDadosException {
        List<Locacao> locacoes = new ArrayList<>();
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
                Locacao locacao = getLocacaoFromResultset(res);
                locacoes.add(locacao);
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

    private Locacao getLocacaoFromResultset(ResultSet res) throws SQLException {
        Locacao locacao = new Locacao();
        locacao.setIdLocacao(res.getInt("id_locacao"));
        locacao.setDataLocacao(res.getDate("data_locacao").toLocalDate());
        locacao.setDataDevolucao(res.getDate("data_devolucao").toLocalDate());
        locacao.setValorLocacao(res.getDouble("valor_locacao_total"));
        locacao.setCliente(getClientFromResultSet(res));
        locacao.setVeiculo(getVeiculoFromResultSet(res));
        locacao.setFuncionario(getFuncionarioResultSet(res));
        locacao.setCartaoCredito(getFromResultSetCartaoCredito(res));
        return locacao;
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

    private CartaoCredito getFromResultSetCartaoCredito(ResultSet res) throws SQLException {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setIdCartaoCredito(res.getInt("id_cartao"));
        cartaoCredito.setNumero(res.getString("numero_cartao"));
        cartaoCredito.setBandeiraCartao(BandeiraCartao.valueOf(res.getString("bandeira_cartao")));
        cartaoCredito.setValidade(res.getString("validade"));
        cartaoCredito.setLimite(res.getDouble("limite"));
        return cartaoCredito;
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

    public Locacao findById(Integer chave) throws BancoDeDadosException {
        Locacao locacao = new Locacao();
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
                locacao.setIdLocacao(res.getInt("id_locacao"));
                locacao.setDataLocacao(res.getDate("data_locacao").toLocalDate());
                locacao.setDataDevolucao(res.getDate("data_devolucao").toLocalDate());
                locacao.setValorLocacao(res.getDouble("valor_locacao_total"));
                locacao.setCliente(getClientFromResultSet(res));
                locacao.setVeiculo(getVeiculoFromResultSet(res));
                locacao.setFuncionario(getFuncionarioResultSet(res));
                locacao.setCartaoCredito(getFromResultSetCartaoCredito(res));
            }

            return locacao;
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
