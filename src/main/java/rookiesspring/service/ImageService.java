/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.ProductDTO_1;
import rookiesspring.dto.UploadImageDTO;
import rookiesspring.dto.response.ImageResponseDTO;
import rookiesspring.exception.BadRequestException;
import rookiesspring.mapper.ImageMapper;
import rookiesspring.model.Image;
import rookiesspring.model.Product;
import rookiesspring.repository.ImageRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.service.interfaces.ImageServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ImageService implements ImageServiceInterface {

    @Autowired
    private ImageRepository repository;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductRepository productRepository;

    public void addImages(long product_id, ImageDTO[] images) {
        Product product = productRepository.getReferenceById(product_id);
        if (images.length != 0) {
            for (ImageDTO i : images) {
                if (!repository.existsByName(i.name())) {
                    Image image = imageMapper.toEntity(i);
                    image.setProduct(product);
                    product.addImage(image);
                }
            }
            productRepository.save(product);
        }
    }

    public void removeImages(long product_id, long[] images_id) {
        Product p = productRepository.getReferenceById(product_id);
        if (images_id.length != 0) {
            for (long id : images_id) {
                Image i = repository.getReferenceById(id);
                System.out.println(i.getId());
                p.removeImage(i);
            }
            productRepository.save(p);
        }
    }

    public ImageResponseDTO uploadImage(UploadImageDTO upload_image_DTO) {
        if (!productRepository.existsById(upload_image_DTO.getProduct_id())) {
            throw new EntityNotFoundException("Product Not Existed");
        }
        Product product = productRepository.getReferenceById(upload_image_DTO.getProduct_id());

        Image image = new Image();
        image.setName(upload_image_DTO.getName());
        image.setUrl(cloudinaryService.uploadFile(upload_image_DTO.getFile(), "product_1"));
        image.setProduct(product);

        if (image.getUrl() == null) {
            throw new BadRequestException("Failed to Upload Image");
        }

        repository.save(image);

        return imageMapper.ToResponseDTO(image);

//            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
    }

    public ResponseEntity<Map> uploadImage(ProductDTO_1 productDTO) {
        return null;
    }

    @Override
    public boolean checkExist(long id
    ) {
        return false;
    }

}
