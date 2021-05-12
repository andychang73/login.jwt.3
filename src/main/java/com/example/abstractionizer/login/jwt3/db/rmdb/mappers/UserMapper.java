package com.example.abstractionizer.login.jwt3.db.rmdb.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}