///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package rookiesspring.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.stereotype.Service;
//import rookiesspring.dto.ImportBillDTO;
//import rookiesspring.dto.response.ImportBillResponseDTO;
//import rookiesspring.mapper.ImportBillMapper;
//import rookiesspring.model.ImportBill;
//import rookiesspring.repository.ImportBillRepository;
//import rookiesspring.service.interfaces.ImportBillServiceInterface;
//
///**
// *
// * @author HP
// * @author Tamina
// */
//@Service
//@NoArgsConstructor
//@Setter
//@Getter
//public class ImportBillService implements ImportBillServiceInterface {
//
//    ImportBillRepository repository;
//    ImportBillMapper mapper;
//
//    public ImportBillService(ImportBillRepository repository, ImportBillMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    public List<ImportBillResponseDTO> getAll() {
//        List<ImportBillResponseDTO> response = new ArrayList<>();
//        List<ImportBill> list = repository.findAll();
//        for (ImportBill i : list) {
//            ImportBillResponseDTO r = mapper.ToResponseDTO(i);
//            response.add(r);
//        }
//        return response;
//    }
//
//    public ImportBillResponseDTO getOneById(long id) {
//        return mapper.ToResponseDTO(repository.findById(id).orElseThrow());
//    }
//
//    public List<ImportBillResponseDTO> getAllByName(String name) {
//        List<ImportBillResponseDTO> response = new ArrayList<>();
//        List<ImportBill> list = repository.findAllByNameContainingIgnoreCase(name);
//        for (ImportBill i : list) {
//            ImportBillResponseDTO r = mapper.ToResponseDTO(i);
//            response.add(r);
//        }
//        return response;
//    }
//
//    public ImportBillResponseDTO saveOne(ImportBillDTO dto) {
//        return mapper.ToResponseDTO(repository.save(mapper.toEntity(dto)));
//    }
//
//    public ImportBillResponseDTO updateOne(ImportBillDTO dto) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    public void deleteOne(long id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public boolean checkExist(long id) {
//        return repository.existsById(id);
//    }
//}
