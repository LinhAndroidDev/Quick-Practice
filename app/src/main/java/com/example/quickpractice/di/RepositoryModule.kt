package com.example.quickpractice.di

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
}