package user.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserInfoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // how many records to insert per batch
    private final int INSERT_BATCH_SIZE = 100;
    private final String SQL_INSERT_USERINFO = "INSERT INTO t_user_info " +
            "(f_id,f_username,f_password,f_gender,f_ssn,f_age,f_status,f_created_at,f_updated_at) " +
            "values(?,?,?,?,?,?,?,?,?)";

    public void batchInsert(List<UserInfo> userInfos) {
        int listSize = userInfos.size();
        for (int i = 0; i < listSize; i += INSERT_BATCH_SIZE) {
            final List<UserInfo> batchList = userInfos.subList(i, i+INSERT_BATCH_SIZE > listSize ? listSize : i+INSERT_BATCH_SIZE);

            jdbcTemplate.batchUpdate(SQL_INSERT_USERINFO, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement pStmt, int j) throws SQLException {
                    UserInfo userInfo = batchList.get(j);
                    pStmt.setInt(1, userInfo.getId());
                    pStmt.setString(2, userInfo.getUsername());
                    pStmt.setString(3, userInfo.getPassword());
                    pStmt.setInt(4, userInfo.getGender());
                    pStmt.setString(5, userInfo.getSsn());
                    pStmt.setInt(6, userInfo.getAge());
                    pStmt.setInt(7, userInfo.getStatus());
                    pStmt.setLong(8, userInfo.getCreatedAt());
                    pStmt.setLong(9, userInfo.getUpdatedAt());
                }

                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
            });

        }
    }
}
