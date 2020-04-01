package de.n1ck145.lobbySystem.MySQL;

import de.n1ck145.lobbySystem.main.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class API_MySQL {
    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;

    public static void connect() {
        if (!isConnected())
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§aMySQL connencted!");
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL connect Error!");
            }
    }

    public static void disconnect() {
        if (isConnected())
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL disconnet error!");
            }
    }

    public static boolean isConnected() {
        return !(con == null);
    }
    
    public static void update(String querry) {
        try {
            PreparedStatement ps = con.prepareStatement(querry);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL update error!");
        }
    }

    public static ResultSet getResult(String querry) {
        try {
            PreparedStatement ps = con.prepareStatement(querry);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.getMain().getPrefix()) + "§4MySQL get-result error!");

            return null;
        }
    }
}