package model.statics;



import com.google.common.base.Joiner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 *
 */
@Component
@ConfigurationProperties(prefix = "app")
public class SpringApp implements InitializingBean {
    private String name;
    private String version;
    private String groupId;
    private String secret;
    private String apiVersion;
    private String apiBasePackage;
    private String[] profiles;

    private int serverPort;
    private String serverHost;
    private String[] dbModel;

    @Autowired
    public SpringApp(Environment environment) {
        this.profiles = environment.getActiveProfiles();
        this.serverPort = Integer.parseInt(environment.getProperty("server.port"));
        this.serverHost = environment.getProperty("server.host",
                NetUtil.getLocalHost().orElseThrow(() -> new IllegalArgumentException("server host")));
    }

    public String getProfiles() {
        return Joiner.on(",").join(profiles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiBasePackage() {
        return apiBasePackage;
    }

    public void setApiBasePackage(String apiBasePackage) {
        this.apiBasePackage = apiBasePackage;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String[] getDbModel() {
        return dbModel;
    }

    public void setDbModel(String[] dbModel) {
        this.dbModel = dbModel;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}