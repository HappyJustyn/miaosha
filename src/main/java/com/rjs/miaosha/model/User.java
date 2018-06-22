package com.rjs.miaosha.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "miaosha_user")
public class User {
    @Id
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(password+salt)+salt)--两次md5加密
     */
    private String password;

    private String salt;

    /**
     * 头像-云存储的ID
     */
    private String head;

    @Column(name = "register_date")
    private Date registerDate;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "login_count")
    private Integer loginCount;

}