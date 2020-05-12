package com.votemine.votemineReward.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLQuery {

    private SQLStore storage;

    public SQLQuery(SQLStore storage){
     this.storage = storage;
    }

    public void exec(String query, QueryExecution executor) throws SQLException {
        Connection conn = storage.open();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        executor.exec(preparedStatement);
        preparedStatement.close();
        conn.close();
    }

}
