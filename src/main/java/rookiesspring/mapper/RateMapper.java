/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.RateDTO;
import rookiesspring.dto.response.RateResponseDTO;
import rookiesspring.model.Rate;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class RateMapper implements BaseMapper<Rate, RateDTO, RateResponseDTO> {

    @Override
    public Rate toEntity(RateDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RateResponseDTO ToResponseDTO(Rate e) {
        RateResponseDTO rate_response_dto = new RateResponseDTO(e.getUser().getUserName(), e.getScore(), e.getComment());
        return rate_response_dto;
    }

    @Override
    public List<RateResponseDTO> ToResponseDTOList(List<Rate> e) {
        List<RateResponseDTO> list = new ArrayList<>();
        for (Rate rate : e) {
            list.add(ToResponseDTO(rate));
        }
        return list;
    }

}
