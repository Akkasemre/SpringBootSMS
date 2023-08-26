package com.tpe.domain;

import com.tpe.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)//yapi string olarak gelsin
    @Column(length = 30, nullable = false)
    private UserRole name; //Admin, admin, A D M I N  (String yazamayız)

    public String toString(){


        return "Role [name ="+ name +"]";
    }
}
