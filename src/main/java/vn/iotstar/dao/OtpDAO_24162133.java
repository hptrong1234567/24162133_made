package vn.iotstar.dao;

import java.util.Optional;
import vn.iotstar.model.OtpToken_24162133;

public interface OtpDAO_24162133 {
    OtpToken_24162133 insert(OtpToken_24162133 token);
    Optional<OtpToken_24162133> findLatestByEmailAndType(String email, String type);
    void deleteByEmailAndType(String email, String type);
    void update(OtpToken_24162133 token);
}