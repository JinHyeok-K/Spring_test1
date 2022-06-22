package Test1_web.service;

//import Test1_web.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    HttpServletRequest request;

}
