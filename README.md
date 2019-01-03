<!doctype html>
<html>
<head>
<meta charset='UTF-8'><meta name='viewport' content='width=device-width initial-scale=1'>
<title>20190103 - Spring Boot Simple Course</title></head>
<body><h2>0. Spring Boot ? (5분)</h2>
<ol start='' >
<li>Web으로 제공</li>
<li>SpringFramework 은 이제 고인물</li>
<li>좀더 쉽게 접근하자...</li>
<li>Web Application Server 의 기능이 모두 다 필요 할까?</li>

</ol>
<h2>1. Spring Boot 개발 툴 설치 (10분 ~ 15분)</h2>
<ol start='' >
<li>Spring Tool Suite (이하 STS) 다운로드 및 설치</li>

</ol>
<pre><code>https://spring.io/tools
</code></pre>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103105000119.png' alt='image-20190103105000119' referrerPolicy='no-referrer' /></p>
<h2>2. Project 생성하기 (10분 ~ 15분)</h2>
<ol start='' >
<li><p>Spring Starter Project 실행</p>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103105253638.png' alt='image-20190103105253638' referrerPolicy='no-referrer' /></p>
</li>
<li><p>Project 초기 설정</p>
<ul>
<li>Name 지정하고, type으로 Maven 지정. Java 버전은 8로 함.</li>
<li>Packaging은 Jar 또는 War 무관.</li>

</ul>
</li>

</ol>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103105827688.png' alt='image-20190103105827688' referrerPolicy='no-referrer' /></p>
<ol start='3' >
<li><p>Project Dependencies 지정</p>
<ul>
<li><p>주요 Spring Boot Starter 지정</p>
</li>
<li><p>Web 기반에서는 Web만 지정.</p>
</li>
<li><p>그외 Starter 내용들</p>
<ul>
<li><p>Actuator : Spring Boot Application 상태 관리 기능.</p>
<pre><code>https://supawer0728.github.io/2018/05/12/spring-actuator/
</code></pre>
</li>
<li><p>DevTools : 편리한 개발환경 세팅을 제공하기 위한 용도. automatic restart, livereload 기능 등..</p>
<pre><code>http://haviyj.tistory.com/11
</code></pre>
</li>
<li><p>Mybatis, PostgreSQL, RabiitMQ : DB 및 Queue 저장소 관련 기능</p>
</li>
<li><p>Security : 기존 Spring Security 관련한 인증 및 권한 기능</p>
<pre><code>http://heowc.tistory.com/4
</code></pre>
</li>

</ul>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103110952674.png' alt='image-20190103110952674' referrerPolicy='no-referrer' /></p>
</li>

</ul>
</li>

</ol>
<h2>3. Project 기능 개발 하기 (20분 ~ 30분)</h2>
<ol start='' >
<li><p>Project 탐색 하기</p>
<ul>
<li><p>src/main/java : 개발 코드 위치</p>
</li>
<li><p>src/main/resource : 정적 또는 설정 관련 파일 위치</p>
</li>
<li><p>src/test/java : 테스트 코드 위치</p>
</li>
<li><p>Maven Dependencies : pom.xml 에 기술된 필요 연관 Library등의 관계 확인. Project에서 실제 참조하고 있는 내용.</p>
</li>
<li><p>mvnw : Maven Wrapper</p>
</li>
<li><p>pom.xml : Project Object Model. </p>
<pre><code>https://araikuma.tistory.com/447
</code></pre>
</li>
<li><p>***Application.java : Start Class</p>
</li>

</ul>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103112203419.png' alt='image-20190103112203419' referrerPolicy='no-referrer' /></p>
</li>
<li><p>코드 생성하기</p>
<ul>
<li><p>Controller - BookController.java</p>
<pre><code>package com.javasampleapproach.spring.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.spring.postgresql.model.Book;
import com.javasampleapproach.spring.postgresql.repo.BookRepository;

