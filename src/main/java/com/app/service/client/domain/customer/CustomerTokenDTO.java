package com.app.service.client.domain.customer;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerTokenDTO implements Serializable {

  private String token;
  private CustomerDTO customerDTO;
}
