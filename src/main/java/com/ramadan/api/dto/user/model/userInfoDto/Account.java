package com.ramadan.api.dto.user.model.userInfoDto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private List<String> roles;

}
