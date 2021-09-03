package com.gzt.service;

import com.gzt.model.User;

public interface UserService {
    User findByPhone(String phone);
}
