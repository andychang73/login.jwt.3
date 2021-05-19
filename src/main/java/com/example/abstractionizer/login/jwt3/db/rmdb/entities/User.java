package com.example.abstractionizer.login.jwt3.db.rmdb.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Date lastLoginTime;

    private Integer status;

    public User setId(Integer id){
        this.id = id;
        return this;
    }

    public User setUsername(String username){
        this.username = username;
        return this;
    }
}
