/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.RateDTO;
import rookiesspring.model.User;
import rookiesspring.service.RateService;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/api/v1/rates")
public class RateController {
    private RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }
    
    @GetMapping("")
    public ResponseEntity rateProduct(@RequestParam(value = "product_id", required = true) long product_id){
        return ResponseEntity.ok().body(rateService.getAllRating(product_id));
    } 
    @PostMapping("")
    public ResponseEntity rateProduct(Authentication auth, @Valid @RequestBody() RateDTO rate_dto) {
        var user = (User) auth.getPrincipal();
        return ResponseEntity.ok(Util.message(Double.toString(rateService.rateProduct(rate_dto, user))));
    }
}
