package controller;

import model.Customer;
import service.CustomerServiceImpl;
import service.ICustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Customer", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
	private ICustomerService customerService = new CustomerServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action == null)
			action = "";

		switch (action){
			case "create":
				createCustomer(req, resp);
				break;

			case "edit":
				updateCustomer(req, resp);
				break;

			case "delete":
				deleteCustomer(req, resp);
				break;

			default:
				break;
		}
	}

	private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Customer customer = this.customerService.findByID(id);
		RequestDispatcher dispatcher;
		if(customer == null){
			dispatcher = req.getRequestDispatcher("error-404.jsp");
		} else {
			this.customerService.remove(id);
			try {
				resp.sendRedirect("/customers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name").trim();
		String email = req.getParameter("email").trim();
		String address = req.getParameter("address").trim();

		Customer customer = this.customerService.findByID(id);
		RequestDispatcher dispatcher;

		if(customer == null || name == null || email == null || address == null ||
			name.length() == 0 || email.length() == 0 || address.length() == 0){
			dispatcher = req.getRequestDispatcher("error-404.jsp");
		} else {
			if(name == null || email == null || address == null ||
				name.length() == 0 || email.length() == 0 || address.length() == 0)
			{
				req.setAttribute("message", "Please enter all fields");
			}else{
				customer.setName(name);
				customer.setEmail(email);
				customer.setAddress(address);
				req.setAttribute("message", "Customer information was updated");
			}
			req.setAttribute("customer", customer);
			dispatcher = req.getRequestDispatcher("customer/edit.jsp");
		}

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("name").trim();
		String email = req.getParameter("email").trim();
		String address = req.getParameter("address").trim();
		int id = (int)(Math.random() * 10000);

		RequestDispatcher dispatcher = req.getRequestDispatcher("customer/create.jsp");

		if (name == null || name.length() == 0 ||
			email == null || email.length() == 0 ||
			address == null || address.length() == 0
		){
			req.setAttribute("message", "Please enter all fields");
		} else {
			Customer customer = new Customer(id, name, email, address);
			this.customerService.save(customer);
			req.setAttribute("message", "New customer was created");
		}

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action == null)
			action = "";

		switch (action){
			case "create":
				showCreateForm(req, resp);
				break;

			case "edit":
				showEditForm(req, resp);
				break;

			case "delete":
				showDeleteForm(req, resp);
				break;

			case "view":
				showViewForm(req, resp);

				break;

			default:
				listCustomers(req, resp);
				break;
		}
	}

	private void showViewForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Customer customer = this.customerService.findByID(id);
		RequestDispatcher dispatcher;
		if(customer == null){
			dispatcher = req.getRequestDispatcher("error-404.jsp");
		} else {
			req.setAttribute("customer", customer);
			dispatcher = req.getRequestDispatcher("customer/view.jsp");
		}
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));

		Customer customer = this.customerService.findByID(id);

		RequestDispatcher dispatcher;

		if (customer == null) {
			dispatcher = req.getRequestDispatcher("error-404.jsp");
		}else{
			dispatcher = req.getRequestDispatcher("customer/delete.jsp");
		}

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Customer customer = this.customerService.findByID(id);
		RequestDispatcher dispatcher;
		if (customer == null) {
			dispatcher = req.getRequestDispatcher("error-404.jsp");
		}else{
			req.setAttribute("customer", customer);
			dispatcher = req.getRequestDispatcher("customer/edit.jsp");
		}

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher dispatcher = req.getRequestDispatcher("customer/create.jsp");

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listCustomers(HttpServletRequest req, HttpServletResponse resp) {
		List<Customer> customers = customerService.findAll();
		req.setAttribute("customers", customers);

		RequestDispatcher dispatcher = req.getRequestDispatcher("customer/list.jsp");

		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
