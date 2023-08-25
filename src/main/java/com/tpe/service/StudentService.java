package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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


    // ? getByIdWithRequestParam()********************
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id :" + id));
        //optional type return eder. nullPointer hatası almamak için nullPointerException fırlatmaz.

    }

    //? Not: delete()********************
    public void deleteStudent(Long id) {
        Student student = findStudent(id);

        studentRepository.delete(student);

//        studentRepository.deleteById(id); cte fırlatmaz rte de fırlatmaz .

    }

    public void updateStudent(Long id, StudentDTO studentDTO) {
        //! id li ögrenci var mı ?
        Student student = findStudent(id);
        //! email enique mi ?
        /*
                1) mevcut email : mrc,  yeni : mrc -->TRUE
                2) mevcut email: mrc,   yeni: ahmt(DB de zaten var ) --> FALSE
                3) mevcut email :mrc, yeni : mhmt (DB de yok) --> TRUE

                !!interview sorusu
                !iki datayı karşılaştırırken == ile equal arasındaki fark edir
                !equals value
                !== referansada bakar
         */
        boolean emailExist =studentRepository.existsByEmail(studentDTO.getEmail());
        if(emailExist && !studentDTO.getEmail().equals(student.getEmail())){
            //? ikinci durum için bir exception
            throw new ConflictException("Email is already exist!");
        }
        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);

    }

    //? Not: Pageble********************
    public Page<Student> getAllWithPage(Pageable pageable) {

       return studentRepository.findAll(pageable);
    }

    //? NOT: JPQL********************
    public List<Student> findAllEqualsGrade(Integer grade) {

        return studentRepository.findAllEqualsGrade(grade);
    }

    //?Not: DB den direkt DTO olarak verileri alabilirmiyim ?
    public StudentDTO findStudentDtoById(Long id) {
        return studentRepository.findStudentDtoById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id: " + id)); //opsiyonel istiyoruz
    }
}
