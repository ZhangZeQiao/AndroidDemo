package com.zzq.demo.done2function.sqlite.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// TODO Dao 必须是接口或者抽象类，Room 使用注解自动生成访问数据库的代码
@Dao
public interface UserDao {

    @Insert
    void insertUser(User users);
    // TODO 如果插入的数据在数据库表中已经存在，就会抛出异常

    @Insert
    void insertUsers(List<User> users);

    @Insert
    void insertUsers(User... users);
    // 如果 @Insert 方法只接收 1 个参数，则它可以返回 long，这是插入项的新 rowId。如果参数是数组或集合，则应返回 long[] 或 List<Long>。

    @Delete
    void delete(User user);
    // TODO 如果通过 Entity 来删除数据，传进来的参数需要包含主键

    @Delete
    void deleteUsers(User... users);
    // 虽然通常没有必要，但是您可以让此方法返回一个 int 值，以指示从数据库中删除的行数。

    @Update
    void updateUser(User users);
    // TODO 如果通过 Entity 来更新数据，传进来的参数需要包含主键，参数会覆盖旧数据，参数中没有值的字段将置为 null

    @Update
    void updateUsers(User... users);
    // 虽然通常没有必要，但是您可以让此方法返回一个 int 值，以指示数据库中更新的行数

    /*
    @Query 是 DAO 类中使用的主要注释。它允许您对数据库执行读/写操作。
    每个 @Query 方法都会在编译时进行验证，因此如果查询出现问题，则会发生编译错误，而不是运行时失败。
    Room 还会验证查询的返回值，以确保当返回的对象中的字段名称与查询响应中的对应列名称不匹配时，Room 可以通过以下两种方式之一提醒您：
    1、如果只有部分字段名称匹配，则会发出警告。
    2、如果没有任何字段名称匹配，则会发出错误。
    */

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE uid=(:userId)")
    User findUserById(int userId);

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    User findUserByName(String first, String last);

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last")
    List<User> loadAllByName(String first, String last);

    /*
    返回列的子集：
    大多数情况下，您只需获取实体的几个字段。
    例如，您的界面可能仅显示用户的名字和姓氏，而不是用户的每一条详细信息。
    通过仅提取应用界面中显示的列，您可以节省宝贵的资源，并且您的查询也能更快完成。
    借助 Room，您可以从查询中返回任何基于 Java 的对象，前提是结果列集合会映射到返回的对象。
    例如，您可以创建以下基于 Java 的普通对象 (POJO) 来获取用户的名字和姓氏。
    Room 知道该查询会返回 first_name 和 last_name 列的值，并且这些值会映射到 NameTuple 类的字段中。因此，Room 可以生成正确的代码。
    如果查询返回的列过多，或者返回 NameTuple 类中不存在的列，则 Room 会显示一条警告。
    */
    @Query("SELECT first_name, last_name FROM users")
    List<NameTuple> loadFullName();

}
