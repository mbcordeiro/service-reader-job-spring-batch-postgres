package com.matheuscordeiro.servicereaderjob.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.matheuscordeiro.servicereaderjob.domain.User;

@Component
public class UserReader implements ItemReader<User> {

	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return null;
	}
  
}