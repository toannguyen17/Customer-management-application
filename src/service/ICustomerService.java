package service;

import model.Customer;

import java.util.List;

public interface ICustomerService {
	List<Customer> findAll();

	Customer findByID(int id);

	void save(Customer customer);
	void update(int id, Customer customer);
	void remove(int id);
}
