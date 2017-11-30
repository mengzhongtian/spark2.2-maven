package dao.Impl;

import dao.AggrDao;
import domain.UserBehavior;
import jdbc.JDBCHelper;

public class AggrDaoImpl  implements AggrDao{
    @Override
    public void insert(UserBehavior userBehavior) {
        JDBCHelper instance = JDBCHelper.getInstance();
        String sql = "insert into user_behavior "
                + "values(?,?,?,?,?,?,?,?,?)";
        Object[] param = new Object[]{
                userBehavior.getUser_id()
                , userBehavior.getAct_obj()
                , userBehavior.getObj_type()
                , userBehavior.getBhv_type()
                , userBehavior.getBhv_cnt()
                , userBehavior.getBhv_datetime()
                , userBehavior.getContent()
                , userBehavior.getTrace_id()
        };
        instance.executeUpdate(sql, param);
    }
}