@CrossOrigin(origins = &quot;http://localhost:8081&quot;)
@RestController
@RequestMapping(&quot;/api&quot;)
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@GetMapping(&quot;/books&quot;)
	public List&lt;Book&gt; getAllBooks() {
		System.out.println(&quot;Get all Books...&quot;);

		List&lt;Book&gt; list = new ArrayList&lt;&gt;();
		Iterable&lt;Book&gt; customers = bookRepository.findAll();

		customers.forEach(list::add);
		return list;
	}

	@PostMapping(&quot;/books/create&quot;)
	public Book createBook(@Valid @RequestBody Book book) {
		System.out.println(&quot;Create Book: &quot; + book.getTitle() + &quot;...&quot;);

		return bookRepository.save(book);
	}

	@GetMapping(&quot;/books/{id}&quot;)
	public ResponseEntity&lt;Book&gt; getBook(@PathVariable(&quot;id&quot;) Long id) {
		System.out.println(&quot;Get Book by id...&quot;);

		Optional&lt;Book&gt; bookData = bookRepository.findById(id);
		if (bookData.isPresent()) {
			return new ResponseEntity&lt;&gt;(bookData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity&lt;&gt;(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(&quot;/books/{id}&quot;)
	public ResponseEntity&lt;Book&gt; updateBook(@PathVariable(&quot;id&quot;) Long id, @RequestBody Book book) {
		System.out.println(&quot;Update Book with ID = &quot; + id + &quot;...&quot;);

		Optional&lt;Book&gt; bookData = bookRepository.findById(id);
		if (bookData.isPresent()) {
			Book savedBook = bookData.get();
			savedBook.setTitle(book.getTitle());
			savedBook.setAuthor(book.getAuthor());
			savedBook.setDescription(book.getDescription());
			savedBook.setPublished(book.getPublished());

			Book updatedBook = bookRepository.save(savedBook);
			return new ResponseEntity&lt;&gt;(updatedBook, HttpStatus.OK);
		} else {
			return new ResponseEntity&lt;&gt;(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(&quot;/books/{id}&quot;)
	public ResponseEntity&lt;String&gt; deleteBook(@PathVariable(&quot;id&quot;) Long id) {
		System.out.println(&quot;Delete Book with ID = &quot; + id + &quot;...&quot;);

		try {
			bookRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity&lt;&gt;(&quot;Fail to delete!&quot;, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity&lt;&gt;(&quot;Book has been deleted!&quot;, HttpStatus.OK);
	}
}

</code></pre>
</li>
<li><p>Model - Book.java</p>
<pre><code>package com.javasampleapproach.spring.postgresql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = &quot;book&quot;)
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = &quot;title&quot;)
	private String title;

	@Column(name = &quot;author&quot;)
	private String author;

	@Column(name = &quot;description&quot;)
	private String description;

	@Column(name = &quot;published&quot;)
	private int published;

	protected Book() {
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return &quot;Book [id=&quot; + id + &quot;, title=&quot; + title + &quot;, author=&quot; + author + &quot;, description=&quot; + description
				+ &quot;, published=&quot; + published + &quot;]&quot;;
	}

}

</code></pre>
</li>
<li><p>Repository - BookRepository.java</p>
<pre><code>package com.javasampleapproach.spring.postgresql.repo;

import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.spring.postgresql.model.Book;

public interface BookRepository extends CrudRepository&lt;Book, Long&gt; {

}

</code></pre>
</li>
<li><p>Properties - application.properties</p>
<pre><code>spring.datasource.url=jdbc:postgresql://localhost/testdb
spring.datasource.username=postgres
spring.datasource.password=123
spring.jpa.generate-ddl=true

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
</code></pre>
</li>

</ul>
</li>

</ol>
<ol start='3' >
<li><p>Project 응용 하기</p>
<ul>
<li><p>이전 Project를 호출하는 BFF(Back-end for Front-end) 서비스 Project 새로 만들기</p>
</li>
<li><p>Controller - FrontController.java</p>
<pre><code>package com.javasampleapproach.spring.frontend.controller;

import com.javasampleapproach.spring.frontend.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(&quot;/api&quot;)
public class FrontController {

    @Autowired
    RestTemplate restTemplate;


    @Value(&quot;${target.service.name}&quot;)
    private String targetServiceName;


    @GetMapping(&quot;/books&quot;)
    public List&lt;Book&gt; getAllBooks() {
        System.out.println(&quot;Get all Books in FrontEnd&quot;);

        String url = &quot;http://&quot; + targetServiceName + &quot;/api/books&quot;;

        System.out.println(&quot;Request Uri: &quot; + url);

//        return restTemplate.postForObject(url, null, List.class);
        List&lt;Book&gt; resultList = restTemplate.getForObject(url, List.class);

        System.out.println(&quot;Response Data: &quot; + resultList.toString());

        return resultList;
    }
}

</code></pre>
</li>
<li><p>Model - Book.java</p>
<pre><code>package com.javasampleapproach.spring.frontend.model;

import java.io.Serializable;

public class Book implements Serializable {

    private long id;

    private String title;

    private String author;

    private String description;

    private int published;

    protected Book() {
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }
}

</code></pre>
</li>
<li><p>Main - Application.java</p>
<pre><code>package com.javasampleapproach.spring.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {

		return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(3 * 1000))
				.setReadTimeout(Duration.ofSeconds(30 * 1000))
				.build();
	}
}


</code></pre>
</li>
<li><p>Properties - application.properties</p>
<pre><code>target.service.name=spring-boot-1
</code></pre>
</li>

</ul>
</li>

</ol>
<h2>4. Maven 사용하기 (5분)</h2>
<ol start='' >
<li><p>Maven 설치</p>
<ol start='' >
<li><p>MacOS </p>
<pre><code>brew install maven
</code></pre>
</li>
<li><p>그 외 : <code>http://maven.apache.org/download.cgi</code> 설치 후 path 설정</p>
<pre><code>https://zetawiki.com/wiki/윈도우_메이븐_설치
</code></pre>
</li>

</ol>
</li>
<li><p>Maven 명령어</p>
<ol start='' >
<li><p><code>compile</code> : 컴파일 수행.</p>
</li>
<li><p><code>package</code> : 컴파일 결과를 패키징. pom 에 명시한 옵션에 따라 수행. ㅇㅇㅇ-0.0.1-SNAPSHOT.jar 로 생성 됨. 아래 내용 참조 함.</p>
<pre><code>&lt;groupId&gt;com.javasampleapproach&lt;/groupId&gt;
&lt;artifactId&gt;spring-boot-postgresql&lt;/artifactId&gt;
&lt;version&gt;0.0.1-SNAPSHOT&lt;/version&gt;
&lt;packaging&gt;jar&lt;/packaging&gt;
</code></pre>
</li>
<li><p><code>clean</code> : 빌드되어 만들어진 산출물(결과)를 모두 제거 함.</p>
</li>
<li><p><code>install</code> : 로컬 저장소에 저장</p>
</li>
<li><p><code>test</code> : 테스트 클래스 실행</p>
</li>
<li><p><code>mvn clean package -DMaven.test.skip=true</code> : 이전 결과를 제거하고, 새로 빌드하지만, 테스트 수행은 생략 함.</p>
</li>

</ol>
</li>

</ol>
<p>&nbsp;</p>
<h2>5. 실행 및 디버깅 (5분)</h2>
<ol start='' >
<li><p>STS 내부 실행</p>
<ul>
<li>STS 출력에서 결과 제공</li>
<li><code>Debug As</code> 실행 시에는 Break point 설정으로 문제 원인 해결에 사용</li>
<li>STS 에서 제공하는 다양한 Runtime 옵션등 사용</li>

</ul>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103140045983.png' alt='image-20190103140045983' referrerPolicy='no-referrer' /></p>
</li>
<li><p>Console 실행</p>
<ul>
<li><p>결과물 (ㅇㅇㅇ.jar 또는 ㅇㅇㅇ.war)에 대한 직접 실행하는 방법.</p>
</li>
<li><p>최종 결과물을 만들어서 실행하는 형태.</p>
</li>
<li><p>-D 옵션으로 <code>application.properties</code> 내용을 실행 시점에 변경 할 수 있음. Docker Image 생성 이후에 전달 하는 방법에서 유용 할 것 으로 생각함.</p>
<p>예) 원격 서비스명을 IP가 아닌 변수 처리 하여, kubenetes 서비스 명으로 대치.</p>
<p><img src='20190103 - Spring Boot Simple Course.assets/image-20190103140437770.png' alt='image-20190103140437770' referrerPolicy='no-referrer' /></p>
</li>

</ul>
</li>

</ol>
<p>&nbsp;</p>
</body>
</html>
