package com.upf464.koonsdiary.domain.model

enum class DateTerm(val type: Type, val term: Long) {
    DAY_7(Type.DAY, 7),
    DAY_14(Type.DAY, 14),
    DAY_21(Type.DAY, 21),
    DAY_28(Type.DAY, 28),

    WEEK_7(Type.WEEK, 7),
    WEEK_14(Type.WEEK, 14),
    WEEK_21(Type.WEEK, 21),
    WEEK_28(Type.WEEK, 28),

    MONTH_7(Type.MONTH, 7),
    MONTH_14(Type.MONTH, 14),
    MONTH_21(Type.MONTH, 21),
    MONTH_28(Type.MONTH, 28);

    enum class Type {
        DAY,
        WEEK,
        MONTH
    }
}
