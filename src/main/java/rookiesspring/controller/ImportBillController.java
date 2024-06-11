///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package rookiesspring.controller;
//
//import java.util.List;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import rookiesspring.dto.ImportBillDTO;
//import rookiesspring.dto.response.ImportBillResponseDTO;
//import rookiesspring.service.ImportBillService;
//
///**
// *
// * @author HP
// * @author Tamina
// */
////@RestController
////@RequestMapping("api/v1/import-bills")
//@NoArgsConstructor
//public class ImportBillController {
//
//    @Autowired
//    private ImportBillService service;
//
//    public ImportBillController(ImportBillService service) {
//        this.service = service;
//    }
//
//    @GetMapping({"", "/", "/all"})
//    public List<ImportBillResponseDTO> getAll() {
//        return service.getAll();
//
//    }
//
//    @GetMapping("/search")
//    public List<ImportBillResponseDTO> getImportBillByName(@RequestParam("name") String name) {
//        return service.getAllByName(name);
//    }
//
//    @GetMapping("/{id}")
//    public ImportBillResponseDTO getImportBill(@PathVariable("id") long id) {
//        return service.getOneById(id);
//    }
//    
//
//    @PostMapping("/save")
//    public ImportBillResponseDTO saveImportBill(@RequestBody ImportBillDTO import_bill_dto) {
//        ImportBillResponseDTO i = service.saveOne(import_bill_dto);
//        return i;
//    }
//
////    @PostMapping("/process")
////    @Transactional
////    public ImportBillResponseDTO ProcessBill(@RequestBody() long id) {
////        ImportBillDTO result = service.getOneById(id);
////        if (result == null) {
////            throw new NoSuchElementException();
////        }
////        return service.getImportBillMapper().toImportBill(result);
//////        
//////        result.process();
//////        return service.addOne(result);
////    }
//}
