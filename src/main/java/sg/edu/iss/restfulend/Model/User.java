//package com.example.restfulend.Model;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
//@Data
//@NoArgsConstructor
//public class User {
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;
//    @Column(length = 100)
//    private String firstName;
//    @Column(length = 100)
//    private String lastName;
//    @Column(length = 200)
//    private String address;
//    private String userName;
//    private String passWord;
//    private Boolean isVerified;
//
//}
