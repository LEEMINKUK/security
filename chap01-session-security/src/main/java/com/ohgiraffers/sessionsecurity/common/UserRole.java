package com.ohgiraffers.sessionsecurity.common;



// 클래스의 일종 으로 열거 타입 상수의 집합을 나타냄. final 과 유사
public enum UserRole {
     USER("USER"),
    ADMIN("ADMIN");

     private String role;

     UserRole(String role){
         this.role = role;
     }

     public String getRole(){
         return role;
     }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                '}';
    }
}
