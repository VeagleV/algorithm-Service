package com.crm.algo.core.dto;


import com.crm.algo.core.entity.Span;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {

    @JsonProperty("route_id")
    private Integer routeId;

    @JsonProperty("spans")
    private List<Span> spans;
}
