package softixx.api.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring")
public interface WarningUnmappedMapperConfig {

}
