/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.response.ImageResponseDTO;
import rookiesspring.model.Image;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ImageMapper implements BaseMapper<Image, ImageDTO, ImageResponseDTO> {

    @Override
    public Image toEntity(ImageDTO dto) {
        Image i = new Image();
        i.setUrl(dto.url());
        i.setName(dto.name());
        i.setDescription(dto.description());
        return i;
    }

    @Override
    public ImageResponseDTO ToResponseDTO(Image e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ImageResponseDTO> ToResponseDTOList(List<Image> e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
