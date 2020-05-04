package com.votemine.votemineReward.storage.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface QueryExecution {
    void exec(PreparedStatement statement) throws SQLException;
}
