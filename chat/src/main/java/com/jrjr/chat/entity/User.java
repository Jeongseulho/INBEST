package com.jrjr.chat.entity;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String birthyear;

    private String birthday;

    @ColumnDefault("0")
    private Integer gender;

    private String profileImgSearchName;

    private String profileImgOriginalName;

    private LocalDateTime deletedDate;

    @Builder
    public User(String email, String name, String nickname, String birthyear, String birthday, Integer gender,
                String profileImgSearchName, String profileImgOriginalName) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthyear = birthyear;
        this.birthday = birthday;
        this.gender = gender;
        this.profileImgSearchName = profileImgSearchName;
        this.profileImgOriginalName = profileImgOriginalName;
    }


}
