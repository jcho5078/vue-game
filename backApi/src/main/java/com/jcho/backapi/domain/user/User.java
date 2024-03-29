package com.jcho.backapi.domain.user;

import com.jcho.backapi.util.BooleanConverter;
import com.jcho.backapi.web.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_user", indexes = {
        @Index(name = "idx_t_user_userNo", columnList = "USER_NO"),
        @Index(name = "idx_t_user_loginId_loginPw", columnList = "LOGIN_ID, LOGIN_PW")
})
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User {

    public User(String loginId, String loginPw, String userNm){
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.userNm = userNm;
    }

    public User(long userNo){
        this.userNo = userNo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO", nullable = false)
    private Long userNo;

    @Comment("로그인 ID")
    @Column(name = "LOGIN_ID", length = 50, unique = true)
    private String loginId;

    @Comment("로그인 PW")
    @Column(name = "LOGIN_PW", length = 100)
    private String loginPw;

    @Comment("사용자 이름")
    @Column(name = "USER_NM", length = 150, unique = true)
    private String userNm;

    @Comment("가입일")
    @Column(name = "REG_DT", length = 30)
    @CreatedDate
    private LocalDateTime regDt;

    @Comment("이전 로그인 날짜")
    @Column(name = "LAST_LOGIN_DT", length = 30)
    @CreatedDate
    private LocalDateTime lastLoginDt;

    @Comment("유효여부")
    @Column(name = "IS_VALID", length = 5)
    @Convert(converter = BooleanConverter.class)
    @ColumnDefault(value = "'Y'")
    private boolean isValid;

    /**
     * lastLoginDt 갱신
     * @param currentDateTime
     */
    public void updateLogin(LocalDateTime currentDateTime){
        this.lastLoginDt = currentDateTime;
    }

    /**
     * convert user entity
     * @param userDto
     * @return
     */
    public User toEntity(UserDto userDto){
        this.loginId = userDto.getLoginId();
        this.loginPw = userDto.getLoginPw();
        this.userNm = userDto.getUserNm();
        this.regDt = LocalDateTime.now();

        return this;
    }
}