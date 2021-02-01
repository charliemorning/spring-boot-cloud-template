package org.charlie.template.manager.middleware.elasticsearch;


import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@Builder
public class Index {
    private String name;
    @JsonProperty("settings")
    private IndexSetting setting;
}
