package stepDefinitions;

import java.util.Map;

public class JiraPojo {

    private Map<String, String> session;
    private Map<String, String> loginInfo;


    public Map<String, String> getSession() {
        return session;
    }

    public void setSession(Map<String, String> session) {
        this.session = session;
    }

    public Map<String, String> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, String> loginInfo) {
        this.loginInfo = loginInfo;
    }


}
