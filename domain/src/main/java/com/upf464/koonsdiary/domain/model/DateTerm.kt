package com.upf464.koonsdiary.domain.model

enum class DateTerm(val type: Type, val term: Long) {
    DAY_7(Type.DAY, 7),
    DAY_14(Type.DAY, 14),
    DAY_21(Type.DAY, 21),
    DAY_28(Type.DAY, 28),

    WEEK_8(Type.WEEK, 8),
    WEEK_12(Type.WEEK, 12),
    WEEK_16(Type.WEEK, 16),
    WEEK_20(Type.WEEK, 20),

    MONTH_6(Type.MONTH, 6),
    MONTH_9(Type.MONTH, 9),
    MONTH_12(Type.MONTH, 12),
    MONTH_15(Type.MONTH, 15),
    MONTH_18(Type.MONTH, 18);

    enum class Type {
        DAY,
        WEEK,
        MONTH
    }
}
