package com.jiajun.demo.database;

import android.content.Context;

import com.jiajun.demo.greendao.db.DaoMaster;
import com.jiajun.demo.greendao.db.DaoSession;
import com.jiajun.demo.greendao.db.TestEntityDao;
import com.jiajun.demo.greendao.db.TestEntityDao;
import com.jiajun.demo.model.entities.TestEntity;
import com.jiajun.demo.model.entities.TestEntity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


public class TestDao {
    private DBManager mDBManager;

    public TestDao(Context context) {
        mDBManager = DBManager.getInstance(context);
    }

    //增删改查---------------------------------------

    /**
     * 插入一条
     *
     * @param testEntity
     */
    public void insertTestEntities(TestEntity testEntity){
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao testEntityDao = daoSession.getTestEntityDao();
        testEntityDao.insert(testEntity);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertTestEntityList(List<TestEntity> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        userDao.insertOrReplaceInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteTestEntity(TestEntity user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        userDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllTestEntity(){
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao categoryEntityDao = daoSession.getTestEntityDao();
        categoryEntityDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateTestEntity(TestEntity user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        userDao.update(user);
    }

    /**
     * 更新多条记录
     *
     * @param user
     */
    public void updateAllTestEntity(List<TestEntity> user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        userDao.updateInTx(user);
    }

    /**
     * 查询用户列表
     */
    public List<TestEntity> queryTestEntityList() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        QueryBuilder<TestEntity> qb = userDao.queryBuilder();
        List<TestEntity> list = qb.orderAsc(TestEntityDao.Properties.Id).list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<TestEntity> queryTestEntityList(String name) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        QueryBuilder<TestEntity> qb = userDao.queryBuilder();
        List<TestEntity> list = qb.where(TestEntityDao.Properties.Name.eq(name))
                .orderAsc(TestEntityDao.Properties.Id).list();
        return list;
    }


    /**
     * 查询 某一个名称的 的位置
     */
    public long queryMoreTestEntity(String name) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        QueryBuilder<TestEntity> qb = userDao.queryBuilder();
        TestEntity TestEntity = qb.where(TestEntityDao.Properties.Name.eq(name)).uniqueOrThrow();
        return TestEntity.getId();
    }


    /**
     * 更新 某一个名称 一条记录的位置为第六个
     *
     *
     */
    public void updateMoreTestEntity(String name) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TestEntityDao userDao = daoSession.getTestEntityDao();
        QueryBuilder<TestEntity> qb = userDao.queryBuilder();
        TestEntity TestEntity = qb.where(TestEntityDao.Properties.Name.eq(name)).uniqueOrThrow();
        TestEntity.setId(6);
        userDao.update(TestEntity);
    }
}
