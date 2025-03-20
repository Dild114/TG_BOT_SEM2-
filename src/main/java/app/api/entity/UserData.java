package app.api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
  String userName;
  String password;
  String email;
}
