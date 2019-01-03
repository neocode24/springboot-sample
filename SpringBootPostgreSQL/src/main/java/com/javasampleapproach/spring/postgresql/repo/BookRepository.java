package com.javasampleapproach.spring.postgresql.repo;

import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.spring.postgresql.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
