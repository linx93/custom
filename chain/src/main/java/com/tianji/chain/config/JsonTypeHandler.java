package com.tianji.chain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author xionglin
 * @Classname JsonTypeHandler
 * @Description 自定义mybatis的类型处理器，这个处理器的作用就是处理给原生jdbc的预编译设置参数时调用处理JavaBean字段类型对应到数据库类型和
 * 返回result获取数据从数据库类型转换对应到JavaBean
 * @Date 2019/6/28 09:38
 */
@Slf4j
public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
    /**
     * mapper里json型字段到类的映射。
     * 用法一:
     * 入库：#{jsonDataField, typeHandler=com.adu.spring_test.mybatis.typehandler.JsonTypeHandler}
     * 出库：
     * <resultMap>
     * <result property="jsonDataField" column="json_data_field" javaType="com.xxx.MyClass" typeHandler="com.adu.spring_test.mybatis.typehandler.JsonTypeHandler"/>
     * </resultMap>
     * <p>
     * 用法二：
     * 1）在mybatis-config.xml中指定handler:
     * <typeHandlers>
     * <typeHandler handler="com.adu.spring_test.mybatis.typehandler.JsonTypeHandler" javaType="com.xxx.MyClass"/>
     * </typeHandlers>
     * 2)在MyClassMapper.xml里直接select/update/insert。
     */

    private static final ObjectMapper mapper = new ObjectMapper();

    private Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        super();
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    public JsonTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        //把泛型通过JackJson转为字符串存去数据库
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        //把数据库中的json字符串通过JackJson转为对应的泛型对象
        return this.toObject(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        //把数据库中的json字符串通过JackJson转为对应的泛型对象
        return this.toObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        //把数据库中的json字符串通过JackJson转为对应的泛型对象
        return this.toObject(cs.getString(columnIndex), clazz);
    }

    private String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private T toObject(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return (T) mapper.readValue(content, clazz);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("json converter error");
    }
}
