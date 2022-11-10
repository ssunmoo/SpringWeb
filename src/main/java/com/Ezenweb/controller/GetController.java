package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// p.56
@RestController
@RequestMapping("/api/v1/get-api") // 요청 URL 정의하기
public class GetController {

    // 1. p.57
    @RequestMapping( value = "/hello", method = RequestMethod.GET ) // /hello 주소에 있는 get 방식으로 접속(연결)하겠다
    public String getHello(){
        return "Hello World";
    }

    // 2. p.58
    @GetMapping("/name")
    public String getName(){
        return "Flature";
    }

    // 3. p.59
    @GetMapping("/variable1/{variable}") // 경로상의 변수[값] 추가 가능
    public String getVariable1( @PathVariable String variable ){
        return variable;
    }

    // 4-1. p.60
    @GetMapping("/variable2/{variable}")
    public String getVariable2( @PathVariable( "variable" ) String test ){
        return test;
    }

    // 4-2. test
    @GetMapping("/variable3")
    public String getVariable3( @RequestParam String variable ){
        return variable;
    }

    // [ 변수 1개 ] @PathVariable : http://localhost:8081/api/v1/get-api/variable2/열려라
    // [ 변수 여러개 ] @RequestParam : http://localhost:8081/api/v1/get-api/variable3?variable=열리니 왜안돼

    // 5. p.61
    @GetMapping("/requst1")
    public String getRequstParam1( @RequestParam String name, @RequestParam String email, @RequestParam String organization ){
        return name + " " + email + " " + organization;
    }
    // http://localhost:8081/api/v1/get-api/requst1?name=dd&emai=dd&organization=dd

    // 6. p.62
    @GetMapping("/requst2")
    public String getRequstParam2(@RequestParam Map< String, String > param ){
        StringBuilder sb = new StringBuilder();
        param.entrySet().forEach( map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
        return sb.toString();
    }
    // http://localhost:8081/api/v1/get-api/requst2?key1=aa&key2=aa&key3=aa

    // 7. p.66
    @GetMapping("/requst3")
    public String getRequstParam3(MemberDto dto ){
        return dto.toString();
    }
    // http://localhost:8081/api/v1/get-api/requst3?name=aa&email=aa&organization=aa


}
