package com.dju.linkup.domain.post.model;

public enum PostTopic {
    DAILY_LIFE,     // 일상
    CAREER,         // 커리어
    FINANCE,        // 재테크
    REAL_ESTATE,    // 부동산
    HEALTH,         // 건강
    TRAVEL,         // 여행
    HOBBIES,        // 취미
    ROMANCE;        // 연애

    public static PostTopic fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Topic Value cannot be null or empty");
        }

        try {
            return PostTopic.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Topic " + value);
        }
    }
}
