package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.share.AddGroupRequest
import com.upf464.koonsdiary.domain.request.share.SearchUserRequest
import com.upf464.koonsdiary.domain.request.share.UpdateGroupRequest
import com.upf464.koonsdiary.domain.response.share.AddGroupResponse
import com.upf464.koonsdiary.domain.response.share.SearchUserResponse
import com.upf464.koonsdiary.domain.response.share.UpdateGroupResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.share.AddGroupUseCase
import com.upf464.koonsdiary.domain.usecase.share.SearchUserUseCase
import com.upf464.koonsdiary.domain.usecase.share.UpdateGroupUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ShareUseCaseModule {

    @Binds
    abstract fun bindSearchUserUseCase(
        useCase: SearchUserUseCase
    ): ResultUseCase<SearchUserRequest, SearchUserResponse>

    @Binds
    abstract fun bindAddGroupUseCase(
        useCase: AddGroupUseCase
    ): ResultUseCase<AddGroupRequest, AddGroupResponse>

    @Binds
    abstract fun bindUpdateGroupUseCase(
        useCase: UpdateGroupUseCase
    ): ResultUseCase<UpdateGroupRequest, UpdateGroupResponse>
}