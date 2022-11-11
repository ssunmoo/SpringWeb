package com.Ezenweb.domain.dao;

import com.Ezenweb.domain.dto.BoardDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    public BoardDao() {
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/springweb",
                    "root",
                    "1234");
        }catch (Exception e){ System.out.println("연동실패");}
    }
    // 1. 게시물 등록 SQL
    public boolean setboard( BoardDto boardDto ){
        String sql = "insert into board( btitle , bcontent ) values(?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, boardDto.getBtitle());
            ps.setString(2, boardDto.getBcontent());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println(e);
        } return false;
    }

    // 2. 모든 게시물 리스트 출력
    public ArrayList <BoardDto> getboards(){

        ArrayList< BoardDto > list = new ArrayList< BoardDto >();
        String sql = "select * from board";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while ( rs.next() ) {
                list.add(   // 리스트에 추가
                     BoardDto   // builder 패턴을 사용한 dto 생성
                        .builder()  // 빌터 패턴의 시작을 알림
                        .btitle(rs.getString(1))
                        .bcontent(rs.getString(2))
                        .build());  // 빌더 패턴의 끝을 알림
                return list;
            }
        }catch (Exception e ){
            System.out.println( e );
        }
        return list;
    }







}