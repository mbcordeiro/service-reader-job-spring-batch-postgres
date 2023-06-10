package com.matheuscordeiro.servicereaderjob.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.matheuscordeiro.servicereaderjob.domain.ResponseUser;
import com.matheuscordeiro.servicereaderjob.domain.User;

@Component
public class UserReader implements ItemReader<User> {
	private RestTemplate restTemplate = new RestTemplate();
	private int page = 1;
	private List<User> users = new ArrayList<>();
	private int userIndex = 0;
	private int total = 0;

	@Value("${chunkSize}")
	private int chunkSize;

	@Value("${pageSize}")
	private int pageSize;

	@Value("${limit}")
	private int limit;

	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		User user;

		if (userIndex < users.size()) {
			user = users.get(userIndex);
		} else {
			user = null;
		}

		userIndex++;
		return user;
	}

	private List<User> fetchUserDataFromApi() {
		final var response = restTemplate
				.getForEntity(String.format("https://gorest.co.in/public/v1/users?page=%d", page), ResponseUser.class);
		final var userData = response.getBody().getData();
		return userData;
	}

	@BeforeChunk
	private void beforeChunk(ChunkContext context) {
		for (int i = 0; i < chunkSize; i += pageSize) {
			if (total >= limit) {
				return;
			}
			users.addAll(fetchUserDataFromApi());
			total += users.size(); 
			page++;
		}
	}

	@AfterChunk
	private void afterChunk(ChunkContext context) {
		System.out.println("End Chunk");
		userIndex = 0;
		users = new ArrayList<>();
	}

}