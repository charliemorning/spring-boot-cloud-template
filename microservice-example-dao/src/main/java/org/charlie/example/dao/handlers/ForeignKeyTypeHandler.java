package org.charlie.example.dao.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.charlie.example.po.BasePo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForeignKeyTypeHandler extends BaseTypeHandler<BasePo> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BasePo basePo, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, basePo.getId());
    }

    @Override
    public BasePo getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public BasePo getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public BasePo getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
