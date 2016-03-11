package com.saroj.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

//@Component
//class DummyDataCLR implements CommandLineRunner{
//
//	@Autowired
//	private UserRepository userRepo;
//
//	@Override
//	public void run(String... strings) throws Exception {
//		Stream.of("saroj","kumar", "rout").forEach(n -> this.userRepo.save(new User(n)));
//	}
//}

@RepositoryRestResource
interface UserRepository extends JpaRepository<User, Long>{
	@RestResource(path="by-name")
	Collection<User> findByUserName(@Param("name") String name);
}
@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

	@Bean
	CommandLineRunner commandLine(UserRepository userRepo){
		return strings -> {
			Stream.of("saroj","kumar", "rout", "Pani").forEach(n -> userRepo.save(new User(n)));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}

@RestController
class UserController{

	@Value("${message}")
	private String msg;

	@RequestMapping("/hello")
	public @ResponseBody String hello(){
		return this.msg;
	}

}

@Entity
class User{
	@Id
	@GeneratedValue
	private Long id;
	private String userName;

	public User(String un) {
		this.userName = un;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				'}';
	}
}