package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController// /students //restfull itpinde server olusturdugmuz için RestController annotation kullandık
//RESTful API, iki bilgisayar sisteminin internet üzerinden güvenli bir şekilde bilgi alışverişi yapmak için kullandığı bir arabirimdir.
@RequestMapping("/students")// http://localhost:8080/students
@RequiredArgsConstructor
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);//*loglama işlemleri icin logger hazir.

    private final StudentService studentService;

    //?Not: getAll()********************
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

    // ? getByIdWithRequestParam()********************
    @GetMapping("/query") // cakısma olmaması için path belirttik http://localhost:8080/students/query?id=1 + Get
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        //@RequestParam URL'de yer alan veya form verilerindeki parametreleri almak için kullanılır

        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }
    //!!eger sadece 1 parametre olacaksa path, aynı anda birden fazla parametre varsa RequestParam kullanılması best practice

    // ? getByIdWithPath()******************** http://localhost:8080/students/1+ Get
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //? Not: delete()******************** http://localhost:8080/students/1+ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {

        studentService.deleteStudent(id);
        String message = "Student is deleted successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //? Not: update()********************
    //*@PatchMapping //! parçalı şekişde günceller. güncellenmeyen eski değerler kalır yeniler güncellenir.
    @PutMapping("/{id}")     //*bütün fieldları güncellememiz (setlemek) lazım. setlenmeyenleri (null) olarak değiştirir
    //? http://localhost:8080/students/1+ PUT + JSON
    public ResponseEntity<String> updateStudent(@PathVariable Long id,
                                                @RequestBody StudentDTO studentDTO) {

        studentService.updateStudent(id, studentDTO);
        String message = "Student is updated successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //? Not: Pageble********************
    //todo Pageable http://localhost:8080/students/page?page=0&size=10&sort=name&direction=ASC yada DCS + Get
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllWithPage(
            //zorunlu 2 opsiyonel 4 tane parametre alır
            /*zorunlu*/     @RequestParam("page") int page, //*kaçıncı sayfanın gelecegini söylüyoruz
            /*zorunlu*/     @RequestParam("size") int size,//*her page de kaç ürün olsun
            /*opsiyonel*/   @RequestParam("sort") String prop, //*hangi field a göre sıralama
            /*opsiyonel*/   @RequestParam("direction") Sort.Direction direction//*natural ordermı değilmi o bilgi veriliyor.
    ) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction, prop));
        //!(bir üstteki kod için)Best practice : bu satır burda değil service classında olur service class ında olustururz.

        //* bana spring data jpa dan gelen leri kullanmak için pageable class kullanmak lazım

        Page<Student> studentPage = studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);

    }


//? NOT: JPQL********************
    //? Task:75 puan olan tüm öğrencileri getirmek isyoruz.
    @GetMapping("/grade/{grade}") //http://localhost:8080/students/grade/75 + GET
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade){
         List<Student> students = studentService.findAllEqualsGrade(grade);

         return ResponseEntity.ok(students);
    }

    //?Not: DB den direkt DTO olarak verileri alabilirmiyim ?

     @GetMapping("/query/dto")//http://localhost:8080/students/query/dto?id + GET
        public  ResponseEntity<StudentDTO> getStudentDto(@RequestParam("id")Long id){
        StudentDTO studentDTO =studentService.findStudentDtoById(id);
        return ResponseEntity.ok(studentDTO);
     }

     //? NOT: looger icin yazildi.
    @GetMapping("/welcome")   //* http://localhost:8080/students/welcome  + GET
    public  String welcome(HttpServletRequest request){  //! request üzerinden endpointi tetikledik.
        logger.warn("-------------------------------- Welcome {}",request.getServletPath());
        return "Welcome to Student Controller";
    }
}
