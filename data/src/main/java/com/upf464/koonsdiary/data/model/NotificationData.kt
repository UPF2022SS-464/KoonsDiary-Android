package com.upf464.koonsdiary.data.model

sealed interface NotificationData {

    data class GroupInvite(
        val group: ShareGroupData = ShareGroupData(),
        val isAccepted: Boolean? = null,
    ) : NotificationData

    data class CottonReaction(
        val answer: QuestionAnswerData = QuestionAnswerData(),
        val reaction: ReactionData = ReactionData(),
    ) : NotificationData

    data class NewDiary(
        val diary: ShareDiaryData = ShareDiaryData(),
    ) : NotificationData

    data class DiaryComment(
        val diary: ShareDiaryData = ShareDiaryData(),
        val comment: CommentData = CommentData(),
    ) : NotificationData
}
