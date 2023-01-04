package com.app.service.client.domain.user;

import com.app.service.client.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSuggestionDTO implements Serializable {
  private List<String> usernames;
  private List<String> fullNames;
  private List<String> phone;
  private List<String> email;
  private List<Role> roles;
}
