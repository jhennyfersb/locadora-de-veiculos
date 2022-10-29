package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Veiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VeiculoRepository implements Repositorio<Integer, Veiculo> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_VEICULO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public Veiculo create(Veiculo veiculo) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            veiculo.setIdVeiculo(proximoId);

            String sql = "INSERT INTO VEICULO\n" +
                    "(id_veiculo, marca, modelo, cor, ano, quilometragem, valor_locacao_diario, disponibilidade, placa)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, veiculo.getIdVeiculo());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setString(4, veiculo.getCor());
            stmt.setInt(5, veiculo.getAno());
            stmt.setDouble(6, veiculo.getQuilometragem());
            stmt.setDouble(7, veiculo.getValorLocacao());
            stmt.setString(8, veiculo.getDisponibilidadeVeiculo().toString());
            stmt.setString(9, veiculo.getPlaca());

            stmt.executeUpdate();

            return veiculo;
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

    public Veiculo update(Integer id, Veiculo veiculo) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE VEICULO SET");
            sql.append(" marca = ?,");
            sql.append(" modelo = ?,");
            sql.append(" cor = ?,");
            sql.append(" ano = ?,");
            sql.append(" quilometragem = ?,");
            sql.append(" valor_locacao_diario = ?,");
            sql.append(" disponibilidade = ?,");
            sql.append(" placa = ?");
            sql.append(" WHERE id_veiculo = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            veiculo.setIdVeiculo(id);
            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setInt(4, veiculo.getAno());
            stmt.setDouble(5, veiculo.getQuilometragem());
            stmt.setDouble(6, veiculo.getValorLocacao());
            stmt.setString(7, veiculo.getDisponibilidadeVeiculo().toString());
            stmt.setString(8, veiculo.getPlaca());
            stmt.setInt(9, id);

            stmt.executeUpdate();

            return veiculo;
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

    public boolean delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM VEICULO WHERE id_veiculo = ?";

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

    public List<Veiculo> list() throws BancoDeDadosException {
        List<Veiculo> veiculos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM VEICULO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
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
                veiculos.add(veiculo);
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
        return veiculos;
    }

    public Veiculo findById(Integer chave) throws BancoDeDadosException {
        Veiculo veiculo = new Veiculo();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM VEICULO\n" +
                    "WHERE ID_VEICULO = ?";

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1,chave);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                veiculo.setIdVeiculo(res.getInt("id_veiculo"));
                veiculo.setMarca(res.getString("marca"));
                veiculo.setModelo(res.getString("modelo"));
                veiculo.setCor(res.getString("cor"));
                veiculo.setAno(res.getInt("ano"));
                veiculo.setQuilometragem(res.getDouble("quilometragem"));
                veiculo.setValorLocacao(res.getDouble("valor_locacao_diario"));
                veiculo.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.valueOf(res.getString("disponibilidade")));
                veiculo.setPlaca(res.getString("placa"));
            }
            return veiculo;
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

    public List<Veiculo> listarVeiculosDisponiveis() throws BancoDeDadosException {
        List<Veiculo> veiculos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM VEICULO WHERE DISPONIBILIDADE = 'DISPONIVEL'";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
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
                veiculos.add(veiculo);
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
        return veiculos;
    }
    private Veiculo getVeiculoFRomResultSEt(ResultSet res) {
        return null;
    }
}
