package com.master.DBHandler.Chache;

import com.master.DBHandler.ConfigLoader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CacheHandler implements AutoCloseable{
    JedisPool cacheJedisPool = new JedisPool();

    public CacheHandler(String filePath){

        CacheConfig config = ConfigLoader.loadCacheConfig(filePath);
        JedisPoolConfig jConfig = new JedisPoolConfig();

        jConfig.setMaxTotal(config.getMaxIdle());
        jConfig.setMaxIdle(config.getMaxIdle());
        jConfig.setMinIdle(config.getMinIdle());
        jConfig.setTestOnBorrow(config.isTestOnBorrow());
        jConfig.setTestOnReturn(config.isTestOnReturn());
        jConfig.setTestWhileIdle(config.isTestWhileIdle());
        jConfig.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());
        jConfig.setBlockWhenExhausted(config.isBlockWhenExhausted());

        if(config.isUseSSL()){
            cacheJedisPool = new JedisPool(jConfig,config.getHost(),config.getPort(),config.getConnectionTimeout(),config.getPassword(),true);
        }else if(config.getPassword()!=null && !config.getPassword().isBlank()){
            cacheJedisPool = new JedisPool(jConfig,config.getHost(),config.getPort(),config.getConnectionTimeout(),config.getPassword());
        }else{
            cacheJedisPool = new JedisPool(jConfig,config.getHost(),config.getPort(),config.getConnectionTimeout());
        }

        this.cacheJedisPool = new JedisPool(jConfig);
    }

    public Jedis getResource(){
        return cacheJedisPool.getResource();
    }

    public boolean isHealty(){
        try (Jedis jedis=getResource()){
            return "PING".equals(jedis.ping());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        cacheJedisPool.close();
    }

}
