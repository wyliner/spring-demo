package com.wyl.demo.util;

import java.util.Base64;
import java.util.UUID;

public final class UUIDUtils {
    private UUIDUtils() {
    }

    /**
     * 获取不带横线的uuid eg:{@code 5792a7df495c4dbaa129bf8430b77054}
     *
     * @return 不带横线的uuid
     */
    public static String randomUUIDWithoutHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机生成16位的uuid eg:{@code 5792a7df495c4dba}
     *
     * @return 不带横线16位的uuid
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static String randomHalfUUIDWithoutHyphens() {
        return randomUUIDWithoutHyphens().substring(0, 16);
    }

    public static String randomShortUUID() {
        final UUID uuid = UUID.randomUUID();
        final byte[] uuidBytes = toByteArray(uuid);
        final byte[] encodedBytes = Base64.getUrlEncoder().withoutPadding().encode(uuidBytes);
        return new String(encodedBytes).replace("-", "");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static byte[] toByteArray(final UUID uuid) {
        final long mostSignificantBits = uuid.getMostSignificantBits();
        final long leastSignificantBits = uuid.getLeastSignificantBits();
        final byte[] byteArray = new byte[16];
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) (mostSignificantBits >>> 8 * (7 - i));
            byteArray[i + 8] = (byte) (leastSignificantBits >>> 8 * (7 - i));
        }
        return byteArray;
    }
}
