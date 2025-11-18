package com.example.quickpractice.di

import com.example.quickpractice.data.repository.AuthRepository
import com.example.quickpractice.data.repository.AuthRepositoryImpl
import com.example.quickpractice.data.repository.ExamHistoryRepository
import com.example.quickpractice.data.repository.ExamHistoryRepositoryImpl
import com.example.quickpractice.data.repository.ExamRepository
import com.example.quickpractice.data.repository.ExamRepositoryImpl
import com.example.quickpractice.data.repository.SubjectRepository
import com.example.quickpractice.data.repository.SubjectResponseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSubjectRepository(
        subjectRepositoryImpl: SubjectResponseImpl
    ): SubjectRepository

    @Binds
    @Singleton
    abstract fun bindExamRepository(
        examRepositoryImpl: ExamRepositoryImpl
    ) : ExamRepository

    @Binds
    @Singleton
    abstract fun bindExamHistoryRepository(
        examHistoryRepositoryImpl: ExamHistoryRepositoryImpl
    ) : ExamHistoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository
}