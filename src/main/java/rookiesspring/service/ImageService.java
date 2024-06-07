/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.UploadImageDTO;
import rookiesspring.dto.response.ImageResponseDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.exception.BadRequestException;
import rookiesspring.mapper.ImageMapper;
import rookiesspring.mapper.ProductMapper;
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

    private final ImageRepository repository;
    private final ImageMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ImageService(ImageRepository repository, ImageMapper mapper, CloudinaryService cloudinaryService, ProductRepository productRepository, ProductMapper productMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ProductResponseDTO removeImages(long product_id, long[] images_id, boolean forced) {
        Product p = productRepository.findById(product_id).orElseThrow(() -> new EntityNotFoundException());
        if (images_id.length != 0) {
            for (long id : images_id) {
                Image i = repository.getReferenceById(id);
                if (forced) {
                    p.removeImage(i);
                    repository.hardDelete(id);
                    
                    // todo delete on cloudinary
                } else {
                    repository.delete(i);
                }
            }
        }
        return productMapper.ToResponseDTO(p);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void restoreImages(long image_id) {
        if (repository.existsById(image_id)) {
            repository.restore(image_id);
        } else {
            throw new EntityNotFoundException();
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
        return mapper.ToResponseDTO(image);
    }

}
