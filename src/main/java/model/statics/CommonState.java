package model.statics;

import java.util.Optional;

public enum CommonState implements Valuable {
    /**
     * 初始化
     */
    INIT("0"),
    /**
     * 启用
     */
    ACTIVE("A"),
    /**
     * 禁用
     */
    INACTIVE("I"),
    /**
     * 已删除
     */
    DELETED("D"),
    /**
     * 已完成
     */
    FINISH("F"),

    ;

    String val;

    public static Optional<CommonState> of(String code) {
        return EnumUtil.of(code, CommonState.class);
    }

    public static CommonState ofWithVerify(String code, CommonState... in) {
        return EnumUtil.ofWithVerify(code, CommonState.class, in);
    }

    CommonState(String val) {
        this.val = val;
    }


    @Override
    public String getValue() {
        return val;
    }
}
