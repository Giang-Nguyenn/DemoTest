package com.example.DemoTest.service;

import com.example.DemoTest.core.upload.UploadFile;
import com.example.DemoTest.core.auth.Sign;
import com.example.DemoTest.exception.AlreadyExistsException;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.model.CustomUserDetails;
import com.example.DemoTest.model.User;
import com.example.DemoTest.repository.IRoleRepository;
import com.example.DemoTest.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    public User save(User user){
        return userRepository.save(user);
    }

    public User saveUserSign(Sign sign){
        User user=new User();
        if (userRepository.existsByUsername(sign.getUsername())) throw new AlreadyExistsException(String.format("Username %s AlreadyExists",sign.getUsername()));
        user.setUsername(sign.getUsername());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return new CustomUserDetails(user);
    }

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
    public Boolean delete(long id){
        if (!userRepository.findById(id).isPresent()) throw new NotFoundException("User not found with id : " + id);
        userRepository.deleteById(id);
        return true;
    }
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }
    public User findUserById(Long id){
        User user=userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id));
        return user;
    }
    public List<User> findAllUser(Pageable pageable){
        return userRepository.findAllUser(pageable);
    }
    @Transactional
    public String uploadImage(Long id ,MultipartFile file) throws IOException {
        Optional optional=userRepository.findById(id);
        if(!optional.isPresent()) throw new NotFoundException("User not found with id : " + id);
        UploadFile uploadFile=new UploadFile();
        String url = uploadFile.upload(id,file);
        User user= (User) optional.get();
        user.setImage(url);
        userRepository.save(user);
        return url;
    }


}
