package com.example.DemoTest.service;

import com.example.DemoTest.core.Sign;
import com.example.DemoTest.model.User;
import com.example.DemoTest.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
//        return new CustomUserDetails(user);
        return null;
    }

    public String saveUserSign(Sign sign) throws Exception {
        User user=new User();
        if (userRepository.existsByUserName(sign.getUserName())) throw new Exception("Username đã tồn tại");
        user.setUserName(sign.getUserName());
        user.setPassWord(passwordEncoder.encode(sign.getPassWord()));
        userRepository.save(user);
        return "success";
    }

//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
//        return new CustomUserDetails(user);
//    }

//    public List<User> getAll(){
//        return (List<User>) userRepository.findAll();
//    }
//    public List<String> save(User user){
//        List<String> lmessage=new ArrayList<>();
//        if (userRepository.findByUserName(user.getUserName()) !=null)  lmessage.add("username đã tồn tại");
////        if (!roleRepository.findById(user.getRole().getId()).isPresent()) lmessage.add("Role không tồn tại");
//        if (lmessage.size()==0){
//            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
//            userRepository.save(user);
//            lmessage.add("success");
//        }
//        return lmessage;
//    }
    public String delete(long id){
        if (!userRepository.findById(id).isPresent()) throw new UsernameNotFoundException("User not found with id : " + id);
        userRepository.deleteById(id);
        return "success";
    }
//    public User findByUserName(String username){
//        return userRepository.findByUserName(username);
//    }


}
