package com.erdemklync.shopin.data.mapper

import com.erdemklync.shopin.data.remote.entity.RatingEntity
import com.erdemklync.shopin.domain.model.Rating
import com.erdemklync.shopin.domain.util.Mapper


class RatingMapper : Mapper<RatingEntity, Rating> {

    override fun mapFromEntity(entity: RatingEntity): Rating {
        return Rating(
            count = entity.count ?: 0,
            rate = entity.rate ?: 0.0
        )
    }

    override fun mapToEntity(domainModel: Rating): RatingEntity {
        return RatingEntity(
            count = domainModel.count,
            rate = domainModel.rate
        )
    }

}
