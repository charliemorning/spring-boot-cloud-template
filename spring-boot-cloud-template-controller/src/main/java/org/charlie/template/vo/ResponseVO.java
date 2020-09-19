package org.charlie.template.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseVO {
    private String resultCode;
    private String resultMessage;
}
