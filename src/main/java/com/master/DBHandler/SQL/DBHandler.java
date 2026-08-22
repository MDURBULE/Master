package com.master.DBHandler.SQL;

import java.sql.Connection;
import java.sql.SQLException;

import com.master.DBHandler.ConfigLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBHandler {

    HikariDataSource dataSource = new HikariDataSource();

  
    public DBHandler() {
    }

    public DBHandler(String configPath){

        DBConfig dbconfig = ConfigLoader.loadDbConfig(configPath);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbconfig.getJdbcUrl());
        config.setUsername(dbconfig.getUsername());
        config.setPassword(dbconfig.getPassword());

        config.setMaximumPoolSize(dbconfig.getMaximumPoolSize());
        config.setMinimumIdle(dbconfig.getMinimumIdle());
        config.setConnectionTimeout(dbconfig.getConnectionTimeout());
        config.setIdleTimeout(dbconfig.getIdleTimeout());
        config.setMaxLifetime(dbconfig.getMaxLifetime());
        config.setValidationTimeout(dbconfig.getValidationtime());
        
        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    };

    public boolean isHealty(){
       try (Connection connection = getConnection()){
         return true;
       } catch (Exception e) {
         return false;
       }
    };

    public void close(){
        dataSource.close();
    };
} 
