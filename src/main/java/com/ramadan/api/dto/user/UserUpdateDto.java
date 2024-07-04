package com.ramadan.api.dto.user;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.user.User}
 */
@Data
@NoArgsConstructor
public class UserUpdateDto implements Serializable {
    String name;
    String famillyName;
    String email;
    String telephone;
}
