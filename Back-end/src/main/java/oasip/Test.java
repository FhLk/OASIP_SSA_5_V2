package oasip;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Test {
    public static void main(String[] args) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(8,32,1,65536,10);
        String pass=encoder.encode("oasipadmin");
        System.out.println(pass);
    }
}
