package com.teste.dependencia;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class DependenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DependenciaApplication.class, args);
//		new MigracaoUsuario(new FileReader(), new BdWriter()).migrar();
	}

	@Bean
	ApplicationRunner runner(MigracaoUsuario migracaoUsuario) {
		return args -> migracaoUsuario.migrar();
	}
}



@Component
class MigracaoUsuario {
	Reader<User> reader; // = new FileReader();
	Writer<User> write; // = new BdWriter();

	public MigracaoUsuario(Reader<User> reader,Writer<User> write) {
		this.reader = reader;
		this.write = write;
	}

	void migrar() {
		// Ler usuários de A
		List<User> users = reader.read();
		// Escrever usuários em B
		write.write(users);

	}

}

record User(String email, String username, String password) { }

interface Reader<T> {
	List<T> read();
}

interface Writer<T> {
	void write(List<T> itens);
}

@Component
class FileReader implements Reader{
//	List<User> read() {
//		System.out.println("lendo usuários do arquivo...");
//		return List.of(new User("lucas@gmail.com","lucas","123456"));
//	}
	@Override
	public List read() {
		System.out.println("lendo usuários do arquivo...");
		return List.of(new User("lucas@gmail.com","lucas","123456"));
	}
}

@Component
class BdWriter implements Writer{
//	void write(List<User> users) {
//		System.out.println("Escrevendo usuários no banco...");
//		System.out.println(users);
//
//	}
	@Override
	public void write(List itens) {
		System.out.println("Escrevendo usuários no banco...");
		System.out.println(itens);
	}
}



//mvn spring-boot:run