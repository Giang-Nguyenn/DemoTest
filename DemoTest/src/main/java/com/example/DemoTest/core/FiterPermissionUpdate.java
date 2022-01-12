package com.example.DemoTest.core;

import com.example.DemoTest.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FiterPermissionUpdate {
    public Boolean hasPermissionUpdate(Long idUpdate, Authentication authentication){
        CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();
        return (idUpdate.equals(customUserDetails.getId()));
    }

//    public Map<Object,Object> removeField(Map<Object,Object> fields, List<String> listFieldRemore){
//        fields.remove("id");
//        for (String field:listFieldRemore){
//            fields.remove(field);
//        }
//        return fields;
//    }
}
