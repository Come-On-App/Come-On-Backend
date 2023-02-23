package com.comeon.backend.meeting.infrastructure.mapper;

import com.comeon.backend.meeting.command.domain.MemberRole;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(MemberRole.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MemberRoleTypeHandler implements TypeHandler<MemberRole> {

    @Override
    public void setParameter(PreparedStatement ps, int i, MemberRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public MemberRole getResult(ResultSet rs, String columnName) throws SQLException {
        String codeName = rs.getString(columnName);
        return getEnum(codeName);
    }

    @Override
    public MemberRole getResult(ResultSet rs, int columnIndex) throws SQLException {
        String codeName = rs.getString(columnIndex);
        return getEnum(codeName);
    }

    @Override
    public MemberRole getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String codeName = cs.getString(columnIndex);
        return getEnum(codeName);
    }

    private MemberRole getEnum(String codeName) {
        return MemberRole.of(codeName).orElseThrow(() -> new MemberRoleTypeMismatchException(codeName));
    }
}
