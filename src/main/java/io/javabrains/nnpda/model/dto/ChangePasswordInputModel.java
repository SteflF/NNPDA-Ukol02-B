package io.javabrains.nnpda.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordInputModel {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
