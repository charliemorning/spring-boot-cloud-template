package org.charlie.template.configures;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component()
@ConfigurationProperties(prefix = "es")
public class ElasticSearchConfig {
    private String urls;
    public String[] getUrls() {
        return urls.split(",");
    }
}
