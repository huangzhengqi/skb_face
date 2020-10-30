package com.fzy.admin.fp.member.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum MemberConsumeActivityLevelEnum {
    ONE("消費1次", times -> times == 1),
    TWO_TO_FOUR("消費2-4次", times -> 2 <= times && 4 >= times),
    FIVE_TO_NINE("消費5-9次", times -> 5 <= times && 9 >= times),
    MORE_THAN_TEN("10次以上", times -> 10 <= times),
    ;
    String description;
    Predicate<Long> judge;
}
