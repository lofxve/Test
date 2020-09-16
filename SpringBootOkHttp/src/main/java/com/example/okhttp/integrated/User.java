package com.example.okhttp.integrated;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String userName;
    private String password;
    private String token;
    private String baseurl;
}
