package com.customer.controller;

import com.customer.model.customer.Customer;
import com.customer.model.customer.CustomerForm;
import com.customer.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Value("${upload-file}")
    private String fileUpload;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customerService.getAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm", customerForm);
        return "/customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customerForm") CustomerForm customerForm) {
        MultipartFile file = customerForm.getAvatar();
        String fileName = file.getOriginalFilename();
        Customer customer = new Customer(customerForm.getId(), customerForm.getFirstName(), customerForm.getLastName(), fileName);
        customerService.save(customer);
        try {
            byte[] bytes = file.getBytes();
            File file1 = new File(fileUpload + fileName);
            FileCopyUtils.copy(bytes, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showCreate(@PathVariable("id") Long id) {
        Customer customer = customerService.getById(id);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/info/{id}")
    public ModelAndView showInfo(@PathVariable("id") Long id) {
        Customer customer = customerService.getById(id);
        ModelAndView modelAndView = null;
        if (customer == null) {
            modelAndView = new ModelAndView("404");
        } else {
            modelAndView = new ModelAndView("customer/info");
            modelAndView.addObject("customer", customer);
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("customer/delete");
        Customer customer = customerService.getById(id);
        if (customer == null) {
            modelAndView = new ModelAndView("404");
        } else {
            modelAndView.addObject("customer", customer);
        }
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
        return "redirect:/customer";
    }

}
