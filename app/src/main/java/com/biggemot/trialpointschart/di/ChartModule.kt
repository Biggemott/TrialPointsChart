package com.biggemot.trialpointschart.di

import com.biggemot.trialpointschart.data.ChartRepoImpl
import com.biggemot.trialpointschart.domain.ChartInteractor
import com.biggemot.trialpointschart.domain.ChartInteractorImpl
import com.biggemot.trialpointschart.domain.ChartRepo
import com.biggemot.trialpointschart.domain.entity.PointEntity
import com.biggemot.trialpointschart.presentation.mapper.PointModelMapper
import com.biggemot.trialpointschart.presentation.model.PointModel
import com.biggemot.trialpointschart.utils.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ChartModule {

    @Binds
    abstract fun bindChartDataInteractor(
        chartDataInteractorImpl: ChartInteractorImpl
    ): ChartInteractor

    @Binds
    abstract fun bindChartDataRepo(
        chartRepoImpl: ChartRepoImpl
    ): ChartRepo

    @Binds
    abstract fun bindPointModelMapper(
        pointModelMapper: PointModelMapper
    ): Mapper<PointEntity, PointModel>
}