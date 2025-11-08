package com.game.damareen.domain.db.mapper;

import com.game.damareen.domain.World;
import com.game.damareen.domain.db.entity.WorldEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorldMapper {
    World fromEntity(WorldEntity worldEntity);
    WorldEntity toEntity(World world);
}
