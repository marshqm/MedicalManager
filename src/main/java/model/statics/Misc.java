package model.statics;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * 杂项
 */
public class Misc {
    /**
     * <主版号>.<次版号>.<修建号>-<发行号>#<构建信息>(SCM版本号)，例如 1.0​​.0-alpha#build152
     * <p>
     * 版本优先级：
     * 1.0.0-alpha < 1.0.0-alpha.1 < 1.0.0-alpha.beta < 1.0.0-beta < 1.0.0-beta.2 < 1.0.0-beta.11 < 1.0.0-rc.1 < 1.0.0
     *
     * <p>
     * 在maven构建中需要添加：
     *
     * <pre>
     * {@code
     * <plugin>
     *   <artifactId>maven-jar-plugin</artifactId>
     *     <configuration>
     *       <archive>
     *           <manifestEntries>
     *               <Implementation-Title>${project.name}</Implementation-Title>
     *               <Implementation-Version>${project.version}</Implementation-Version>
     *               <Implementation-Build-Number>${build.number}</Implementation-Build-Number>
     *               <Implementation-SCM-Revision>${build.revision}</Implementation-SCM-Revision>
     *           </manifestEntries>
     *       </archive>
     *   </configuration>
     * </plugin>
     * }
     * </pre>
     *
     * @param mavenGroupId maven group id
     * @param mavenArtifactId maven artifact id
     * @see <a href="http://semver.org">semver.org</a>
     */
    public static String getVersion(String mavenGroupId, String mavenArtifactId) {
        String version = null;
        String build = null;
        String revision = null;

        // jar
        try {
            Enumeration enu = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");
            while (enu.hasMoreElements()) {
                URL url = (URL) enu.nextElement();
                String file = url.toString();
                if(file.contains(mavenArtifactId) && !file.contains("/BOOT-INF/lib")) {
                    Manifest manifest = new Manifest(url.openStream());
                    Attributes attributes = manifest.getMainAttributes();
                    version = attributes.getValue("Implementation-Version");
                    build = attributes.getValue("Implementation-Build-Number");
                    revision = attributes.getValue("Implementation-SCM-Revision");
                    break;
                }
            }
        } catch (Exception e) {
            // ignore
        }

        // maven中
        String mavenCoordinate = mavenGroupId + "/" + mavenArtifactId;
        if(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(mavenCoordinate)) {
            try(InputStream in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("META-INF/maven/" + mavenCoordinate + "/pom.properties")) {
                Properties prop = new Properties();
                prop.load(in);
                version = prop.getProperty("version");
            } catch (Exception e) {
                // ignore
            }
        }

        // 使用主机名
        if(StringUtils.isBlank(version)) {
            try {
                version = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                // ignore
            }
        }
        return (StringUtils.isBlank(version) ? "N/A" : version)
                + (StringUtils.isBlank(build) ? "" : "#build" + build)
                + (StringUtils.isBlank(revision) ? "" : "." + revision);
    }

    public static long getUptime() {
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }
}
