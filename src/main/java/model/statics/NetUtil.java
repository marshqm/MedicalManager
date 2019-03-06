package model.statics;

import java.net.InetAddress;
import java.util.Optional;

public class NetUtil {
    /**
     * 获取本机 host
     *
     */
    public static Optional<String> getLocalHost() {
        try {
            return Optional.of(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
            // empty
        }
        return Optional.empty();
    }
}
