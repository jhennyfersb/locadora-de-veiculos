package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class ConexaoBancoDeDados {
//    private static final String SERVER = "localhost";
//    private static final String PORT = "1521"; // Porta TCP padrão do Oracle
//    private static final String DATABASE = "xe";
//
//    // Configuração dos parâmetros de autenticação
//    private static final String USER = "system";
//    private static final String PASS = "oracle";
//
//    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
//
//        // Abre-se a conexão com o Banco de Dados
//        Connection con = DriverManager.getConnection(url, USER, PASS);
//
//        // sempre usar o schema vem_ser
//        con.createStatement().execute("alter session set current_schema=ALUGUEL_DE_CARROS");
//
//        return con;
//    }
@Service
public class ConexaoBancoDeDados {

    @Value("${jdbc-string}")
    private String jdbcString;

    @Value("${jdbc-user}")
    private String user;

    @Value("${jdbc-pass}")
    private String pass;

    @Value("${jdbc-schema}")
    private String schema;

    public Connection getConnection() throws SQLException {
        //  String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
        // jdbc:oracle:thin:@localhost:1521:xe
        Connection con = DriverManager.getConnection(jdbcString, user, pass);

        con.createStatement().execute("alter session set current_schema=" + schema);

        return con;
    }
}


