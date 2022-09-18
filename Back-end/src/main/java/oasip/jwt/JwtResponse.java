package oasip.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String access_token;
    private final String refresh_token;

    public JwtResponse(String accress_token,String refresh_token) {
        this.access_token = accress_token;
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public String getRefresh_token(){
        return this.refresh_token;
    }
}
