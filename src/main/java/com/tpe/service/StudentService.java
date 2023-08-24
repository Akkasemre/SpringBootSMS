package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//optional degil
@RequiredArgsConstructor // final ile setlenen değişkenleri otomatik const create eder.
public class StudentService {

//    @Autowired//çok tavsiye edilen değil ama kullanılabilir.

    private final StudentRepository studentRepository;

    //Not: getAll()
    public List<Student> getAll() {
        return studentRepository.findAll(); // SELECT * from student ;
    }
    //Arr kullanmıyoruz çünkü Arr kullanmak için lenght bilgisine ihtiyacımız olur bu da ya fazla alan kullanmasına , ya da alanının yetersiz kalmasınına sebep olur.
    //........ null ..

    //create student
    public void createStudent(Student student) {
        //!email conflict(cakısma) control
        if (studentRepository.existsByEmail(student.getEmail())) /* id kısmını silip baş harfi büyük şekilde istediğimiz filedi yazdık Email*/ {
            throw new ConflictException("Email is already exist");
        }
        studentRepository.save(student);
    }
    //existByGrade(70) gibi  DATA CPA bu işe yapar.


    // ? getByIdWithRequestParam()
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id :" + id));
        //optional type return eder. nullPointer hatası almamak için nullPointerException fırlatmaz.

    }

    //? Not: delete()
    public void deleteStudent(Long id) {
        Student student = findStudent(id);

        studentRepository.delete(student);

//        studentRepository.deleteById(id); cte fırlatmaz rte de fırlatmaz .

    }
}
