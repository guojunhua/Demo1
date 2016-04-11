package com.lzl_rjkx.doctor.utils;

/**
 * Created by lzl_os on 16/2/22.
 */
public class DbDaoUtils {

//    private DbManager db;
//
//    public DbDaoUtils(DbManager db) {
//        this.db = db;
//    }
//
//    public boolean add(CollectDao dao) {
//        try {
//            db.save(dao);
//            return true;
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 增加或者是更新
//     *
//     * @param dao
//     */
//    public void addOrUpdate(CollectDao dao) {
//        try {
//            db.saveOrUpdate(dao);//这个方法 如果不存在就添加，存在就更新 将原始的值更新为传递的对象上面的新值，判断存在的标准就是主键是否存在，不允许使用自增主键
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(CollectDao dao) {
//        try {
//            db.delete(dao);//从数据库删除指定的对象对应行，必须手动指定唯一主键，判断标准以主键为准
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteById(Object object) {
//        try {
//            db.deleteById(CollectDao.class, object);//根据主键删除对应的对象，第一个参数指定是表名
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteAll() {
//        try {
//            db.delete(CollectDao.class);//根据类文件删除表中所有数据，但是表还存在
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteByWhere(String value) {
//        try {
//            db.delete(CollectDao.class, WhereBuilder.b("id", "=", value));
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void update(CollectDao dao) {
//        try {
//            db.update(dao, new String[]{"id", "icon", "title", "author", "pubTime"});//更新数据
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public void updateByWhere(CollectDao dao) {
////        try {
////            //根据一个查询的条件去更新对应的字段名
////            db.update(dao, WhereBuilder.b("id", "=", "1"), new String[]{"id", "icon", "title", "author", "pubTime"});
////        } catch (DbException e) {
////            e.printStackTrace();
////        }
////    }
//
//    /**
//     * @return 查询所有的数据
//     */
//    public List<CollectDao> query() {
//        try {
//            return db.findAll(CollectDao.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * @param id 主键的值
//     * @return
//     */
//    public CollectDao findById(Object id) {
//        try {
//            return db.findById(CollectDao.class, id);//根据主键的值查询返回对应的对象
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 根据sql语句查询
//     *
//     * @param sql select * from collectDao where id = xxx;
//     * @return
//     */
//    public List<DbModel> findBySql(String sql) {
//        try {
//            return db.findDbModelAll(new SqlInfo(sql));//根据sql语句查询
//            //db.dropDb();//删除数据库文件
//            //db.dropTable(CollectDao.class);//删除某张表
//            //db.addColumn(CollectDao.class, "content");//更新某张表，增加字段，第二个参数就是增加的字段名
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


}
