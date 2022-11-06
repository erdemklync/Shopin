package com.erdemklync.shopin.domain.util

interface Mapper <DataEntity, DomainModel> {
    fun mapFromEntity(entity: DataEntity): DomainModel
    fun mapToEntity(domainModel: DomainModel): DataEntity
}