package com.example.abstractionizer.login.jwt3.db.rmdb.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.models.bo.UserUpdateBo;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    int countByUsername(@Param("username") String username);

    User getByIdOrUsername(@Param("user_id") Integer userId, @Param("username") String username);

    int updateLastLoginTime(@Param("username") String username);

    int updateUserInfo(@Param("user_id") Integer userId, @Param("user") UserUpdateBo user);

    int countByUserIdOrUsername(@Param("user_id") Integer userId, @Param("username") String username);

    UserInfoVo selectByIdOrUsername(@Param("user_id") Integer userId, @Param("username") String username);

    int updatePassword(@Param("user_id") Integer userId, @Param("password") String password);
}
