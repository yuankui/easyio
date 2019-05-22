# reactor usage of coming `ReactorHttpRunner` with [SpringBoot WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)
 
```
@Runwith(ReactorHttpRunner.class)
@RemoteApp("some.other.app.name")
interface DemoClient {
	Flux<Student> getStudents(Integer classId);
	Mono<ClassInfo> getClassInfo(Integer classId);
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
