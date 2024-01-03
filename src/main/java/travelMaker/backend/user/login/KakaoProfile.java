package travelMaker.backend.user.login;
import lombok.Data;

@Data
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public KakaoAccount kakao_account;
    @Data
    public class KakaoAccount {
        public Profile profile;
        public String name;
        public Boolean has_email;
        public String email;
        public String age_range;
        public String gender;
        public String birthyear;
        public String birthday;
        @Data
        public class Profile{
            public String profile_image_url;
        }

    }

}