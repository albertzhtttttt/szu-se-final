package com.szu.weboj.dao;

import com.szu.weboj.pojo.UserType;
import com.szu.weboj.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * ClassName: BaseDao
 * Package: com.szu.weboj.dao
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:33
 * @Version 1.0.0
 */
public class BaseDao {
	// 公共的查询方法  返回的是单个对象
	public <T> T baseQueryObject(Class<T> clazz, String sql, Object... args) {
		T t = null;
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rows = 0;
		try {
			// 准备语句对象
			preparedStatement = connection.prepareStatement(sql);
			// 设置语句上的参数
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			// 执行 查询
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				t = (T) resultSet.getObject(1);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}

			}
			JDBCUtil.releaseConnection();
		}
		return t;
	}
	// 公共的查询方法  返回的是对象的集合

	public <T> List<T> baseQuery(Class clazz, String sql, Object... args) {
		List<T> list = new ArrayList<>();
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rows = 0;
		try {
			// 准备语句对象
			preparedStatement = connection.prepareStatement(sql);
			// 设置语句上的参数
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			// 执行查询
			resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 将结果集通过反射封装成实体类对象
			while (resultSet.next()) {
				// 使用反射实例化对象
				Object obj = clazz.getDeclaredConstructor().newInstance();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					Object value = resultSet.getObject(columnName);
					// 处理datetime类型字段和java.util.Data转换问题
					if (value.getClass().equals(LocalDateTime.class)) {
						value = Timestamp.valueOf((LocalDateTime) value);
					}
					// 处理blob类型字段和String转换问题
					else if (value instanceof byte[]) {
						// 将 byte[] 转为 Base64 字符串
						value = Base64.getEncoder().encodeToString((byte[]) value);
					}

					// 获取字段对象
					Field field = clazz.getDeclaredField(columnName);
					// 判断字段类型是否为 UserType，处理枚举类型转换
					if (field.getType().equals(UserType.class) && value instanceof String) {
						try {
							// 将字符串转为对应的 UserType 枚举值
							value = UserType.valueOf(((String) value));
						} catch (IllegalArgumentException e) {
							// 处理非法值的情况，输出错误日志或设置默认值
							System.out.println("Invalid value for UserType: " + value);
						}
					}
					field.setAccessible(true);
					field.set(obj, value);
				}

				list.add((T) obj);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			JDBCUtil.releaseConnection();
		}
		return list;
	}

	// 通用的增删改方法
	public int baseUpdate(String sql, Object... args) {
		// 获取连接
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement preparedStatement = null;
		int rows = 0;
		try {
			// 准备语句对象
			preparedStatement = connection.prepareStatement(sql);
			// 设置语句上的参数
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			// 执行 增删改 executeUpdate
			rows = preparedStatement.executeUpdate();
			// 释放资源(可选)


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}

			}
			JDBCUtil.releaseConnection();
		}
		// 返回的是影响数据库记录数
		return rows;
	}
}
