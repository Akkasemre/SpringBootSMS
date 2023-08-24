package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor // başında final olan değerleri const a alır

@Entity
public class Student {

    /*    @Getter*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // otamatik id creation işlemini hibernate e bırakır. , strategy- otamatik oalrark ,1,2,3 diye gider
    @Setter(AccessLevel.NONE)

    private Long id; // int id : default 0
    @NotNull(message = "first name can not be null ")
    @NotBlank(message = "first name can not be white space")
    @Size(min=2,max=25,message = "First name '${validatedValue}'must be between {min] and {max} long" ) // def size 255
    /*    @Getter
        @Setter*/
    @Column(nullable = false,length = 25)
    private /*final*/ String name;//boşluk,

    @Column(nullable = false, length = 25)
    private /*final*/ String lastName;

    private /*final */Integer grade;

    @Column(nullable = false,length = 25,unique = true)
    @Email(message = "Provide valid email")
    private /*final*/  String email;

    private/* final*/ String phoneNumber;

    @Setter(AccessLevel.NONE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy HH:mm:ss",timezone = "Turkey")//std_ .. 4:20:09
    //sadece json değerini formatlar (client a gidecek Json ı formatlar) db deki veriyi formatlamaz.
    private LocalDateTime createDate = LocalDateTime.now();//4:20:9:123123



}