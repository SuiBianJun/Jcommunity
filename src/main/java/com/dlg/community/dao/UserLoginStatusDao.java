package com.dlg.community.dao;


import com.dlg.community.pojo.UserLoginStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginStatusDao extends JpaRepository<UserLoginStatus, Integer> {

    @Query("select u from UserLoginStatus u where u.user_token=:userToken")
    UserLoginStatus selByToken(@Param("userToken") String userToken);

    @Query(nativeQuery = true, value = "select * from t_user_auto_login where user_id=:id limit 0, 1")
    UserLoginStatus selUserById(@Param("id") Integer id);

}
