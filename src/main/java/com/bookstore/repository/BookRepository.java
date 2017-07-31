package com.bookstore.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bookstore.model.Book;
import com.bookstore.util.NumberGenerator;
import com.bookstore.util.TextUtil;

@Transactional(TxType.SUPPORTS)
public class BookRepository {
	
	@PersistenceContext(unitName = "BookStore")
	private EntityManager em;
	
	@Inject
	private TextUtil textUtil;
	@Inject
	private NumberGenerator numberGenerator;
	
	public Book find(@NotNull @Min(1)Long id){
		return em.find(Book.class, id);
	}
	
	@Transactional(TxType.REQUIRED)
	public Book create(@NotNull @Valid Book book){
		book.setTitle(textUtil.sanitize(book.getTitle()));
		em.persist(book);
		return book;
	}
	
	@Transactional(TxType.REQUIRED)
	public void delete(@NotNull @Min(1) @Max(999999) Long id){
		em.remove(em.getReference(Book.class, id));
	}
	
	public List<Book> findAll(){
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.title DESC", Book.class);
		return query.getResultList();
	}
	
	public Long countAll(){
		TypedQuery<Long> query = em.createQuery("SELECT COUNT (b) FROM Book b", Long.class);
		return query.getSingleResult();
	}

}
