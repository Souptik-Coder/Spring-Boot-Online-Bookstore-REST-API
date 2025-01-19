package com.example.BookStoreAPI.service;

import com.example.BookStoreAPI.dto.CustomerDTO;
import com.example.BookStoreAPI.model.Customer;
import com.example.BookStoreAPI.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO createCustomer(CustomerDTO customer) {
        Customer savedCustomer = customerRepository.save(modelMapper.map(customer, Customer.class));
        return  modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    public Customer registerCustomer(String firstName, String lastName, String email, String password) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        return customerRepository.save(customer);
    }
}
