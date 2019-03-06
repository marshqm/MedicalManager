package model.statics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * body 对象包装为统一的响应格式
 */
public class JBoyResponse extends ResponseEntity {
    static Logger logger = LoggerFactory.getLogger(JBoyResponse.class);
    public static final String SUCCESS = "10000";
    public static final int HTTPSTATUS = 200;

    WrapperBody wrapper = new WrapperBody();

    public JBoyResponse(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }

    @Override
    public WrapperBody getBody() {
        if(!wrapper.__loaded) {
            wrapper.__loaded = true;
            wrapper.setObj(super.getBody());
            logger.debug("Response: {}", null, 200);
        }

        return wrapper;
    }

    public static BodyBuilder status(int status) {
        return status(HttpStatus.valueOf(status));
    }

    public static BodyBuilder status(HttpStatus status) {
        return new DefaultBuilder(status);
    }

    public static BodyBuilder success() {
        return status(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> success(T body) {
        BodyBuilder builder = success();
        return builder.body(body);
    }

    public static <T> ResponseEntity<T> error(CommonErrorCode code) {
        return error(code, null);
    }

    public static <T> ResponseEntity<T> error(CommonErrorCode code, String message) {
        return error(code, message, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<T> error(CommonErrorCode code, String message, Object debug) {
        DefaultBuilder builder = (DefaultBuilder) status(HTTPSTATUS);
        builder.code = code;
        builder.message = message;
        builder.debug = debug;
        return builder.build();
    }

    public static class WrapperBody {
        private String code = SUCCESS;
        private String message;
        private Object obj;
        private Object _debug;

        boolean __loaded;

        public WrapperBody() {
        }

        public WrapperBody(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static WrapperBody code(String code) {
            WrapperBody response = new WrapperBody();
            response.setCode(code);
            return response;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Object get_debug() {
            return _debug;
        }

        public void set_debug(Object _debug) {
            this._debug = _debug;
        }
    }

    ////////////////////////////////////

    private static class DefaultBuilder implements BodyBuilder {

        CommonErrorCode code;
        String message;
        Object debug;

        private final HttpStatus statusCode;

        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<HttpMethod>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder eTag(String eTag) {
            if (eTag != null) {
                if (!eTag.startsWith("\"") && !eTag.startsWith("W/\"")) {
                    eTag = "\"" + eTag;
                }
                if (!eTag.endsWith("\"")) {
                    eTag = eTag + "\"";
                }
            }
            this.headers.setETag(eTag);
            return this;
        }

        @Override
        public BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        @Override
        public BodyBuilder cacheControl(CacheControl cacheControl) {
            String ccValue = cacheControl.getHeaderValue();
            if (ccValue != null) {
                this.headers.setCacheControl(cacheControl.getHeaderValue());
            }
            return this;
        }

        @Override
        public BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        @Override
        public ResponseEntity build() {
            return body(null);
        }

        @Override
        public ResponseEntity body(Object body) {
            JBoyResponse response = new JBoyResponse(body, this.headers, this.statusCode);
            if(this.code != null) {
                response.wrapper.setCode("");
            }
            response.wrapper.setMessage(this.message);
            response.wrapper.set_debug(this.debug);

            return response;
        }
    }
}
