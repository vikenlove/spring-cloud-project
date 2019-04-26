package com.emerging.framework.core.redis;
/**
* @author:Faye.wang
* @version 创建时间：2018年12月4日 上午9:20:20
* RedisHelper UtilsClass
*/

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisUtil {

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;


	/**
	 * @author hyp
	 * 递减
	 * @param key 键
	 * @param delta 要减少几(小于0)
	 * @return
	 */
	public long decr(String key, long delta){
		if(delta<0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 设置key失效时间
	 *
	 * @param key
	 * @param liveTime
	 */
	public void setKeyLiveTime(final String key, final int liveTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.expire(key.getBytes(), liveTime);
				return 1L;
			}
		});
	}

	/**
	 * 通过正则表达式获取key
	 *
	 * @param pattern
	 * @return
	 */
	public List<String> getKeys(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys != null) {
			List<String> list = new ArrayList<String>();
			Iterator<Serializable> iterator = keys.iterator();
			while (iterator.hasNext()) {
				list.add((String) iterator.next());
			}
			return list;
		}
		return null;
	}

	/**
	 * 获取key的生命
	 *
	 * @param key
	 * @return
	 */
	public Long liveTime(final String key) {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				result = connection.ttl(key.getBytes());
				return result;
			}
		});
	}

	/**
	 * 获取redis长度
	 *
	 * @return
	 */
	public Long size() {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				result = connection.dbSize();
				return result;
			}
		});
	}

	/**
	 * 添加
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		final byte[] b_key = key.getBytes();
		byte[] b_value = null;
		try {
			b_value = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		set(b_key, b_value, -1);
	}

	/**
	 * 添加
	 *
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {

		final byte[] b_key = key.getBytes();
		byte[] b_value = null;
		try {
			b_value = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		set(b_key, b_value, liveTime);
	}

	private void set(final byte[] key, final byte[] value, final int liveTime) {

		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	/**
	 * 取值
	 *
	 * @param key
	 * @return
	 */
	public String get(final String key) {

		final byte[] b_key = key.getBytes();
		if (get(b_key) != null && get(b_key).length > 0) {
			try {
				return new String(get(b_key), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private byte[] get(final byte[] key) {

		return (byte[]) redisTemplate.execute(new RedisCallback<Object>() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key);
			}
		});
	}

	/**
	 * 删除
	 *
	 * @param keys
	 * @return
	 */
	public long del(final String... keys) {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	/**
	 * 删除全部
	 */
	public void delAll() {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushAll();
				return 1L;
			}
		});
	}

	/**
	 * 删除当前数据库
	 */
	public void delDB() {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return 1L;
			}
		});
	}

}
