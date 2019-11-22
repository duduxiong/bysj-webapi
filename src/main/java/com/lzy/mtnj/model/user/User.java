package com.lzy.mtnj.model.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lzy.mtnj.config.Constants;
import com.lzy.mtnj.infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name="mtnj_user")
public class User extends BaseEntity {
  private String userAccount;
  private String password;
  private String userName;
  private String avatar;
  @Column(name = "sex")
  private Integer sex;
  @JsonFormat(pattern = Constants.DEFAULT_DATE_FORMAT)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private String tel;
  private String cellPhone;
  private String email;
  @Column(name = "status")
  private Boolean status;
  @Column(name = "superAdmin")
  private Integer superAdmin;
  private String addUser;
  @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_FORMAT)
  private LocalDateTime addDate;
  private String updateUser;
  @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_FORMAT)
  private LocalDateTime updateDate;
  @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_FORMAT)
  private LocalDateTime lastLoginDate;
  private String lastLoginArea;
  private String parentId;
  private String parentName;

  @Transient
  private List<String> roleList;
}
