package com.example.Rabota.Controller;


import com.example.Rabota.Models.Car;
import com.example.Rabota.Models.Employee;
import com.example.Rabota.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
public String GetRab(Model model)
    {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees",employees);
        return "Main";
    }
    @GetMapping("/Add/Employee")
    public String GetAddEmployee(Model model)
    {
        return "Add-Employee";
    }
    @PostMapping("/Add/Employee")
    public String blogPostAdd(@RequestParam(value="lastname") String lastname,
                              @RequestParam(value ="name" ) String name,
                              @RequestParam(value = "maiddlename") String middlename,
                              @RequestParam(value ="birthday" ) Date birthday,
                              @RequestParam(value = "expetienxs") int expetienxs,
                              Model model)
    {
        Employee employee = new Employee(lastname,name,middlename, birthday,expetienxs);
        employeeRepository.save(employee);
        return "redirect:/";
    }


    @GetMapping( path = "/Search/Employee")
    public String blogFilter(Model model)
    {
        return "Search-Employee";
    }

    @PostMapping("/Search/Employee-result")
    public String blogResult(@RequestParam String lastname, Model model)
    {
        List<Employee> emp = employeeRepository.findByLastname(lastname);
        model.addAttribute("result", emp);
        return "Search-Employee";
    }
    @PostMapping("/Search/Employee")
    public String simpleSearch(@RequestParam String lastname, Model model)
    {
        List<Employee> emp = employeeRepository.findByLastnameContains(lastname);
        model.addAttribute("result", emp);
        return "Search-Employee";
    }
    @GetMapping("/blog/Employee/{id}/Edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model){
        if(!employeeRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Employee> employee= employeeRepository.findById(id);
        ArrayList<Employee> lis = new ArrayList<>();
        employee.ifPresent(lis::add);
        model.addAttribute("employee", lis);
        return "Edit-Employee";

    }
    @PostMapping("/blog/Employee/{id}/Edit")
    public String blogPosyUpdate(
            @PathVariable(value = "id") Long id,
            @RequestParam(value="lastname") String lastname,
            @RequestParam(value ="name" ) String name,
            @RequestParam(value = "maiddlename") String middlename,
            @RequestParam(value ="birthday" ) Date birthday,
            @RequestParam(value = "expetienxs") int expetienxs,
                                 Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setLastname(lastname);
        employee.setName(name);
        employee.setMiddlename(middlename);
        employee.setBirthday(birthday);
        employee.setExpetienxs(expetienxs);
        employeeRepository.save(employee);
        return "redirect:/";



    }
    @PostMapping("/blog/Employee/{id}/Remove/")
    public String blogEmployeeDelete(
            @PathVariable(value = "id") Long id,
            Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/";
    }
    @GetMapping("/blog/Employee/{id}")
    public String CarDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        ArrayList<Employee> lis = new ArrayList<>();
        employee.ifPresent(lis::add);
        model.addAttribute("employee", lis);
        return "blog-EmployeeDetails";

    }

}
