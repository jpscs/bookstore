package com.bookstore.rest;


import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@Path("/books")
public class BookEndpoint {
	@Inject
	private BookRepository bookRepository;

	@GET
	@Path("/count")
	public Response countAllBooks() {
		//Long nbrOfBooks = bookRepository.countAll();
		return Response.ok(100).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("id") @Min(1)Long id) {
		Book book = bookRepository.find(id);
		return Response.ok(book).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Book createBook(Book book) {
		return bookRepository.create(book);
	}

	@DELETE
	@Path("/{id}")
	public void deleteBook(Long id) {
		bookRepository.delete(id);
	}

/*	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllBooks() {		
		List<Book> books = bookRepository.findAll();
		
		if(books.size()==0)
			return Response.noContent().build();
		
		return Response.ok(books).build();
	}*/



}
