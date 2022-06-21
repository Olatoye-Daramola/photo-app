package com.olatoye.photoapp.services.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<?, ?> upload(byte[] bytes, Map<?, ?> params) throws IOException {
        return cloudinary.uploader().upload(bytes, params);
    }
}
