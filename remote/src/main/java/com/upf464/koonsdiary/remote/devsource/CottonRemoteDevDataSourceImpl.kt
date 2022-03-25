package com.upf464.koonsdiary.remote.devsource

import com.upf464.koonsdiary.data.source.CottonRemoteDataSource
import javax.inject.Inject

internal class CottonRemoteDevDataSourceImpl @Inject constructor(
): CottonRemoteDataSource {
    // 서버에서 질문 요청 + 질문 값 받아오기
    override suspend fun GetQuestion(id: String, questionKR: String, questionUS: String): Result<String> {
        return Result.success("")
    }

}