package com.app.service.client.mapper;

import com.app.service.client.domain.make.MakeDTO;
import com.app.service.client.model.Make;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface MakeMapperClient {

    MakeMapperClient INSTANCE = Mappers.getMapper(
        MakeMapperClient.class);

    MakeDTO EntityToDto(Make make);
    Make DtoToEntity(MakeDTO makeDTO);
    List<MakeDTO> listModelToList(List<Make> model);
}
