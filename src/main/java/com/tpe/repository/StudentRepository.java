package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//jpa extend edildiğinde repository otomatik repo yapar ama okunabilirlik için @Repository kullanılır.
@Repository //optional
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmail(String email);

    //JpaRepository<Student,Long> ==> Student da id pk di. id Long
    //CRUD op kodları barırdırır.

}


