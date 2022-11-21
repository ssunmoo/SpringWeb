package com.Ezenweb.domain.test.연관객체2;

import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@ToString
public class 제품 {

    String 제품명;

    @OneToMany(mappedBy = "제품", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<이미지> 이미지list = new ArrayList<>();

}
