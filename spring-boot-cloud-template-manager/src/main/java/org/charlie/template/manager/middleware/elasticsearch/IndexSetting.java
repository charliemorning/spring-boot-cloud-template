package org.charlie.template.manager.middleware.elasticsearch;

import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@Builder
public class IndexSetting {
    @JsonProperty("number_of_shards")
    private int shards;
    @JsonProperty("number_of_replicas")
    private int replicas;
}
