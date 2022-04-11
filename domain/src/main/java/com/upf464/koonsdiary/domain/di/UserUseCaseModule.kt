package com.upf464.koonsdiary.domain.di

import com.upf464.koonsdiary.domain.request.user.AutoSignInRequest
import com.upf464.koonsdiary.domain.request.user.FetchUserImageListRequest
import com.upf464.koonsdiary.domain.request.user.SignInWithKakaoRequest
import com.upf464.koonsdiary.domain.request.user.SignInWithUsernameRequest
import com.upf464.koonsdiary.domain.request.user.SignUpWithKakaoRequest
import com.upf464.koonsdiary.domain.request.user.SignUpWithUsernameRequest
import com.upf464.koonsdiary.domain.request.user.UpdateUserRequest
import com.upf464.koonsdiary.domain.request.user.ValidateSignUpRequest
import com.upf464.koonsdiary.domain.response.EmptyResponse
import com.upf464.koonsdiary.domain.response.user.FetchUserImageListResponse
import com.upf464.koonsdiary.domain.usecase.ResultUseCase
import com.upf464.koonsdiary.domain.usecase.user.AutoSignInUseCase
import com.upf464.koonsdiary.domain.usecase.user.FetchUserImageListUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignInWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignInWithUsernameUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithKakaoUseCase
import com.upf464.koonsdiary.domain.usecase.user.SignUpWithUsernameUseCase
import com.upf464.koonsdiary.domain.usecase.user.UpdateUserUseCase
import com.upf464.koonsdiary.domain.usecase.user.ValidateSignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserUseCaseModule {

    @Binds
    abstract fun bindValidateSignUpUseCase(
        useCase: ValidateSignUpUseCase
    ) : ResultUseCase<ValidateSignUpRequest, EmptyResponse>

    @Binds
    abstract fun bindSignInWithUsernameUseCase(
        useCase: SignInWithUsernameUseCase
    ) : ResultUseCase<SignInWithUsernameRequest, EmptyResponse>

    @Binds
    abstract fun bindSignUpWithUsernameUseCase(
        useCase: SignUpWithUsernameUseCase
    ) : ResultUseCase<SignUpWithUsernameRequest, EmptyResponse>

    @Binds
    abstract fun bindSignInWithKakaoUseCase(
        useCase: SignInWithKakaoUseCase
    ) : ResultUseCase<SignInWithKakaoRequest, EmptyResponse>

    @Binds
    abstract fun bindSignUpWithKakaoUseCase(
        useCase: SignUpWithKakaoUseCase
    ) : ResultUseCase<SignUpWithKakaoRequest, EmptyResponse>

    @Binds
    abstract fun bindAutoSignInUseCase(
        useCase: AutoSignInUseCase
    ) : ResultUseCase<AutoSignInRequest, EmptyResponse>

    @Binds
    abstract fun bindUpdateUserUseCase(
        useCase: UpdateUserUseCase
    ) : ResultUseCase<UpdateUserRequest, EmptyResponse>

    @Binds
    abstract fun bindFetchUserImageListUseCase(
        useCase: FetchUserImageListUseCase
    ) : ResultUseCase<FetchUserImageListRequest, FetchUserImageListResponse>
}
