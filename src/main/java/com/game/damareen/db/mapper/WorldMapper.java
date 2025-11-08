package com.game.damareen.db.mapper;

import com.game.damareen.domain.World;
import com.game.damareen.db.entity.WorldEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorldMapper {
    World fromEntity(WorldEntity worldEntity);
    WorldEntity toEntity(World world);
}
