package com.gyastreb.testproject.db;

import com.gyastreb.testproject.models.Main;
import com.gyastreb.testproject.models.Result;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyastreb on 9/19/16.
 */
public class ResultDAO extends BaseDaoImpl<Result, Integer> {

    private static ResultDAO instance;

    public static ResultDAO getInstance() throws SQLException {
        DBController databaseManager = DBFactory.getManager();
        if (instance == null || instance.getConnectionSource() != databaseManager.getConnectionSource()) {
            instance = new ResultDAO(databaseManager.getConnectionSource());
        }

        return instance;
    }

    public ResultDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Result.class);
    }

    public List<Result> get() throws SQLException {
        initialize();

        QueryBuilder<Result, Integer> builder = ResultDAO.getInstance().queryBuilder();
        builder.limit(5);
        builder.orderBy("name", true);

        List<Result> results = query(builder.prepare());
        return (results.size() != 0) ? results : null;
    }

    public Result save(Result result) throws SQLException {
        createOrUpdate(result);
        return result;
    }
}
