package com.bootcamp.project.project_yahoo_finance.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDateDTO {

  @JsonIgnore
  private String systemDate;

  @JsonIgnore
  private String symbol;

  public void setSystemDate(String systemDate){
    this.systemDate = systemDate.split("T")[0];
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
  Map<String, Object> result = new HashMap<>();
  result.put("SYSDATE-".concat(symbol), systemDate);
  return result;
  }

}
