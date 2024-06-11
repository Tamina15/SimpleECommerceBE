/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.response.SupplierResponseDTO;
import rookiesspring.dto.response.custom.SupplierResponseDTOShort;
import rookiesspring.mapper.SupplierMapper;
import rookiesspring.repository.SupplierRepository;
import rookiesspring.service.interfaces.SupplierServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class SupplierService implements SupplierServiceInterface {

    SupplierRepository repository;
    SupplierMapper mapper;

    public SupplierService(SupplierRepository repository, SupplierMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SupplierResponseDTOShort> findAll() {
        return repository.findAllProjectedBy();
    }

    public List<SupplierResponseDTOShort> findAllByName(String name) {
        return repository.findAllProjectedByNameContainingIgnoreCase(name);
    }

    public SupplierResponseDTOShort findOneById(long id) {
        return repository.findOneProjectedById(id);
    }

    public List<SupplierResponseDTO> findAllByNameFull(String name) {
        return mapper.ToResponseDTOList(repository.findAllByNameContainingIgnoreCase(name));
    }

    public List<SupplierResponseDTO> findAllFull() {
        return mapper.ToResponseDTOList(repository.findAll());
    }

    public SupplierResponseDTO findOneByIdFull(long id) {
        return mapper.ToResponseDTO(repository.findById(id).orElseThrow());
    }

}
