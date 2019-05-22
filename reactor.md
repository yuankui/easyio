# Integrate with Reactor
 
```java
@Runwith(ReactorHttpRunner.class)
@RemoteApp("some.other.app.name")
interface DemoClient {
	@Get("/api/students/{classId}")
	Flux<Student> getStudents(@Path("classId") Integer classId);
	
	@Get("/api/classInfo/{classId}")
	Mono<ClassInfo> getClassInfo(@Path("classId") Integer classId);
}

@Controller
public class DemoController {

	@Autowired
	private DemoClient demoClient;

	@GetMapping("/students/{classId}")
	public Flux<ClassInfo> students(@PathVariable("classId") Integer classId) {
		val students = demoClient.getStudents(classId);
		val classInfo = demoClient.getClassInfo(classId).CollectToList();

		return classInfo.zip(students)
			.map(tuple1 -> {
				val cl = tuple1._1();
				val st = tuple1._2();
				cl.setStudents(st);
				return cl;
			})
	}
}
```


# Refer
- [SpringBoot WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)
- http://reactive-streams.io/
