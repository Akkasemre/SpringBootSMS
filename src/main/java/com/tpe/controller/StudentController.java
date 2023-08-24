package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController// /students //restfull itpinde server olusturdugmuz için RestController annotation kullandık
//RESTful API, iki bilgisayar sisteminin internet üzerinden güvenli bir şekilde bilgi alışverişi yapmak için kullandığı bir arabirimdir.
@RequestMapping("/students")// http://localhost:8080/students
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    //?Not: getAll()
    @GetMapping// http://localhost:8080/students + Get
    public ResponseEntity<List<Student>> getAll() {  //status olmassa olmaz dır responseentity ile status de setleyebilmemiz
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);//default olarak 200(işlem başarılı ile gerçekleşince) + students status code gönderir.
        //return new ResponseEntity <>/students,HttpStatus.CREATED.ok) yukardaki ile aybı işlevi var
        // aşşada bunu kullanmamızın sebebi status olarak 201 yani yeni bit veri olusturdugu için kullandık

    }

    //?Not: createStudent()****************************
    @PostMapping//http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String, String>> createStudent(@RequestBody @Valid Student student)/*req adisinden gelen json dosyayı core java tarafında student class o türünde maple demek*/ {
        //@Valid mapleyeceğimiz class da annotation var ise onları da alır
        //otomatik olarak jackson mapler
        studentService.createStudent(student);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Student is created successfully");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);//map + 201
    }

    // ? getByIdWithRequestParam()
    @GetMapping("/query") // cakısma olmaması için path belirttik http://localhost:8080/students/query?id=1 + Get
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        //@RequestParam URL'de yer alan veya form verilerindeki parametreleri almak için kullanılır

        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }
    //!!eger sadece 1 parametre olacaksa path, aynı anda birden fazla parametre varsa RequestParam kullanılması best practice

    // ? getByIdWithPath() http://localhost:8080/students/1+ Get
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //? Not: delete() http://localhost:8080/students/1+ DELETE
   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){

        studentService.deleteStudent(id);
        String message = "Student is deleted successfully";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

  /*  //? Not: update()
    *//*@PatchMapping*//* //! parçalı şekişde günceller. güncellenmeyen eski değerler kalır yeniler güncellenir.
    @PutMapping("{/id}")     //bütün fieldları güncellememiz (setlemek) lazım. setlenmeyenleri null olarak değiştirir
    //? http://localhost:8080/students/1+ PUT + JSON
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @RequestBody Student student){

    }
*/


}
