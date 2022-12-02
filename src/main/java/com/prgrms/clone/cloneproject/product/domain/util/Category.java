package com.prgrms.clone.cloneproject.product.domain.util;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Category {
    OUTER(),
    TOP(),
    BOTTOM(),
    DRESS(),
    SHOES(),
    ETC();

    public static Category findCategory(String input) {
        if (isNotContain(input)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("해당하는 카테고리를 찾을 수 없습니다. 올바른 값을 전달하세요. [전달된 값 : {0}]", input));
        }
        return Category.valueOf(input);
    }

    public static List<String> getAllCategories(){
        return Arrays.stream(Category.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private static boolean isNotContain(String input) {
        return !getAllCategories().contains(input);
    }
}
