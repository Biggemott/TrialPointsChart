package com.biggemot.trialpointschart.presentation.mapper

import com.biggemot.trialpointschart.domain.entity.PointEntity
import com.biggemot.trialpointschart.presentation.model.PointModel
import com.biggemot.trialpointschart.utils.Mapper
import javax.inject.Inject

class PointModelMapper @Inject constructor() : Mapper<PointEntity, PointModel> {

    override fun map(input: PointEntity): PointModel {
        return PointModel(
            x = input.x,
            y = input.y
        )
    }
}