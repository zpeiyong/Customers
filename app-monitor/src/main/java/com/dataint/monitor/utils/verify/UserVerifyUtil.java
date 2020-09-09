package com.dataint.monitor.utils.verify;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.monitor.dao.entity.User;
import com.dataint.monitor.exception.sys.LoginErrorException;
import com.dataint.monitor.exception.sys.UserNotExistException;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserVerifyUtil {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private UserVerifyUtil(){}

    public static void verifyLogin(User user, String password) {
        if (user == null)
            throw new UserNotExistException();

        if (!user.getEnabled())
            throw new DataintBaseException(BaseExceptionEnum.DATA_VER_USER_NOT_ENABLED);

        if (StringUtils.isEmpty(password)) {
            throw new LoginErrorException();
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new LoginErrorException(BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR.getIndex(),
                    BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR.getName());
        }
    }
}
