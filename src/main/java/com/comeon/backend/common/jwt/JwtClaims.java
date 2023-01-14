package com.comeon.backend.common.jwt;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JwtClaims implements Map<String, Object> {

    public static final String ISSUER = "iss";
    public static final String SUBJECT = "sub";
    public static final String EXPIRATION = "exp";
    public static final String ISSUED_AT = "iat";

    public static final String USER_ID = "uid";
    public static final String AUTHORITIES = "aut";
    public static final String NICKNAME = "nik";

    // 중복 허용 X
    private final Map<String, Object> payload;

    public JwtClaims() {
        this.payload = new LinkedHashMap<>();
    }

    public JwtClaims(Map<String, Object> map) {
        this.payload = map;
    }

    @Override
    public int size() {
        return payload.size();
    }

    @Override
    public boolean isEmpty() {
        return payload.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return payload.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return payload.containsValue(o);
    }

    @Override
    public Object get(Object o) {
        return payload.get(o);
    }

    @Override
    public Object put(String s, Object o) {
        if (o == null) {
            return payload.remove(s);
        } else {
            return payload.put(s, o);
        }
    }

    @Override
    public Object remove(Object o) {
        return payload.remove(o);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        if (m == null) {
            return;
        }
        for (String s : m.keySet()) {
            put(s, m.get(s));
        }
    }

    @Override
    public void clear() {
        payload.clear();
    }

    @Override
    public Set<String> keySet() {
        return payload.keySet();
    }

    @Override
    public Collection<Object> values() {
        return payload.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return payload.entrySet();
    }

    @Override
    public String toString() {
        return payload.toString();
    }

    @Override
    public int hashCode() {
        return payload.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return payload.equals(obj);
    }

    public String getIssuer() {
        return this.get(ISSUER, String.class);
    }

    public void setIssuer(String issuer) {
        payload.put(ISSUER, issuer);
    }

    public String getSubject() {
        return this.get(SUBJECT, String.class);
    }

    public void setSubject(String subject) {
        payload.put(SUBJECT, subject);
    }

    public Instant getExpiration() {
        return this.get(EXPIRATION, Instant.class);
    }

    public void setExpiration(Instant expiration) {
        payload.put(EXPIRATION, Date.from(expiration));
    }

    public Instant getIssuedAt() {
        return this.get(ISSUED_AT, Instant.class);
    }

    public void setIssuedAt(Instant issuedAt) {
        payload.put(ISSUED_AT, Date.from(issuedAt));
    }

    public Long getUserId() {
        return this.get(USER_ID, Long.class);
    }

    public void setUserId(Long userId) {
        payload.put(USER_ID, userId);
    }

    public String getAuthorities() {
        return this.get(AUTHORITIES, String.class);
    }

    public void setAuthorities(String authorities) {
        payload.put(AUTHORITIES, authorities);
    }

    public String getNickname() {
        return this.get(NICKNAME, String.class);
    }

    public void setNickname(String nickname) {
        payload.put(NICKNAME, nickname);
    }

    public <T> T get(String claimName, Class<T> requiredType) {
        Object value = get(claimName);
        if (value == null) {
            return null;
        }

        if (Instant.class.equals(requiredType) || Date.class.equals(requiredType)) {
            if (EXPIRATION.equals(claimName) || ISSUED_AT.equals(claimName)) {
                value = toMilliSecNumber(value);
            }
        }

        if (Date.class.equals(requiredType)) {
            value = toDate(value, claimName);
        } else if (Instant.class.equals(requiredType)) {
            value = toInstant(value, claimName);
        }

        return castClaimValue(value, requiredType);
    }

    private Object toMilliSecNumber(Object obj) {
        if (obj instanceof Number) {
            long seconds = ((Number) obj).longValue();
            obj = seconds * 1000;
        } else if (obj instanceof String) {
            long seconds = Long.parseLong((String) obj);
            obj = seconds * 1000;
        }
        return obj;
    }

    private Instant toInstant(Object obj, String claimName) {
        if (obj instanceof Instant) {
            return (Instant) obj;
        } else if (obj instanceof Date) {
            return ((Date) obj).toInstant();
        } else if (obj instanceof Calendar) {
            return ((Calendar) obj).toInstant();
        } else if (obj instanceof Number) {
            long millis = ((Number) obj).longValue();
            return Instant.ofEpochMilli(millis);
        } else if (obj instanceof String) {
            return Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String) obj));
        } else {
            throw new IllegalStateException("Cannot create Date from '" + claimName + "' value '" + obj + "'.");
        }
    }

    private Date toDate(Object v, String name) {
        if (v == null) {
            return null;
        } else if (v instanceof Date) {
            return (Date) v;
        } else if (v instanceof Calendar) {
            return ((Calendar) v).getTime();
        } else if (v instanceof Number) {
            long millis = ((Number) v).longValue();
            return new Date(millis);
        } else {
            throw new IllegalStateException("Cannot create Date from '" + name + "' value '" + v + "'.");
        }
    }

    private <T> T castClaimValue(Object value, Class<T> requiredType) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            if (requiredType == Long.class) {
                value = (long) intValue;
            } else if (requiredType == Short.class && intValue >= Short.MIN_VALUE && intValue <= Short.MAX_VALUE) {
                value = (short) intValue;
            } else if (requiredType == Byte.class && intValue >= Byte.MIN_VALUE && intValue <= Byte.MAX_VALUE) {
                value = (byte) intValue;
            }
        }

        if (!requiredType.isInstance(value)) {
            // TODO 예외 처리
            throw new RuntimeException();
        }

        return requiredType.cast(value);
    }
}
