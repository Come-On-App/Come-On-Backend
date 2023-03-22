package com.comeon.backend.user.command.domain;

public class NicknameUtils {

    private static String[] strs = {
            "귀여운",
            "잘먹는",
            "지지않는",
            "강한",
            "단단한",
            "맛있는",
            "상큼한",
            "일 잘하는",
            "황금",
            "사랑스런",
            "아름다운",
            "멋있는",
            "달콤한",
            "시원한",
            "진화된",
            "비싼",
            "사랑받는",
            "빛나는",
            "치명적인",
            "벗어날 수 없는",
            "헤어나올 수 없는",
            "모이고 싶은",
            "만나고 싶은",
            "보고싶은",
            "수줍은",
            "부끄러운",
            "패기넘치는",
            "돈 많은",
            "부유한",
            "코딩잘하는",
            "콩닥콩닥",
            "두근두근",
            "반짝반짝",
            "왕",
    };

    public static String randomNickname(OauthProvider provider) {
        int rand = (int) Math.round(Math.random() * (strs.length - 1));
        switch (provider) {
            case KAKAO:
                return strs[rand] + " " + "커피콩";
            case GOOGLE:
                return strs[rand] + " " + "구글";
            case APPLE:
                return strs[rand] + " " + "사과";
        }

        return null;
    }
}
