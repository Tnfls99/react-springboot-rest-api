package com.prgrms.clone.cloneproject.product.domain.util;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Color {
    BLACK(),
    IVORY();

    public static Color findColor(String input){
        if(isNotContain(input)){
            throw new IllegalArgumentException(
                    MessageFormat.format("해당하는 컬러를 찾을 수 없습니다. 올바른 값을 전달하세요. [전달된 값 : {0}]", input));
        }
        return Color.valueOf(input);
    }

    public static List<String> getAllColors(){
        return Arrays.stream(Color.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private static boolean isNotContain(String input){
        return !getAllColors().contains(input);
    }
}
