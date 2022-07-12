package com.dev.core.auth.authservice.model.api;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleToUserForm {

    private String username;

    private String roleName;
}