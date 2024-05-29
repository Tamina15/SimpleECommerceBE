/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

import rookiesspring.controller.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.response.SupplierResponseDTO;
import rookiesspring.dto.response.custom.SupplierResponseDTOShort;
import rookiesspring.service.SupplierService;

/**
 *
 * @author HP
 * @author Tamina
 */
//@RestController
//@RequestMapping("api/v1/suppliers")
public class SupplierController {

    @Autowired
    SupplierService service;

    @GetMapping({"", "/"})
    public List<SupplierResponseDTOShort> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SupplierResponseDTOShort getOneById(@PathVariable("id") long id) {
        return service.findOneById(id);
    }

    @GetMapping("/search")
    public List<SupplierResponseDTOShort> getAllByName(@RequestParam("name") String name) {
        return service.findAllByName(name);
    }

    @GetMapping("/full")
    public List<SupplierResponseDTO> getAllFull() {
        return service.findAllFull();
    }

    @GetMapping("/full/{id}")
    public SupplierResponseDTO getOneByIdFull(@PathVariable("id") long id) {
        return service.findOneByIdFull(id);
    }

    @GetMapping("/full/search")
    public List<SupplierResponseDTO> getAllByNameFull(@RequestParam("name") String name) {
        return service.findAllByNameFull(name);
    }
}
