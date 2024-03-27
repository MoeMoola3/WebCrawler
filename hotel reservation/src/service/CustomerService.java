package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.Customer;

public class CustomerService {
    private static final Map <String, Customer>  customers  = new HashMap<>();
    private static final CustomerService INSTANCE = new CustomerService();

    private CustomerService() {}

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
        //{ "john.doe@example.com" : obj }
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}

