/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.mapper;

import java.util.List;

/**
 *
 * @author HP
 */
public interface BaseMapper<E, DTO, R> {

    public E toEntity(DTO dto);

    public R ToResponseDTO(E e);

    public List<R> ToResponseDTOList(List<E> e);
}
