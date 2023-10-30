package travelMaker.backend.user.login;
import lombok.Data;

@Data
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public KakaoAccount kakao_account;
    @Data
    public class KakaoAccount {
        public Boolean name_needs_agreement;
        public String name;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public Boolean has_age_range;
        public Boolean age_range_needs_agreement;
        public String age_range;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;
        public String gender;

    }

}