package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//jpa extend edildiğinde repository otomatik repo yapar ama okunabilirlik için @Repository kullanılır.
@Repository //optional
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);


    //JpaRepository<Student,Long> ==> Student da id pk di. id Long
    //CRUD op kodları barırdırır.


    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")//Student s :pGrade==> parametredeki grade demek
    // @Query("SELECT s FROM Student s WHERE s.grade=:pGrade") ==> hql
    //@Query(value = "SELECT * FROM Student s WHERE s.grade=:pGrade",nativeQuery = true)//native SQL
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    //! niye HQL yerine JPQL kullanmalıyım ? jpa ile uyumlu oldugundan.
    //! hql  sadece hibernate ile uyumlu.
    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id")
    //*SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id  ==> StudentDTO st1 = new StudentDTO(Student)
    //* DTO class ındaki constructor ı  JPQL kullanarak pojo yu DTO ya çevirdik.
    //* funFact(sıkıysa sql e new yazın /.mirac hoca)
    Optional<StudentDTO> findStudentDtoById(@Param("id")Long id);
}


