package com.example.thy.defination;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

/*
 * CustomIntegerArrayType added for mapping integer[] to database table
 * */
public class CustomIntegerArrayType implements UserType<Integer[]> {

    @Override
    public int getSqlType() {
        return 0;
    }

    @Override
    public Class returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Integer[] integers, Integer[] j1) {
        return Arrays.equals(integers, j1);
    }

    @Override
    public int hashCode(Integer[] integers) {
        return Arrays.hashCode(integers);
    }

    /*
     * get
     * */
    @Override
    public Integer[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session,
                                 Object owner) throws SQLException {
        Array array = rs.getArray(position);
        return array != null ? (Integer[]) array.getArray() : null;
    }

    /*
     * set value
     * */
    @Override
    public void nullSafeSet(PreparedStatement st, Integer[] value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (st != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection().createArrayOf("int", value);
                st.setArray(index, array);
            } else {
                st.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public Integer[] deepCopy(Integer[] value) {
        return (value == null) ? null : Arrays.copyOf(value, value.length);
    }


    @Override
    public boolean isMutable() {
        return false;
    }


    @Override
    public Serializable disassemble(Integer[] value) {
        return deepCopy(value);
    }

    @Override
    public Integer[] assemble(Serializable cached, Object owner) {
        return deepCopy((Integer[]) cached);
    }

}