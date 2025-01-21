package com.example.BookStoreAPI.controller;

import com.example.BookStoreAPI.dto.CustomerDTO;
import com.example.BookStoreAPI.model.Customer;
import com.example.BookStoreAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.createCustomer(customer);
        EntityModel<CustomerDTO> resource = EntityModel.of(savedCustomer,
                linkTo(methodOn(CustomerController.class).createCustomer(savedCustomer)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).registerCustomer(null, null, null, null)).withRel("register"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<Customer>> registerCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {

        Customer savedCustomer = customerService.registerCustomer(firstName, lastName, email, password);
        EntityModel<Customer> resource = EntityModel.of(savedCustomer,
                linkTo(methodOn(CustomerController.class).registerCustomer(firstName, lastName, email, password)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).createCustomer(null)).withRel("create"));
        return ResponseEntity.ok(resource);
    }
}
