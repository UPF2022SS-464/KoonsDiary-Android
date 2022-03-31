package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.share.*
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.share.*
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.share.*
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

    @Binds
    abstract fun bindDeleteGroupUseCase(
        useCase: DeleteGroupUseCase
    ): ResultUseCase<DeleteGroupRequest, EmptyResponse>

    @Binds
    abstract fun bindFetchGroupListUseCase(
        useCase: FetchGroupListUseCase
    ): ResultUseCase<FetchGroupListRequest, FetchGroupListResponse>

    @Binds
    abstract fun bindAddShareDiaryUseCase(
        useCase: AddShareDiaryUseCase
    ): ResultUseCase<AddShareDiaryRequest, AddShareDiaryResponse>

    @Binds
    abstract fun bindUpdateShareDiaryUseCase(
        useCase: UpdateShareDiaryUseCase
    ): ResultUseCase<UpdateShareDiaryRequest, UpdateShareDiaryResponse>
}