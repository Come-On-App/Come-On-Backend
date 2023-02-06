package com.comeon.backend.common.log;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedByteArrayOfInputStream;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedByteArrayOfInputStream = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(cachedByteArrayOfInputStream);
    }

    public static class CustomServletInputStream extends ServletInputStream {

        private final InputStream cachedBodyInputStream;

        public CustomServletInputStream(byte[] byteArrayOfInputStream) {
            this.cachedBodyInputStream = new ByteArrayInputStream(byteArrayOfInputStream);
        }

        @Override
        public boolean isFinished() {
            try {
                return cachedBodyInputStream.available() == 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }
    }
}
