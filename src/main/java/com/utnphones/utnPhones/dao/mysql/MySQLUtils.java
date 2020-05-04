package com.utnphones.utnPhones.dao.mysql;

public class MySQLUtils {
    private static final String BASE_SELECT = "SELECT * FROM ";

    /* TABLES */
    protected static final String TABLE_PERSONS = "persons ";
    protected static final String TABLE_PHONE_LINES = "phone_lines ";
    protected static final String TABLE_CALLS = "calls ";
    protected static final String TABLE_LINE_TYPES = "line_types ";

    /* PERSONS QUERY */

    protected static final String GET_ALL_PERSONS = BASE_SELECT + TABLE_PERSONS;
    protected static final String GET_ALL_CLIENTS = GET_ALL_PERSONS + " where persons.id_user_type = 1";

    /* PHONE LINES QUERY */
    protected static final String GET_ALL_PHONE_LINES = BASE_SELECT + TABLE_PHONE_LINES;
    protected static final String GET_PHONE_LINE_BY_PERSON = GET_ALL_PHONE_LINES + " where "+ TABLE_PHONE_LINES + ".id_person = ?";
    protected static final String GET_PHONE_LINE_BY_ID = GET_ALL_PHONE_LINES + " where "+ TABLE_PHONE_LINES + ".id_phone_line = ?";

    /* CALLS QUERY */
    protected static final String GET_ALL_CALLS = BASE_SELECT + TABLE_CALLS;

    /* LINE TYPES QUERY */
    protected static final String GET_ALL_LINE_TYPES = BASE_SELECT + TABLE_LINE_TYPES;
}
