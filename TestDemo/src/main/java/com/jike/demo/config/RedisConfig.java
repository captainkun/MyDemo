package com.jike.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@PropertySource("classpath:dev/application-profile-redis.properties")
public class RedisConfig {

	@Value("${redis.hostName}")
	private String hostName;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.maxIdle}")
	private Integer maxIdle;

	@Value("${redis.maxTotal}")
	private Integer maxTotal;

	@Value("${redis.maxWaitMillis}")
	private Integer maxWaitMillis;

	@Value("${redis.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;

	@Value("${redis.numTestsPerEvictionRun}")
	private Integer numTestsPerEvictionRun;

	@Value("${redis.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${redis.testWhileIdle}")
	private boolean testWhileIdle;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲数
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 连接池的最大数据库连接数
		jedisPoolConfig.setMaxTotal(maxTotal);
		// 最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		// 在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory JedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//		JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
//		// 连接池
//		JedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//		// IP地址
//		JedisConnectionFactory.setHostName(this.hostName);
//		// 端口号
//		JedisConnectionFactory.setPort(this.port);
//		// 如果Redis设置有密码
//		if (StringUtils.isNotBlank(password)) {
//			JedisConnectionFactory.setPassword(password);
//		}
//		// 客户端超时时间单位是毫秒
//		JedisConnectionFactory.setTimeout(5000);
//		return JedisConnectionFactory;

		//单机版jedis
		RedisStandaloneConfiguration redisStandaloneConfiguration =
				new RedisStandaloneConfiguration();
		//设置redis服务器的host或者ip地址
		redisStandaloneConfiguration.setHostName(this.hostName);
		//设置默认使用的数据库
		redisStandaloneConfiguration.setDatabase(0);
		//设置密码

		if (StringUtils.isNotBlank(password)) {
			redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
		}
		//设置redis的服务的端口号
		redisStandaloneConfiguration.setPort(this.port);
		//获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
		//指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
		jpcb.poolConfig(jedisPoolConfig);
		//通过构造器来构造jedis客户端配置
		JedisClientConfiguration jedisClientConfiguration = jpcb.build();
		//单机配置 + 客户端配置 = jedis连接工厂
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
		return redisTemplate;
	}

	private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
		// 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
		// 默认情况下，RedisTemplate 使用 JdkSerializationRedisSerializer，也就是 JDK 序列化，容易产生 Redis 中保存了乱码的错觉。
		// 通常考虑到易读性，可以设置 Key 的序列化器为 StringRedisSerializer。
		// 但直接使用 RedisSerializer.string()，相当于使用了 UTF_8 编码的 StringRedisSerializer，需要注意字符集问题。
		// 如果希望 Value 也是使用 JSON 序列化的话，可以把 Value 序列化器设置为 Jackson2JsonRedisSerializer。
		// 默认情况下，不会把类型信息保存在 Value 中，
		// 即使我们定义 RedisTemplate 的 Value 泛型为实际类型，查询出的 Value 也只能是 LinkedHashMap 类型。
		// 如果希望直接获取真实的数据类型，
		// 可以启用 Jackson ObjectMapper 的 activateDefaultTyping 方法，把类型信息一起序列化保存在 Value 中。
		// 如果希望 Value 以 JSON 保存并带上类型信息，
		// 更简单的方式是，直接使用 RedisSerializer.json() 快捷方法来获取序列化器。
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 开启事务
//		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(factory);
	}

}
