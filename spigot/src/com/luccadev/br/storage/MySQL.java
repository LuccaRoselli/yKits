package com.luccadev.br.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.luccadev.br.storage.exception.MySQLException;

public class MySQL {

	private Connection connection;

	private ResultSet rs = null;
	private Statement stm = null;
	private PreparedStatement ps = null;

	private String url;
	private String user;
	private String passowrd;

	public MySQL(String ip, Integer port, String database, String user, String password) throws MySQLException {
		this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
		this.user = user;
		this.passowrd = password;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			throw new MySQLException("ClassNotFound", "Erro mySQL nao conectado");
		} catch (SQLException e) {
			throw new MySQLException("SQLException", "Erro mySQL nao conectado");
		}
	}

	public Statement getStatement() {
		try {
			return getConnection().createStatement();
		} catch (SQLException e) {
			return null;
		}
	}

	public void connect() throws MySQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, passowrd);
		} catch (ClassNotFoundException e) {
			throw new MySQLException("ClassNotFound", "Erro mySQL nao conectado");
		} catch (SQLException e) {
			throw new MySQLException("SQLException", "Erro mySQL nao conectado");
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() throws SQLException {
		if (!connection.isClosed())
			connection.close();
		if (!ps.isClosed())
			ps.close();
		if (!stm.isClosed())
			stm.close();
		if (!rs.isClosed())
			rs.close();
	}

	public void preparedStatement(String query) {
		try {
			if (getConnection().isClosed()) {
				try {
					connect();
				} catch (MySQLException e) {
					e.printStackTrace();
				}
			}

			ps = connection.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeStatement(String query) {
		try {
			if (getConnection().isClosed()) {
				try {
					connect();
				} catch (MySQLException e) {
					e.printStackTrace();
				}
			}
			stm = connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object resultSet(String sql, String coluna) {
		try {
			if (getConnection().isClosed()) {
				try {
					connect();
				} catch (MySQLException e) {
					e.printStackTrace();
				}
			}
			rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				return rs.getObject(coluna);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean resultSetBoolean(String sql) {
		try {
			if (getConnection().isClosed()) {
				try {
					connect();
				} catch (MySQLException e) {
					e.printStackTrace();
				}
			}

			rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
