package com.jike.demo.tim.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jike.demo.tim.enums.TIMTagEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qu.kun
 * @date 2021/7/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TIMTagValue {

    @JsonProperty("Tag")
    private TIMTagEnum tag;
    @JsonProperty("Value")
    private String value;

}
