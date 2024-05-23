/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class CloudinaryService {

    @Resource
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            return null;
        }
    }

//    public Map uploadFile(MultipartFile file, String folderName) throws IOException {
//        return cloudinary.uploader().upload(file.getBytes(), 
//            ObjectUtils.asMap(
//                "folder", folderName
//            ));
//    }
//    public Map uploadVideo(MultipartFile file, String folderName) throws IOException {
//        return cloudinary.uploader().upload(file.getBytes(), 
//            ObjectUtils.asMap(
//                "resource_type", "video",
//                "folder", folderName
//            ));
//    }
}
